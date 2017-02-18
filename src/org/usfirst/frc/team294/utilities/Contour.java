package org.usfirst.frc.team294.utilities;

public class Contour {
	private double xPos, yPos, area, height, radius;
	private boolean eliminated = false;
	
	//constructor
	public Contour(double xPos, double yPos, double area, double height) {
		this.xPos = xPos;//Center X position of contour
		this.yPos = yPos;//Center Y position of contour
		this.area = area;//Area of contour
		this.height = height;//Height of the contour
		this.radius = Math.sqrt(this.area/Math.PI)/3; //Adjusted radius of contour (Divided by two to reduce overlap detection likelihood)
	}
	//Argumentless Constructor
	public Contour() {this.xPos = this.yPos = this.area = this.height = this.radius = 0; }
	
	//Getters
	public double getXPos() {return this.xPos; }
	public double getYPos() {return this.yPos; }
	public double getArea() {return this.area; }
	public double getHeight() {return this.height; }
	public double getRadius() {return this.radius; }
	public boolean isEliminated() {return this.eliminated; }
	
	//Setters
	public void eliminate() {this.eliminated = true; } //"Eliminates" this variable, by setting eliminated to true
	
	//Special Methods
	public double getDistance(Contour c) { //Gets pixel distance between two contours
		return Math.hypot(c.getXPos() - this.getXPos(), c.getYPos() - this.getYPos()); //  = Math.sqrt(xDist * xDist + yDist * yDist);
	}
	public boolean intersects(Contour c) { //Determines if two contours intersect (treated as circles)
		return (c.getDistance(this) < (c.getRadius() + this.getRadius()));
	}
}