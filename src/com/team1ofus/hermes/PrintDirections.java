package com.team1ofus.hermes;

import java.awt.Point;
import java.util.ArrayList;
import java.lang.String;
//import javax.swing.*;

public class PrintDirections { 
	public PrintDirections() { 
	}	
	 
	
	public void parseDirections(ArrayList<CellPoint> AStarDirections){
		int numberOfSteps = AStarDirections.size(); 
		ArrayList<String> stateList = new ArrayList<String>(); 
		for(int i = 1; i < numberOfSteps; i++){ 
			CellPoint iterationI = AStarDirections.get(i);
			CellPoint prevIteration = AStarDirections.get(i-1); 
			double xDiff = getXDifference(iterationI,prevIteration);
			double yDiff = getYDifference(iterationI,prevIteration); 
			String state = getState(xDiff, yDiff); 
			stateList.add(state); 
		}
		ArrayList<Directions> directionsList = route(stateList);
		int dListSize = directionsList.size(); 
		int prevDegree = 0;  
		for(int i = 0; i < dListSize; i++){ 
			Directions currentInstruction = directionsList.get(i); 
			double currentDistance = currentInstruction.getDistance(); 
			String currentState = currentInstruction.getHeading();
			if(i==0){ 
				String startInstruction = "Walk "; 
				startInstruction += toFullWord(currentState);
				currentInstruction.turnInstruction = startInstruction; 
				prevDegree = toDegrees(currentState); 
				directionsList.set(i, currentInstruction); 
			}
			else{
				String newInstruction = toTurns(toDegrees(currentState),prevDegree); 
				currentInstruction.turnInstruction = newInstruction; 
				prevDegree = toDegrees(currentState); 
			}
			System.out.print(currentInstruction.heading);
			System.out.print(",");
			System.out.print(currentInstruction.turnInstruction);
			System.out.print(",");
			System.out.println(currentInstruction.distance);
		}
	}
	
	/* 
	 * finds the change in X between two coordinates 
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
	 * given the "state" (direction) of a step
	 * outputs the distance traveled.
	 * each tile is a 3x3 box 
	 * steps N,S,E,W all travel 3 feet 
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
	 * given a heading and distance
	 * outputs Directions
	 */
	private Directions newDirection(String heading, double distance){ 
		Directions newEntry = new Directions(); 
		newEntry.heading = heading; 
		newEntry.distance = distance; 
		return newEntry; 
	}
	
	/*
	 * given an ArrayList of (string) States (N,S,E,W,NE,NW,SE,SW)
	 * outputs an ArrayList of non-repeating Directions 
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
	 * this is where the code gets dumb.... I couldnt think of a better way to do this, and didnt want to spend more time on it.  
	 * to convert the heading to directions to turn left, slight left, sharp left etc... 
	 */
	
	/*
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
	
	
	private String toTurns(int currentDegree, int prevDegree){ 
		String turn = new String(); 
		int diff = currentDegree - prevDegree; 
		System.out.print(currentDegree);
		System.out.print(",");
		System.out.print(prevDegree);
		System.out.print(",");
		System.out.print(diff);
		System.out.print(",");
		if(diff == 45 || diff == -315){ 
			turn = "Slight Right"; 
		}
		else if(diff == 90 || diff == -270){ 
			turn = "Right"; 
		}
		else if(diff == 135 || diff == -225){ 
			turn = "Sharp Right"; 
		}
		else if(diff == -45 || diff == 315){ 
			turn = "Slight Left"; 
		}
		else if(diff == -90 || diff == 270){ 
			turn = "Left"; 
		}
		else if(diff == -135 || diff == 225){ 
			turn = "Sharp Left"; 
		}
		return turn; 
	}
	
	private String toFullWord(String state){ 
		String degree = null; 
		switch (state){ 
			case "N": degree = "North"; 
			break;
			case "NE": degree = "NorthEast"; 
			break;
			case "E": degree = "East"; 
			break;
			case "SE": degree = "SouthEast"; 
			break;
			case "S": degree = "South";
			break;
			case "SW": degree = "SouthWest"; 
			break;
			case "W": degree = "West";
			break;
			case "NW": degree = "NorthWest"; 
			break; 	
		}
		return degree; 
	}
}


