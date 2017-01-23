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
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

   public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
   }
   public Vision(){
		table = NetworkTable.getTable("GRIP/myContoursReport");
		grip_table = NetworkTable.getTable("GRIP");
	}
   public double getGearAngleOffset() {
	   centerX = table.getNumberArray("centerX", networkTableDefault );
	   gearAngleOffset = .085625*(320-(centerX[0] + centerX[1])/2);
	   return gearAngleOffset;
   }
   public double getDistance() {
	   height = table.getNumberArray("height", networkTableDefault );
	   distance = 2.5/Math.tan(.0428125*((height[2]+height[3])/2)); //in inches
	   return distance;
   }
}

