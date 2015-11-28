package com.team1ofus.hermes;

import java.awt.Point;
import java.util.ArrayList;
import java.lang.String;
//import javax.swing.*;

public class PrintDirections { 
	//public enum Direction{FORWARD,BACKWARD,LEFT,RIGHT,F_LEFT,F_RIGHT,B_LEFT,B_RIGHT}
	
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
		for(int i = 0; i < dListSize; i++){ 
			Directions currentInstruction = directionsList.get(i); 
			double currentDistance = currentInstruction.getDistance(); 
			String currentState = currentInstruction.getHeading();
			
			System.out.print(currentState);
			System.out.print(",");
			System.out.println(currentDistance);
		}
	}
	private double getXDifference(CellPoint currentP, CellPoint previousP){ 
		Point currentPoint = currentP.getPoint();
		double xC = currentPoint.getX();
		Point previousPoint = previousP.getPoint();
		double xP = previousPoint.getX(); 
		double diffX = xC - xP; 
		return diffX; 
	}
	private double getYDifference(CellPoint currentP, CellPoint previousP){ 
		Point currentPoint = currentP.getPoint();
		double yC = currentPoint.getY();
		Point previousPoint = previousP.getPoint();
		double yP = previousPoint.getY(); 
		double diffY = yC - yP; 
		return diffY; 
	}
	
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
	
	private double getDistance(String state){ 
		if(state.length() == 1){ 
		return 3; //1 tile = 3 feet
		} 
		else { 
			return 4.24; //sqrt(3^2 + 3^2). distance of diagonal. 
		}
	}
	
	private Directions newDirection(String heading, double distance){ 
		Directions newEntry = new Directions(); 
		newEntry.heading = heading; 
		newEntry.distance = distance; 
		return newEntry; 
	}
	
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
}


