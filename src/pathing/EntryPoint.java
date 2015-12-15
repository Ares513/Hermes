package pathing;

import java.awt.Point;

/* This object is designed to allow other cells to reference a place in a cell without having to know it exactly. 
 * For example, if the 'world' cell wants to reference the front door of the 'ak' cell, instead of having to know the exact 
 * x,y coordinates inside ak of that point, it can instead reference the EntryPoint object whose ID is 'AK Front Door', and let
 * that EntryPoint object keep track of exactly where inside AK the door leads to.
 * Author Forrest Cinelli
 */
public class EntryPoint{
	//id string should be a human-readable string that describes the entry point; i.e. 'AK Front Door' or 'AK Side Door'
	private String id;
	//loc stores the xy coordinates where you appear when you enter through this entrypoint
	private Point loc;
	
	public EntryPoint(String anId, Point aLoc){
		id = anId;
		loc = aLoc;
	}
	public String getId(){
		return id;
	}
	public Point getLoc(){
		return loc;
	}
}
