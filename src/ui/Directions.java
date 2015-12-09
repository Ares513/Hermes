package ui;

import java.lang.String;
import pathing.CellPoint;
//An object used for storing direction information. This will be used when printing out directions to the user
public class Directions { 
	private String heading; 
	private String turnInstruction; 
	private double distance; 
	private CellPoint cellPoint; 
	private String iconType; 
	
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
	
	public CellPoint getCellPoint(){ 
		return this.cellPoint; 
	}
	public String getIcon() { 
		return this.iconType; 
	}
	
	public void setHeading(String newHeading){ 
		this.heading = newHeading;
	}
	
	public void setTurnInstructions (String newTurnInstruction){ 
		this.turnInstruction = newTurnInstruction; 
	}
	
	public void setDistance (double newDistance){ 
		this.distance = newDistance; 
	}
	
	public void setCellPoint (CellPoint newCellPoint){ 
		this.cellPoint = newCellPoint; 
	}
	
	public void setTurnIcon(String turnInstruction){ 
		this.iconType = turnInstruction + ".png"; 
	}
}