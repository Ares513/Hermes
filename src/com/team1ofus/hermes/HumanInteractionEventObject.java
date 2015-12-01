package com.team1ofus.hermes;
import java.util.*;

//TODO Comments1
public class HumanInteractionEventObject { 
	public HumanInteractionEventObject() {
	}
	
	private List<IHumanInteractionListener> listeners = new ArrayList<IHumanInteractionListener>();
	
	public void addListener(IHumanInteractionListener toAdd){
		listeners.add(toAdd);
		}
	public void doClick(int x, int y){
		
		for(IHumanInteractionListener UL : listeners){
			UL.onTileClicked(x, y);
			System.out.println("listeners called");
		}
	
	}
}