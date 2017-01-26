package org.usfirst.frc.team294.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 */
public class Vision extends Subsystem {
	NetworkTable table;
	NetworkTable grip_table;
	double[] networkTableDefault = new double[] { -1.0 };
	
	double[] centerX, centerY, centerGoal, height;
	double gearAngleOffset, distance;
	
	double camPXWidth = 320, camPXHeight = 240, camDiagonalAngle = 68.5;
	double camPXDiagonal = Math.sqrt(camPXWidth * camPXWidth + camPXHeight * camPXHeight);
	double camVertAngle = (camPXHeight / camPXDiagonal) * camDiagonalAngle;
	double camHorizAngle = (camPXWidth / camPXDiagonal) * camDiagonalAngle;
	double[][] contours;
	
	
   public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
   }
   public Vision(){
		table = NetworkTable.getTable("GRIP/myContoursReport");
		grip_table = NetworkTable.getTable("GRIP");
	}
   public int[] filterContours() {
	   //Determines the indices which two contours are most likely to be the targets
	   double[][] rawContours= {table.getNumberArray("centerX", networkTableDefault ), 
			   				 table.getNumberArray("centerY", networkTableDefault ),
			   				 table.getNumberArray("area", networkTableDefault )};
	   //1. Find overlapping contours and keep larger of the two
	   for (int i=0; i < rawContours[0].length; i++) {rawContours[3][i]  = Math.sqrt(rawContours[2][i])/2;} //Gets square x/y radius from point.
	   contours = null;
	   for (int a = 0; a < rawContours[0].length; a++) {
		   for (int b = 0; b < rawContours[0].length; b++) {
			   if (b == a) {continue; }
			   double[] aC = {rawContours[0][a], rawContours[1][a], rawContours[2][a], rawContours[3][a]};
			   double[] bC = {rawContours[0][b], rawContours[1][b], rawContours[2][b], rawContours[3][b]};
			   if (aC[3] + bC[3] > Math.abs(aC[0] - bC[0]) && aC[3] + bC[3] > Math.abs(aC[1] - bC[1])) { //Do the rectangles intersect?
				   if (aC[2] > bC[2]) {for (int i = 0; i < 4; i++) {contours[i][contours[3].length] = aC[i]; break;}} //If a has more area than b, add it to filtered list
			   } else {for (int i = 0; i < 4; i++ ) {contours[i][contours[3].length] = aC[i];}} //if a does not overlap, add it to filtered list
		   }
	   }
	   //2. Choose two biggest remaining contours
	   double[] maxTwoAreas = {0, 0}; // {size1, size2}
	   int[] index = {0, 0}; //Array for two best index values 
	   for (int i = 0; i < contours[2].length; i++) { //Loop through each area value to determine biggest contours
		   if (contours[2][i] > maxTwoAreas[0]) {maxTwoAreas[0] = contours[2][i]; index[0] = i;}
		   else if (contours[2][i] > maxTwoAreas[1]) {maxTwoAreas[1] = contours[2][i]; index[1] = i;}
	   }
	   return index;
   }
   public double getGearAngleOffset() {
	   //Gives the robot's angle of offset from the gear target in degrees
	   double[] cX = table.getNumberArray("centerX", networkTableDefault ); //Get x-position values from network tables
	   int[] index = filterContours(); //Gets indices of two best contours
	   gearAngleOffset = (camPXWidth/2 - (cX[index[0]] + cX[index[1]])/2)/camPXWidth * camHorizAngle; //in degrees
	   return gearAngleOffset;
   }
   public double getGearDistance() {
	   //Gives the distance of the robot from the gear target.
	   height = table.getNumberArray("height", networkTableDefault ); //Get contour height values from network table
	   int[] index = filterContours(); //Gets indices of two best contours
	   distance = 2.5/Math.tan((camVertAngle/2*(height[index[0]]+height[index[1]])/2/camPXWidth)*3.141592/180); //in inches (faster)
	   return distance;
   }
}

