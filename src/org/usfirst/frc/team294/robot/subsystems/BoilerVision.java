package org.usfirst.frc.team294.robot.subsystems;
import org.usfirst.frc.team294.utilities.Contour;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;


public class BoilerVision extends Subsystem {
	NetworkTable table;
	NetworkTable grip_table;
	double[] networkTableDefault = new double[] { -1.0 };

	double distance;

	double camHeight = 1; //Height of center of camera off of the ground
	double camAngle  = 40; //Upward angle offset of camera (in degrees)
	
	double camPXWidth = 320, camPXHeight = 240, camDiagonalAngle = 68.5; //Pixels, Pixels, Degrees
	double camPXDiagonal = Math.sqrt(camPXWidth * camPXWidth + camPXHeight * camPXHeight); //Diagonal camera pixel length
	double camVertAngle = (camPXHeight / camPXDiagonal) * camDiagonalAngle; //Vertical camera aperture angle
	double camHorizAngle = (camPXWidth / camPXDiagonal) * camDiagonalAngle; //Horizontal camera aperture angle

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}
	public BoilerVision(){
		table = NetworkTable.getTable("GRIP/myContoursReport");
		grip_table = NetworkTable.getTable("GRIP");
	}
	public Contour[] filterContours() {
		if (table.getNumberArray("area", networkTableDefault).length < 2) { //Make sure # of contours is valid
			Contour[] temp = {new Contour(), new Contour()};
			return temp;
		}
		Contour[] contours;
		//Instantiate array of contours to be filtered
		int tempXLength, tempYLength, tempAreaLength, tempHeightLength;
		while (true) { 
			double[] tempXPos = table.getNumberArray("centerX",   networkTableDefault);
			double[] tempYPos =  table.getNumberArray("centerY",   networkTableDefault);
			double[] tempArea = table.getNumberArray("area",   networkTableDefault);
			double[] tempHeight = table.getNumberArray("height", networkTableDefault);
			tempXLength = tempXPos.length;
			tempYLength = tempYPos.length;
			tempAreaLength = tempXPos.length;
			tempHeightLength = tempArea.length;
			contours = new Contour[tempXLength];
			if (tempXLength == tempYLength  && tempYLength == tempAreaLength && tempAreaLength == tempHeightLength){
				for (int i = 0; i < tempXLength; i++) {
					contours[i] = new Contour(tempXPos[i], tempYPos[i], tempArea[i], tempHeight[i]);
				}
				break;
			}
		}
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
		return bestContours;
	}
		
	public double getBoilerDistance() {
		//Gives the distance of the robot from the ball goal
		Contour[] targets = filterContours(); //Gets best two best contours
		if (targets[0].getArea() > 20 && targets[1].getArea() > 20) { //Checks that both contours are significant and not default
			double phi = targets[0].getHeight()/camPXHeight*camVertAngle + (camAngle - camVertAngle/2); //Angle from horizontal to center of the top contour
			distance = (7.208 - camHeight)/Math.tan(phi*Math.PI/180); //7.208 is the height in feet to the center of the top contour
		}
		else { distance = -1; }
		return distance;
	}
}

