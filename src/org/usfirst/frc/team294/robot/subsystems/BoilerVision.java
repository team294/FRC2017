//Only losers play Pokemon Go
package org.usfirst.frc.team294.robot.subsystems;
import org.usfirst.frc.team294.robot.Robot;
import org.usfirst.frc.team294.utilities.Contour;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class BoilerVision extends Subsystem {
	NetworkTable table;
	NetworkTable grip_table;
	double[] networkTableDefault = new double[] { -1.0 };

	double distance;
	double boilerAngleOffset;
	
	//Shooter Threshold (dividing line between where near distance and far distance ought be used)
	double shooterThreshold = 60; //In inches //Temporary value, real value should be input at some point
	int lastVal = 0;
	
	double camHeight = 1.55 * 12; //Height of center of camera off of the ground (in inches)
	double camAngle  = 35; //Upward angle offset of camera (in degrees)
	double camRotationAngle = 7.5; //Angle of rotation about axis coming out of camera.
	double camOffset = 10; //Camera horizontal offset from center of robot (in inches)
	
	double camPXWidth = 320, camPXHeight = 240, camDiagonalAngle = 68.5; //Pixels, Pixels, Degrees
	double camPXDiagonal = Math.hypot(camPXWidth, camPXHeight); //Diagonal camera pixel length
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
			tempAreaLength = tempArea.length;
			tempHeightLength = tempHeight.length;
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
		if (camRotationAngle != 0) {
			double a = camRotationAngle * Math.PI / 180;
			for (int i = 0; i < 2; i++) { //Perform coordinate transformation to account for rotation about axis coming out of camera.
				bestContours[i].setXPos(bestContours[i].getXPos() * Math.cos(a) - bestContours[i].getYPos() * Math.sin(a));
				bestContours[i].setYPos(bestContours[i].getXPos() * Math.sin(a) + bestContours[i].getYPos() * Math.cos(a));
			}
		}
		return bestContours;
	}
	/**
	 * Gets the distance of the robot from the ball goal
	 * @return distance in inches
	 */
	public double getBoilerDistance() {
		//Gives the distance of the robot from the ball goal
		Contour[] targets = filterContours(); //Gets best two best contours
		if (targets[0].getArea() > 5 && targets[1].getArea() > 5) { //Checks that both contours are significant and not default
			//double phi = targets[0].getHeight()/camPXHeight*camVertAngle + (camAngle - camVertAngle/2); //Angle from horizontal to center of the top contour
			double height = camPXHeight - Math.abs(targets[0].getYPos() + targets[1].getYPos())/2;
			double phi = height/camPXHeight*camVertAngle + (camAngle - camVertAngle/2);
			distance = (6.875 * 12 - camHeight * 12)/Math.tan(phi*Math.PI/180); //6.875 is the height in feet to the center of the two contours
		}
		else { distance = -1; }
		System.out.println(""+targets[0].getArea());
		System.out.println(""+targets[1].getArea());

		return distance; //Returns distance in inches
	}
	/**
	 * Gets the robot's angle of offset from the boiler
	 * @return Angle offset in degrees
	 */
	public double getBoilerAngleOffset() {
		//Gives the robot's angle of offset from the boiler in degrees
		Contour[] targets = filterContours(); //Gets best two best contours
		int numValid = 0; //number of contours that are valid (do not have default values, and are reasonably large)
		if (targets[0].getArea() > 5) { // target[0] should be bigger than target[1], so if target[0] fails, so will target[1].
			numValid++; 
			if (targets[1].getArea() > 5) {numValid++; }
		}
		if (numValid == 2) { //If there are two valid contours, use both.
			boilerAngleOffset = (camPXWidth/2 - (targets[0].getXPos() + targets[1].getXPos())/2)/camPXWidth * camHorizAngle; //in degrees
		}
		else if (numValid == 1) { //If there is only one valid contour, use only the one.
			boilerAngleOffset = (camPXWidth/2 - targets[0].getXPos())/camPXWidth * camHorizAngle; //in degrees
		}
		else { return -500; } //Return -500 if there are no "valid" contours (see numValid assignment)
		if (camOffset != 0) {
			boilerAngleOffset = boilerAngleOffset*.0174533; //convert to radians
			boilerAngleOffset = Math.atan(Math.tan(boilerAngleOffset) - camOffset/getBoilerDistance()/Math.cos(boilerAngleOffset))*180/Math.PI; //Adjusts angle for when the camera is not centered on the robot
		}
			return boilerAngleOffset;
	}
	
	/**
	 * Gets the last positive distance of the robot from the ball goal
	 * @return distance in inches only if there are contours 
	 */
	public double getLastBoilerDistance() {
		double firstBoilerDistance = getBoilerDistance();
		double lastBoilerDistance = 0;
		//double negNum = 0;
		if (firstBoilerDistance > 0){
			lastBoilerDistance = firstBoilerDistance;
		}
		//this.boilerAngleOffset = (this.boilerAngleOffset > 0) ? lastBoilerDistance : negNum;
		return lastBoilerDistance;
	}
	
	/**
	 * Likely does not work for boiler at this point
	 * Slightly slower but more accurate (gives distance from center of robot instead of from camera)
	 * @return distance of center of robot from the boiler in inches
	 */
	public double getBetterDistance() { 
		Contour[] targets = filterContours(); //Gets best two best contours
		int numValid = 0; //number of contours that are valid (do not have default values, and are reasonably large)
		if (targets[0].getArea() > 10) {numValid++; }
		if (targets[1].getArea() > 10) {numValid++; }
		double rawAngle;
		if (numValid == 2) {
			rawAngle = (camPXWidth/2 - (targets[0].getXPos() + targets[1].getXPos())/2)/camPXWidth * camHorizAngle; //in degrees
		}
		else if (numValid == 1) {
			rawAngle = (camPXWidth/2 - targets[0].getXPos())/camPXWidth * camHorizAngle; //in degrees
		}
		else { return -500; }
		double d = getBoilerDistance();
		return Math.sqrt(d * d + camOffset * camOffset - 2 * d * camOffset * Math.cos(Math.PI/2 - rawAngle * .0174533));
	}
	
	/**
	 * Tells which shooter mode should be used based on the robot's distance from the boiler
	 * Where that threshold is should be set in BoilerVision.java
	 * @return 0 if close range, 1 if long range
	 */
	public int getShooterMode() {
		double tol = 5; //(in inches) range where change should not occur, prevents rapid switching at threshold
		double d = getLastBoilerDistance();
		if (d < shooterThreshold + tol && d > shooterThreshold - tol){
			return lastVal;
		}
		if (d < shooterThreshold ) {
			lastVal = 0;
			return lastVal; 
		}
		else {
			lastVal = 1;
			return lastVal;
		}
	}
	/**
	 * Displays the distance to boiler on SmartDashboard
	 */
	public void updateSmartDashboard(){
		SmartDashboard.putNumber("Distance to Boiler", Robot.boilerVision.getBoilerDistance());
	}
	
}


