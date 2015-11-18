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
					DataManagement data = new DataManagement(); //data will load all cells, in the meantime dummy data
					ArrayList<PathCell> pathCells = new ArrayList<PathCell>();
					for(int i=0; i< data.getCells().size(); i++) {
						
						com.team1ofus.apollo.Cell current = data.getCells().get(i);
						PathCell converted = new PathCell(current.getID(),  current.getWidth(), current.getHeight(), current.getScale(), TILE_TYPE.WALL);
						
			
					}
					ArrayList<PathCell> dummyList = new ArrayList<PathCell>();
					dummyList.add(new PathCell("Test", 10, 10, 1.0, TILE_TYPE.WALL));
					MapManagement MapManager;
					
				//	UI.managementEvent.addManagementListener(null); // needs an actual listener 
					
					data = new DataManagement();
					

					MapManager = new MapManagement(dummyList);
					UI = new UIManagement(dummyList);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
