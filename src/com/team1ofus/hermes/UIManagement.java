package com.team1ofus.hermes;

import javax.swing.*;


public class UIManagement implements HermesUIInterface{
	HermesUI window;
	public UIManagement(){
		initialize();    
	}
	
	public JFrame frame; 
	/*
	 * public render CellRender; 
	 * public Search searchUI
	 * 
	 */
	
	/*
	 *  public void add(int val1, int val2){
     *      synchronized(this){
     *         this.sum1 += val1;   
     *       }
     *      synchronized(this){
     *         this.sum2 += val2;
     *       }
     *    }
	 * 
	 */
	public void render(){ 
		synchronized(this){ 
		/*
		 * Stuff goes here
		 */
		}
	}
	
	@Override
	public void thereWasAClick(){
		System.out.println("Holy Shit There Was A Click");
	}
	
	void initialize(){
		window = new HermesUI();
		window.humanInteractive.addListener(this);
		window.initialize();
	}

}
