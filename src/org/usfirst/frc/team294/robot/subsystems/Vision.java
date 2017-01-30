package org.usfirst.frc.team294.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
class Contour {
	private double xPos, yPos, area, height;
	private double radius;
	private boolean eliminated = false;
	
	//constructor
	public Contour(double xPos, double yPos, double area, double height) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.area = area;
		this.height = height;
		this.radius = Math.sqrt(this.area/Math.PI)/2; //Adjusted radius of contour (Divided by two to reduce overlap detection likelihood)
	}
	//Other Constructor
	public Contour() {
		this.xPos = 0;
		this.yPos = 0;
		this.area = 0;
		this.height = 0;
		this.radius = 0;
	}
	
	//Getters
	public double getXPos() {return this.xPos; }
	public double getYPos() {return this.yPos; }
	public double getArea() {return this.area; }
	public double getHeight() {return this.height; }
	public double getRadius() {return this.radius; }
	public boolean isEliminated() {return this.eliminated; }
	
	//Setters
	public void eliminate() {this.eliminated = true; }
	
	//Special Methods
	public double getDistance(Contour c) { //Gets pixel distance between two contours
		double xDist = (c.getXPos() - this.getXPos());
		double yDist = (c.getYPos() - this.getYPos());
		return Math.sqrt(xDist * xDist + yDist * yDist);
	}
	public boolean intersects(Contour c) { //Determines if two contours intersect
		return (c.getDistance(this) < c.getRadius() + this.getRadius());
	}
}

public class Vision extends Subsystem {
	NetworkTable table;
	NetworkTable grip_table;
	double[] networkTableDefault = new double[] { -1.0 };

	double gearAngleOffset, distance;

	double camPXWidth = 320, camPXHeight = 240, camDiagonalAngle = 68.5; //Pixels, Pixels, Degrees
	double camPXDiagonal = Math.sqrt(camPXWidth * camPXWidth + camPXHeight * camPXHeight); //Diagonal camera pixel length
	double camVertAngle = (camPXHeight / camPXDiagonal) * camDiagonalAngle; //Vertical camera aperture angle
	double camHorizAngle = (camPXWidth / camPXDiagonal) * camDiagonalAngle; //Horizontal camera aperture angle

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}
	public Vision(){
		table = NetworkTable.getTable("GRIP/myContoursReport");
		grip_table = NetworkTable.getTable("GRIP");
	}
	public Contour[] filterContours() {
		if (table.getNumberArray("area", networkTableDefault).length < 2) { //Make sure # of contours is valid
			System.out.println("Not Enough Contours!!!!");
			Contour[] temp = {new Contour(), new Contour()};
			return temp;
		}
		Contour[] contours;
		//Instantiate array of contours to be filtered
		int tempXLength, tempYLength, tempAreaLength, tempHeightLength;
		while (true) { 
			double[] tempXPos = table.getNumberArray("xPos",   networkTableDefault);
			double[] tempYPos =  table.getNumberArray("yPos",   networkTableDefault);
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
		//Initialize array of contours to be filtered
		//Check if there are only two contours. If so, just use those
		//Eliminate the smaller of any two overlapping contours
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
			if (contours[i].getArea() > bestContours[0].getArea()) {bestContours[0] = contours[i]; }
			else if (contours[i].getArea() > bestContours[1].getArea()) {bestContours[1] = contours[i]; }
		}
		return bestContours;
	}
	
	public double getGearAngleOffset() {
		//Gives the robot's angle of offset from the gear target in degrees
		Contour[] targets = filterContours(); //Gets best two best contours
		gearAngleOffset = (camPXWidth/2 - (targets[0].getXPos() + targets[1].getXPos())/2)/camPXWidth * camHorizAngle; //in degrees
		SmartDashboard.putNumber("Angle Offset", gearAngleOffset);
		return gearAngleOffset;
	}
	
	public double getGearDistance() {
		//Gives the distance of the robot from the gear target.
		Contour[] targets = filterContours(); //Gets best two best contours
		distance = 2.5/Math.tan((camVertAngle/2*(targets[0].getHeight() + targets[1].getHeight())/2/camPXHeight)*Math.PI/180); //in inches (faster)
		SmartDashboard.putNumber("Gear Distance", distance);
		return distance;
	}
}

