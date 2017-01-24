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
	
	double camPXWidth = 240, camPXHeight = 320, camDiagonalAngle = 68.5;
	double camPXDiagonal = Math.sqrt(camPXWidth * camPXWidth + camPXHeight * camPXHeight);
	double camVertAngle = camPXHeight / camPXDiagonal * camDiagonalAngle;
	double camHorizAngle = camPXWidth / camPXDiagonal * camDiagonalAngle;
	
	
   public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
   }
   public Vision(){
		table = NetworkTable.getTable("GRIP/myContoursReport");
		grip_table = NetworkTable.getTable("GRIP");
	}
   public double getGearAngleOffset() {
	   double[] cX = table.getNumberArray("centerX", networkTableDefault );
	   double[] areas = table.getNumberArray("area", networkTableDefault );
	   if (cX.length < 2) { return 0; } //Break out if not enough objects detected
	   double[] maxTwoAreas = {0, 0}; // {size1, size2}
	   int index1 = 0, index2 = 0;
	   for (int i = 0; i < areas.length; i++) {
		   if (areas[i] > maxTwoAreas[0]) {maxTwoAreas[0] = areas[i]; index1 = i;}
		   else if (areas[i] > maxTwoAreas[1]) {maxTwoAreas[1] = areas[i]; index2 = i;}
	   }
	   gearAngleOffset = (camPXWidth/2 - (cX[index1] + cX[index2])/2)/camPXWidth * camHorizAngle;
	   return gearAngleOffset;
   }
   public double getDistance() {
	   height = table.getNumberArray("height", networkTableDefault );
	   double[] areas = table.getNumberArray("area", networkTableDefault );
	   if (height.length < 2) { return 0; } //Break out if not enough objects detected
	   double[] maxTwoAreas = {0, 0}; // {size1, size2}
	   int index1 = 0, index2 = 0;
	   for (int i = 0; i < areas.length; i++) {
		   if (areas[i] > maxTwoAreas[0]) {maxTwoAreas[0] = areas[i]; index1 = i;}
		   else if (areas[i] > maxTwoAreas[1]) {maxTwoAreas[1] = areas[i]; index2 = i;}
	   }
	   distance = 2.5/Math.tan(camVertAngle/2*(height[index1]+height[index2])/2/camPXWidth); //in inches (faster)
	   return distance;
   }
}

