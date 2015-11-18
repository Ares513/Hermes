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
					UIManagement UI;
					UI = new UIManagement();
				//	UI.managementEvent.addManagementListener(null); // needs an actual listener 
					DataManagement DMGR;
					DMGR = new DataManagement();
					
					MapManagement MapManager;
					MapManager = new MapManagement();
					MapManager.getAStarPathCompletedEvent().registerListener(UI);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
