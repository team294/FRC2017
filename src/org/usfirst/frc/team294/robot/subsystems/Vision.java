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

	double camPXWidth = 320, camPXHeight = 240, camDiagonalAngle = 68.5; //Pixels, Pixels, Degrees
	double camPXDiagonal = Math.sqrt(camPXWidth * camPXWidth + camPXHeight * camPXHeight); //Diagonal camera aperture angle
	double camVertAngle = (camPXHeight / camPXDiagonal) * camDiagonalAngle; //Vertical camera aperture angle
	double camHorizAngle = (camPXWidth / camPXDiagonal) * camDiagonalAngle; //Horizontal camera aperture angle
	double[][] contours = new double[5][20];
	int contourLength = 0;

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
		contourLength = 0; //Set contour length back to zero
		double[][] rawContours = {table.getNumberArray("centerX", networkTableDefault), 
				table.getNumberArray("centerY", networkTableDefault ),
				table.getNumberArray("area", networkTableDefault ),
				new double[table.getNumberArray("area", networkTableDefault ).length],
				table.getNumberArray("height", networkTableDefault )};
		int rawContoursLength = table.getNumberArray("area", networkTableDefault ).length;
		//1. Find overlapping contours and keep larger of the two
		for (int i = 0; i < rawContoursLength; i++) {
			rawContours[3][i]  = Math.sqrt(rawContours[2][i])/2;
		} //Gets square x/y radius from point.
		for (int a = 0; a < rawContoursLength; a++) {
			for (int b = 0; b < rawContoursLength; b++) {
				if (b == a) {continue; }
				double[] aC = {rawContours[0][a], rawContours[1][a], rawContours[2][a], rawContours[3][a], rawContours[4][a]};
				double[] bC = {rawContours[0][b], rawContours[1][b], rawContours[2][b], rawContours[3][b], rawContours[4][b]};
				if (aC[3] + bC[3] > Math.abs(aC[0] - bC[0]) && aC[3] + bC[3] > Math.abs(aC[1] - bC[1])) { //Do the rectangles intersect?
					if (aC[2] > bC[2]) { //If a has more area than b, add it to filtered list
						for (int i = 0; i < 5; i++) {
							contours[i][contourLength] = aC[i];
							contourLength++;
						} break;
					}
				} 
				else { //if a does not overlap, add it to filtered list
					for (int i = 0; i < 5; i++ ) {
						contours[i][contourLength] = aC[i];
						contourLength++;
					}
				}
			}
		}
		//2. Choose two biggest remaining contours
		double[] maxTwoAreas = {0, 0}; // {size1, size2}
		int[] index = {0, 0}; //Array for two best index values 
		for (int i = 0; i < contourLength; i++) { //Loop through each area value to determine biggest contours
			if (contours[2][i] > maxTwoAreas[0]) {
				maxTwoAreas[0] = contours[2][i]; index[0] = i;
			} 
			else if (contours[2][i] > maxTwoAreas[1]) {
				maxTwoAreas[1] = contours[2][i]; index[1] = i;
			}
		}
		return index;
	}
	public double getGearAngleOffset() {
		//Gives the robot's angle of offset from the gear target in degrees
		int[] index = filterContours(); //Gets indices of two best contours
		gearAngleOffset = (camPXWidth/2 - (contours[0][index[0]] + contours[0][index[1]])/2)/camPXWidth * camHorizAngle; //in degrees
		return gearAngleOffset;
	}
	public double getGearDistance() {
		//Gives the distance of the robot from the gear target.
		int[] index = filterContours(); //Gets indices of two best contours
		distance = 2.5/Math.tan((camVertAngle/2*(contours[4][index[0]]+contours[4][index[1]])/2/camPXWidth)*3.141592/180); //in inches (faster)
		return distance;
	}
}

