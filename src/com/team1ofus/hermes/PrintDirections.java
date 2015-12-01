package com.team1ofus.hermes;

import java.awt.Point;
import java.util.ArrayList;

import javafx.scene.Parent;

import java.lang.String;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.lang.Double;
//import javax.swing.*;

//This class handles converting the A* path into a set of directions
public class PrintDirections { 
	public PrintDirections() { 
	
	}	 
	public ArrayList<String> parseDirections(ArrayList<CellPoint> AStarDirections){
		ArrayList<String> statesList = stateList(AStarDirections); 
		ArrayList<Directions> directionsList = route(statesList);
		ArrayList<String> finalList = routePrintOut(directionsList); 
		return finalList; 
	}
	
	/* 
	 * Takes a* path, and returns ArrayList of string that represents the direction that each tile is heading.
	 */
	private ArrayList<String> stateList(ArrayList<CellPoint> aStarPath){ 
		int numberOfSteps = aStarPath.size(); 
		ArrayList<String> stateList = new ArrayList<String>(); 
		for(int i = 1; i < numberOfSteps; i++){ 
			CellPoint iterationI = aStarPath.get(i);
			CellPoint prevIteration = aStarPath.get(i-1); 
			double xDiff = getXDifference(iterationI,prevIteration);
			double yDiff = getYDifference(iterationI,prevIteration); 
			String state = getState(xDiff, yDiff); 
			stateList.add(state); 
		}
		return stateList; 
	}
	
	/* 
	 * Takes a list of directions that have a heading, and distances.
	 * Adds in colloquial turn instructions to list of directions.
	 */
	private ArrayList<String> routePrintOut(ArrayList<Directions> dList){ 
		DecimalFormat df = new DecimalFormat("#.00");  
		df.setRoundingMode(RoundingMode.CEILING);
		
		ArrayList<String> humanReadableDirections = new ArrayList<String>(); 
		
		int dListSize = dList.size(); 
		int prevDegree = 0;  
		double totalDistance = 0; 
		
		for(int i = 0; i < dListSize; i++){ 
			Directions currentInstruction = dList.get(i); 
			double currentDistance = currentInstruction.getDistance(); 
			totalDistance += currentDistance; 
			String currentState = currentInstruction.getHeading();
			/* First instruction. provides user with initial instruction */
			if(i==0){  
				String startInstruction = "Head "; 
				startInstruction += toFullWord(currentState);
				startInstruction += ", walk for ";
				startInstruction += df.format(currentDistance);		
				startInstruction += " feet";
				currentInstruction.turnInstruction = startInstruction; 
				prevDegree = toDegrees(currentState); 
				dList.set(i, currentInstruction); 
			}
			/*  Format for all instructions that are not the first /special instruction. */
			else {
				String newInstruction = "Take a ";  
				newInstruction += toTurns(toDegrees(currentState),prevDegree); 
				newInstruction += " to head "; 
				newInstruction += toFullWord(currentState);;
				newInstruction += "\nWalk "; 
				newInstruction += df.format(currentDistance); 
				newInstruction += " feet";
				currentInstruction.turnInstruction = newInstruction; 
				prevDegree = toDegrees(currentState); 
				}
			humanReadableDirections.add(currentInstruction.turnInstruction); 
			//System.out.println(currentInstruction.turnInstruction);
			}
			humanReadableDirections.add(estimatedTime(totalDistance)); 
		  //	System.out.println(estimatedTime(totalDistance));  
			return humanReadableDirections;
		}	
	
	
	/* 
	 * Finds the change in X between two coordinates 
	 * @param CellPoint CellPoint 
	 * @return double
	 */
	private double getXDifference(CellPoint currentP, CellPoint previousP){ 
		Point currentPoint = currentP.getPoint();
		double xC = currentPoint.getX();
		Point previousPoint = previousP.getPoint();
		double xP = previousPoint.getX(); 
		double diffX = xC - xP; 
		return diffX; 
	}
	
	/*
	 * Finds the change in Y between two coordinates 
	 */
	private double getYDifference(CellPoint currentP, CellPoint previousP){ 
		Point currentPoint = currentP.getPoint();
		double yC = currentPoint.getY();
		Point previousPoint = previousP.getPoint();
		double yP = previousPoint.getY(); 
		double diffY = yC - yP; 
		return diffY; 
	}
	
	
	/* 
	 * Given the change in X & Y between two coordinates 
	 * determines the direction of the step. 
	 * will output N,S,E,W,NE,NW,SE,SW
	 */
	private String getState(double xChange, double yChange){ 
		String direction = ""; 
		if(yChange > 0 ){ 
			direction+="S";
		}
		if(yChange < 0){ 
			direction += "N";
		}
		if(xChange > 0 ){
			direction+="E"; 
		}
		if(xChange < 0 ){
			direction+="W"; 
		}
		return direction; 
	}
	
	/* 
	 * Given the "state" (direction) of a step, outputs the distance traveled.
	 * Each tile is a 3x3 box, steps N,S,E,W all travel 3 feet 
	 * Steps NE,NW,SE,SW travel 4.34 feet
	 */
	private double getDistance(String state){ 
		if(state.length() == 1){ 
		return 3; //1 tile = 3 feet
		} 
		else { 
			return 4.24; //sqrt(3^2 + 3^2). distance of diagonal. 
		}
	}
	
	/*
	 * Given a heading and distance outputs Directions
	 */
	private Directions newDirection(String heading, double distance){ 
		Directions newEntry = new Directions(); 
		newEntry.heading = heading; 
		newEntry.distance = distance; 
		return newEntry; 
	}
	
	/*
	 * Given an ArrayList of (string) States (N,S,E,W,NE,NW,SE,SW), outputs an ArrayList of non-repeating Directions 
	 *  N,N,N,E,E -> N 9, E 6
	 */
	private ArrayList<Directions> route(ArrayList<String> states){ 
		int size = states.size();
		ArrayList<Directions> dList = new ArrayList<Directions>();
		double currentDistance =0; 
		
		for(int i = 0; i < size; i++){ 
			String currentState = states.get(i); 
		
			if(i > 0){ 
				String previousState = states.get(i-1); 
				if(currentState.equals(previousState) && i!= (size-1)){ 
					currentDistance += getDistance(currentState); 
				}
				else{ 
					dList.add(newDirection(previousState, currentDistance)); 
					currentDistance = 0; 
					currentDistance = getDistance(currentState); 
				}
			}
			else{ 
				currentDistance += getDistance(currentState); 
			}
		}
		return dList; 
	}
	/*
	 * Takes Cardinal direction and converts to degrees. 
	 * Degrees with respect to +Y Axis This way North is 0... 
	 */
	private int toDegrees(String state){ 
		int degree = 0; 
		switch (state){ 
			case "N": degree = 0; 
			break;
			case "NE": degree = 45; 
			break;
			case "E": degree = 90; 
			break;
			case "SE": degree = 135; 
			break;
			case "S": degree = 180;
			break;
			case "SW": degree = 225; 
			break;
			case "W": degree = 270;
			break;
			case "NW": degree = 315; 
			break; 	
		}
		return degree; 
	}
	
	/*
	 * Takes 2 different headings, determines difference in angle 
	 * Returns a colloquial term for what type of turn to make. 
	 */
	private String toTurns(int currentDegree, int prevDegree){ 
		String turn = new String(); 
		int diff = currentDegree - prevDegree; 
		if(diff == 45 || diff == -315){ 
			turn = "slight right"; 
		}
		else if(diff == 90 || diff == -270){ 
			turn = "right"; 
		}
		else if(diff == 135 || diff == -225){ 
			turn = "sharp right"; 
		}
		else if(diff == -45 || diff == 315){ 
			turn = "slight left"; 
		}
		else if(diff == -90 || diff == 270){ 
			turn = "left"; 
		}
		else if(diff == -135 || diff == 225){ 
			turn = "sharp left"; 
		}
		return turn; 
	}
	
	/*
	 * Turn 1 or 2 letter heading into a word
	 */
	private String toFullWord(String state){ 
		String degree = null; 
		switch (state){ 
			case "N": degree = "North"; 
			break;
			case "NE": degree = "Northeast"; 
			break;
			case "E": degree = "East"; 
			break;
			case "SE": degree = "Southeast"; 
			break;
			case "S": degree = "South";
			break;
			case "SW": degree = "Southwest"; 
			break;
			case "W": degree = "West";
			break;
			case "NW": degree = "Northwest"; 
			break; 	
		}
		return degree; 
	}
	
	//Converts distance into a time estimate
	private String estimatedTime(double distance){ 
		DecimalFormat df = new DecimalFormat("#.00");  
		df.setRoundingMode(RoundingMode.CEILING);
		double toMile = distance/5280; // Feet per mile 
		double perHour = .33;          // Approximate average walking speed is 3 miles/hour
		int minutes = 60; 
		double eTime = (toMile*perHour*minutes);
		String timeToString = df.format(eTime);
		String ETA = "Your route will take approximately "; 
		ETA += timeToString; 
		ETA += " minutes"; 
		return ETA; 
	}
}


