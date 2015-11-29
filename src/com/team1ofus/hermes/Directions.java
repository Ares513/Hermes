package com.team1ofus.hermes;

import java.lang.String;
public class Directions { 
	public String heading; 
	public String turnInstruction; 
	public double distance; 
	
	public Directions(){ 
		
	}
	public String getHeading(){ 
		return this.heading; 
	}
	
	public String getTurnInstruction(){ 
		return this.turnInstruction; 
	}
	
	public double getDistance(){ 
		return this.distance; 
	}
	
}