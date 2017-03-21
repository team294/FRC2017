package org.usfirst.frc.team294.utilities;

public class ProfileGenerator {

	public double currentPosition;
	public double finalPosition;

	public double currentVelocity;
	public double maxVelocity;

	public double currentAcceleration;
	public double maxAcceleration;

	public double dt;
	public double totalTime;
	
	public double threshold;
	
	private double directionSign;

	//Function to update equations for when we increment them, function to say hey we're done
	public ProfileGenerator(double initialPosition, double finalPosition, double initialVelocity, double maxVelocity, double maxAcceleration, double dt, double threshold) {
		// TODO Auto-generated constructor stub
		this.currentPosition = initialPosition;
		this.finalPosition = Math.abs(finalPosition);
		directionSign = this.finalPosition/finalPosition;
		this.currentVelocity = initialVelocity;
		this.maxVelocity = maxVelocity;
		//this.currentAcceleration = currentAcceleration;
		this.maxAcceleration = maxAcceleration;
		this.dt = dt;
		this.threshold = threshold;
		//this.totalTime = totalTime;
	}

	public double getCurrentPosition(){
		double stoppingDistance = 0.5*currentVelocity*currentVelocity/maxAcceleration;
		if(finalPosition - currentPosition < stoppingDistance) currentAcceleration = -maxAcceleration;
		else if(currentVelocity < maxVelocity) currentAcceleration = maxAcceleration;
		else currentAcceleration = 0;
		
		currentVelocity = currentVelocity + currentAcceleration*dt;
		
		if(currentVelocity > maxVelocity) currentVelocity = maxVelocity;
		currentPosition = currentPosition + currentVelocity*dt + .5*currentAcceleration*dt*dt;
		if(currentPosition > finalPosition) currentPosition = finalPosition;
		
		return currentPosition*directionSign;
	}

	public double getCurrentVelocity(){
		double stoppingDistance = 0.5*currentVelocity*currentVelocity/maxAcceleration;
		if(finalPosition - currentPosition < stoppingDistance) currentAcceleration = -maxAcceleration;
		else if(currentVelocity < maxVelocity) currentAcceleration = maxAcceleration;
		else currentAcceleration = 0;
		currentVelocity = currentVelocity + currentAcceleration*dt;
		if(Math.abs(currentVelocity) > Math.abs(maxVelocity)) currentVelocity = maxVelocity;
		currentPosition = currentPosition + currentVelocity*dt + .5*currentAcceleration*dt*dt;
		if(Math.abs(currentPosition) > Math.abs(finalPosition)) currentPosition = finalPosition;
		return currentVelocity;
	}

	public boolean isFinished(){
		if(Math.abs(currentPosition - finalPosition) < threshold) return true;
		else return false;
	}
}
