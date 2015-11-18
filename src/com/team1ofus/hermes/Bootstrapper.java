package com.team1ofus.hermes;

import java.awt.EventQueue;
import java.util.ArrayList;

import com.team1ofus.apollo.DataManagement;
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
					DataManagement data; //data will load all cells, in the meantime dummy data
					ArrayList<PathCell> dummyList = new ArrayList<PathCell>();
					dummyList.add(new PathCell("Test", 10, 10, 1.0, TILE_TYPE.WALL));
					MapManagement MapManager;
					UI = new UIManagement(dummyList);
				//	UI.managementEvent.addManagementListener(null); // needs an actual listener 
					
					data = new DataManagement();
					

					MapManager = new MapManagement(dummyList);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
