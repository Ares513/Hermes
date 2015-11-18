package com.team1ofus.hermes;
import java.util.*;
public class HermesHumanInteractiveEvent { 
	public HermesHumanInteractiveEvent() {
	}
	
	private List<HermesUIInterface> listeners = new ArrayList<HermesUIInterface>();
	
	public void addListener(HermesUIInterface toAdd){
		listeners.add(toAdd);
		}
	public void doClick(){
		System.out.println("clicked");
		
		for(HermesUIInterface UL : listeners)
			UL.thereWasAClick();
	
	}
}