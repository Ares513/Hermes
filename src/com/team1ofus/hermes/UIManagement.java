package com.team1ofus.hermes;

import javax.swing.*;


public class UIManagement {
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
	
	
	
	
	void initialize(){
		window = new HermesUI();
	}

}
