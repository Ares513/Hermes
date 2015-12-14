package ui;

import java.awt.Point;
import java.util.ArrayList;

import javafx.scene.Parent;
import pathing.CellPoint;

import java.lang.String;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.lang.Double;
//import javax.swing.*;

//This class handles converting the A* path into a set of directions
public class PrintDirections { 
	public PrintDirections() { 
	
	}	 
	public ArrayList<Directions> parseDirections(ArrayList<CellPoint> AStarDirections){
		ArrayList<Directions> statesList = stateList(AStarDirections); 
		ArrayList<Directions> directionsList = route(statesList);
		return directionsList; 
	}
	
	public ArrayList<Directions> printableList(ArrayList<Directions> directionsList){ 
		ArrayList<Directions> printList = removeFalseTurns(directionsList);
		ArrayList<Directions> finalList = routePrintOut(printList);
		return finalList; 
	}
	
	/* 
	 * Takes a* path, and returns ArrayList of string that represents the direction that each tile is heading.
	 */
	private ArrayList<Directions> stateList(ArrayList<CellPoint> aStarPath){ 
		int numberOfSteps = aStarPath.size(); 
		ArrayList<Directions> stateList = new ArrayList<Directions>(); 
		for(int i = 1; i < numberOfSteps; i++){ 
			CellPoint iterationI = aStarPath.get(i);
			CellPoint prevIteration = aStarPath.get(i-1); 
			double xDiff = getXDifference(iterationI,prevIteration);
			double yDiff = getYDifference(iterationI,prevIteration); 
			String state = getState(xDiff, yDiff); 
			
			Directions onlyAHeading = new Directions();
			onlyAHeading = newDirection(state, 0, iterationI, null); 
			stateList.add(onlyAHeading); 
		}
		return stateList; 
	}
	
	/* 
	 * Takes a list of directions that have a heading, and distances.
	 * Adds in colloquial turn instructions to list of directions.
	 */
	private ArrayList<Directions> routePrintOut(ArrayList<Directions> dList){ 
		DecimalFormat df = new DecimalFormat("#.00");  
		df.setRoundingMode(RoundingMode.CEILING);
		
		//ArrayList<String> humanReadableDirections = new ArrayList<String>(); 
		
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
				startInstruction += " "; 
				startInstruction += "\n       walk for ";
				startInstruction += df.format(currentDistance);		
				startInstruction += " feet";
				//startInstruction += "\n-------------";
				currentInstruction.setTurnInstructions(startInstruction);
				
				prevDegree = toDegrees(currentState); 
				//dList.set(i, currentInstruction); 
			}
			/*  Format for all instructions that are not the first /special instruction. */
			else if(i == (dListSize-1)){ 
				currentInstruction.setTurnInstructions(null);
			}
			else if(currentInstruction.getDistance() == 0){ 
				currentInstruction.setTurnInstructions(null);
			}
			else if (currentInstruction.getTurnInstruction() == null){
				
				String newInstruction = "Take a ";  
				String turnInstruction = toTurns(toDegrees(currentState),prevDegree); 
				
				if(turnInstruction != null){ 
					newInstruction += turnInstruction; 
					newInstruction += " to head ";
				} 
				else{ 
					newInstruction = "Head "; 
					currentInstruction.setTurnIcon("Forward");
				}
				newInstruction += toFullWord(currentState);;
				newInstruction += " "; 
				newInstruction += "\n       Walk "; 
				newInstruction += df.format(currentDistance); 
				newInstruction += " feet";
				//newInstruction += "\n-------------";
				currentInstruction.setTurnInstructions(newInstruction);
				if(currentInstruction.getIcon() == null) { 
					currentInstruction.setTurnIcon(turnInstruction);
				}
				prevDegree = toDegrees(currentState); 
			}
			//humanReadableDirections.add(currentInstruction.getTurnInstruction()); 
			dList.set(i, currentInstruction); 
		}
		//humanReadableDirections.add(estimatedTime(totalDistance)); 
		Directions last = new Directions(); 
		String estimatedTime = estimatedTime(totalDistance); 
		last.setTurnInstructions(estimatedTime);
		dList.add(dList.size(), last);
		return dList; 
	}	
	
	private ArrayList<Directions> removeFalseTurns(ArrayList<Directions> directionsList){ 
		int size = directionsList.size();
		ArrayList<Directions> newList = new ArrayList<Directions>(); 
		newList.add(directionsList.get(0));
		int j =0; 
		for(int i =1; i < size; i++ ){ 
			Directions currentDirection = newList.get(j);
			String heading = currentDirection.getHeading(); 
			Directions nextDirection = directionsList.get(i);
			String nextHeading = nextDirection.getHeading();
			if(!heading.equals(nextHeading)){ 
				newList.add(nextDirection);
				j++;
			}
		}
		newList.add(directionsList.get(size-1));
		ArrayList<Directions> withDistances  =findDistances(newList);
		return withDistances; 
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
	

	private double getDistance(double xChange, double yChange){ 
		double xSqr = xChange * xChange; // ^ is a bitwise operator maybe? figured I wouldnt take any chances... 
		double ySqr = yChange * yChange; 
		double sqrt = 3* Math.sqrt(xSqr + ySqr); //(multiply by 3 cause 1 tile = 3 feet. 
		
		return sqrt;
	}
	
	/*
	 * Given a heading and distance outputs Directions
	 */
	private Directions newDirection(String heading, double distance, CellPoint cellPoint, String turnInst){ 
		Directions newEntry = new Directions(); 
		newEntry.setHeading(heading); 
		newEntry.setDistance(distance);
		newEntry.setCellPoint(cellPoint);
		newEntry.setTurnInstructions(turnInst);
		return newEntry; 
	}
	
	/*
	 * Given an ArrayList of (string) States (N,S,E,W,NE,NW,SE,SW), outputs an ArrayList of non-repeating Directions 
	 *  N,N,N,E,E -> N 9, E 6
	 */
	private ArrayList<Directions> route(ArrayList<Directions> states){ 
		int size = states.size();
		ArrayList<Directions> dList = new ArrayList<Directions>();
		double currentDistance =0; 
		
		for(int i = 0; i < size; i++){ 
			Directions current = states.get(i); 
			String currentState = current.getHeading(); 
		
			if(i > 0){ 
				Directions previous = states.get(i-1);
				String previousState = previous.getHeading();  
				if(diffMap(current.getCellPoint(), previous.getCellPoint())){  //when entering AK for the side entrance near 108 this statement does not recognize that it is entering a new cell.... wtf
					String newMap = "New Map, Entering: ";
					String name = current.getCellPoint().getCellName(); 
					current.setTurnInstructions(newMap + name);
					dList.add(previous);
					dList.add(current); 
				}
				else if (!currentState.equals(previousState)){ 
					dList.add(newDirection(previousState, currentDistance,previous.getCellPoint(), null));
				}
				
			}
		}
		ArrayList<Directions> straightenedList = straighten(dList); 
		return straightenedList; 
	}
	
	boolean diffMap (CellPoint current, CellPoint previous){ 
		return!(current.getCellName().equals(previous.getCellName())); 	
	}
	
	// takes out zigzag from a* in world map 
	// looks for slight left and right turns that undo each other
	private ArrayList<Directions> straighten(ArrayList<Directions> states){ 
		for(int i =0; i+2 < states.size(); i++){
		Directions current = states.get(i);
		String cellName = current.getCellPoint().getCellName();
		String currentHeading = current.getHeading(); 
		String futureCellName = states.get(i+2).getCellPoint().getCellName();
			if(cellName.contains("World") && futureCellName.contains("World")){ 
				
				
				String nextHeading = states.get(i+1).getHeading(); 
				String onDeckHeading = states.get(i+2).getHeading(); 
				int currentDegree = toDegrees(currentHeading); 
				int nextDegree = toDegrees(nextHeading); 
				int onDeckDegree = toDegrees(onDeckHeading); 
				String nextTurn = toTurns(nextDegree, currentDegree);
				String onDeckTurn = toTurns(onDeckDegree, nextDegree); 

					try{ 
						if(nextTurn.contains("slight") && onDeckTurn.contains("slight")){ 
							if(nextTurn.contains("left") && onDeckTurn.contains("right")){ 
								states.remove(i); 
							}
							else if(nextTurn.contains("right") && onDeckTurn.contains("left")) {
								states.remove(i+1); 
							}
						}
					}catch(Exception e){ 
						/*
						System.out.println(e);
						System.out.println(nextHeading);
						System.out.println(onDeckHeading); 
						System.out.println(current.getCellPoint().getCellName());
						System.out.println(states.get(i+1).getCellPoint().getCellName());
						System.out.println(states.get(i+2).getCellPoint().getCellName());
						System.out.println(nextDegree);
						System.out.println(onDeckDegree);
						System.out.println(nextTurn);
						*/
					}
				} 
			} 
		return states; 
	}
	
	//Find the distances between all points. 
	private ArrayList<Directions> findDistances(ArrayList<Directions> states){ 
		int size = states.size();
		for(int i = 0; i+1 < size; i++){ 
			CellPoint currentLocation = states.get(i).getCellPoint();
			CellPoint nextLocation = states.get(i+1).getCellPoint();
			 double xDiff = getXDifference(nextLocation, currentLocation);
			 double yDiff = getYDifference(nextLocation, currentLocation); 
			 double distance = getDistance(xDiff, yDiff);
			if(currentLocation.getCellName().equals(nextLocation.getCellName())){ 
				 states.get(i).setDistance(distance);	 
			 }
			 
			 currentLocation = nextLocation;
		}
		return states; 
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
		else {
			return null;
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


