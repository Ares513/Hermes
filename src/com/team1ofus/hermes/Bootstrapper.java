package com.team1ofus.hermes;

import java.awt.EventQueue;
import com.team1ofus.hermes.UIManagement;

public class Bootstrapper {
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// HermesHumanInteractiveEvent init = new HermesHumanInteractiveEvent(); 
					UIManagement UI;
					UI = new UIManagement();
					
					init.addListener(UI);
					init.doClick();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
