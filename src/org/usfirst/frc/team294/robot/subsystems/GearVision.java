package org.usfirst.frc.team294.robot.subsystems;


import org.usfirst.frc.team294.utilities.Contour;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class GearVision extends Subsystem {
	NetworkTable table;
	NetworkTable grip_table;
	double[] networkTableDefault = new double[] { -1.0 };

	double gearAngleOffset, distance;

	double camPXWidth = 320, camPXHeight = 240, camDiagonalAngle = 68.5; //Pixels, Pixels, Degrees
	double camPXDiagonal = Math.sqrt(camPXWidth * camPXWidth + camPXHeight * camPXHeight); //Diagonal camera pixel length
	double camVertAngle = (camPXHeight / camPXDiagonal) * camDiagonalAngle; //Vertical camera aperture angle
	double camHorizAngle = (camPXWidth / camPXDiagonal) * camDiagonalAngle; //Horizontal camera aperture angle
	double camOffset = 5.75; //Camera horizontal offset from center of robot
    double camAngleOffsetDegrees = -3.8;
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}
	public GearVision(){
		table = NetworkTable.getTable("GRIP/myGearReport");
		grip_table = NetworkTable.getTable("GRIP");
	}
	public Contour[] filterContours() {
		if (table.getNumberArray("area", networkTableDefault).length < 2) { //Make sure # of contours is valid

			Contour[] temp = {new Contour(), new Contour()};
			return temp;
		}
		Contour[] contours;
		//Instantiate array of contours to be filtered
		int tempXLength, tempYLength, tempAreaLength, tempWidthLength, tempHeightLength;
		while (true) { // Start of continuous loop to make the contour array
			double[] tempXPos = table.getNumberArray("centerX",   networkTableDefault);
			double[] tempYPos =  table.getNumberArray("centerY",   networkTableDefault);
			double[] tempArea = table.getNumberArray("area",   networkTableDefault);
			double[] tempHeight = table.getNumberArray("height", networkTableDefault);
			double[] tempWidth = table.getNumberArray("width", networkTableDefault);
			tempXLength = tempXPos.length;
			tempYLength = tempYPos.length;
			tempAreaLength = tempXPos.length;
			tempWidthLength = tempWidth.length;
			tempHeightLength = tempArea.length;
			contours = new Contour[tempXLength];//Gives your contour array a length equal to the number of centerXs present in the Network Table
			if (tempXLength == tempYLength  && tempYLength == tempAreaLength && tempAreaLength == tempHeightLength && tempHeightLength == tempWidthLength){
				for (int i = 0; i < tempXLength; i++) {
					contours[i] = new Contour(tempXPos[i], tempYPos[i], tempArea[i], tempHeight[i], tempWidth[i]);
				}
				break;
			}
		}//End of the continuous loop to make the contour array

		for (int a = 0; a < contours.length; a++) {
			//if (contours[a].isEliminated()) {continue; } // If the contour at a is already eliminated, skip it
			for (int b = a + 1; b < contours.length; b++) {
				//if (contours[b].isEliminated()) {continue; } // If the contour at b is already eliminated, skip it
				if (contours[a].intersects(contours[b])) { //If contours intersect, delete one of them
					if (contours[a].getArea() < contours[b].getArea()) {contours[a].eliminate();} //If the area of a < area of b, delete a
					else {contours[b].eliminate();} //If the area of b <= the area of a, delete b
				}
			}
		}
		//Find two largest remaining contours and return them
		Contour[] bestContours = {new Contour(), new Contour()};
		for (int i = 0; i < contours.length; i++) {
			if (contours[i].isEliminated()) {continue; } //If the contour is already eliminated, skip it

			if (contours[i].getArea() > bestContours[0].getArea()) { 
				bestContours[1] = bestContours[0];
				bestContours[0] = contours[i];
			}
			else if (contours[i].getArea() > bestContours[1].getArea()) {bestContours[1] = contours[i]; }
		}
		return bestContours; //Returns largest two contours
	}

//Only losers play Pokemon Go
	/**
	 * Gets the robot's angle of offset from the gear target
	 * @return Angle offset in degrees
	 */
	public double getGearAngleOffset() {
		//Gives the robot's angle of offset from the gear target in degrees
		Contour[] targets = filterContours(); //Gets best two best contours
		int numValid = 0; //number of contours that are valid (do not have default values, and are reasonably large)
		if (targets.length > 0 && targets[0].getArea() > 10) {numValid++; }
		if (targets.length > 1 && targets[1].getArea() > 10) {numValid++; }
		if (numValid == 2) {
			gearAngleOffset = (camPXWidth/2 - (targets[0].getXPos() + targets[1].getXPos())/2)/camPXWidth * camHorizAngle; //in degrees
		}
		else if (numValid == 1) {
			//targets[0].getWidth()*5.25/2 adds theoretical number of pixels to center of gear target
			gearAngleOffset = (camPXWidth/2 - (targets[0].getXPos() + targets[0].getWidth()*5.25/2))/camPXWidth * camHorizAngle; //in degrees
		}
		else { return 0; } //Return -500 if there are no "valid" contours (see numValid assignment)
		gearAngleOffset = Math.atan(camOffset/getGearDistance() + Math.tan(gearAngleOffset*Math.PI/180))*180/Math.PI; //Adjusts angle for when the camera is not centered on the robot
		return gearAngleOffset + camAngleOffsetDegrees;
	}

	/**
	 * Gets the distance of the robot from the gear target 
	 * @return distance in inches
	 */
	public double getGearDistance() {
		//Gives the distance of the robot from the gear target if our camera's center is at the same elevation as the center of the gear tape
		int heightOfTape = 5; //Height of the tape on the gear lift
		double tACC = 1; //Proportion of the tape that is at or above our camera's center (if the camera is straight on)
		Contour[] targets = filterContours(); //Gets best two best contours
		int numValid = 0;
		if (targets.length > 0 && targets[0].getArea() > 10) {numValid++; }
		if (targets.length > 1 && targets[1].getArea() > 10) {numValid++; }
		if (numValid == 2) {
		distance = heightOfTape*tACC/Math.tan((camVertAngle*(targets[0].getHeight() + targets[1].getHeight())/2/camPXHeight)*Math.PI/180); //in inches (faster)
		}
		else if (numValid  ==  1) {
			distance = heightOfTape*tACC/Math.tan((camVertAngle*targets[0].getHeight()/camPXHeight)*Math.PI/180); //in inches (faster)
		}
		SmartDashboard.putNumber("Gear Distance", distance);
		return distance;
	}
}

