package com.team1ofus.hermes;

import java.awt.Point;

/* This object is designed to allow other cells to contain references to EntryPoint objects held by other cells. 
 * (A cell name and an entry point id uniquely identifies any EntryPoint.)
 * Note that EntryPointReference objects do not know what cell they're in; a program that needs to know that 
 * must instead figure out the name of the cell it got an EntryPointReference from. 
 * Author Forrest Cinelli
 */
public class EntryPointReference {
	//id of the Entry Point this object references.
	private String entryPointID;
	//name of the Cell which contains the Entry Point this object references. 
	//Note: this is NOT the cell that the EntryPointReference is contained in.
	private String targetCell;
	//x,y location of the exit in the cell that contains this object.
	private Point loc;
	
	public EntryPointReference(String anId, String aCellName, Point aLoc){
		entryPointID = anId;
		targetCell = aCellName;
		loc = aLoc;
	}
	public String getEntryPointID() {
		return entryPointID;
	}
	public String getTargetCell(){
		return targetCell; //Not the cell that holds this EntryPointReferenceObject!!
	}
	public Point getLoc(){
		return loc;
	}
}
