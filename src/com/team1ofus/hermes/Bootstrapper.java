
package com.team1ofus.hermes;

import java.awt.EventQueue;
import java.util.ArrayList;

import com.team1ofus.apollo.DataManagement;
import com.team1ofus.hermes.UIManagement;

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
					UIManagement ui;
					DataManagement data = new DataManagement();
					ArrayList<PathCell> pathCells = CellConverter.convertList(data.getCells());
					
					MapManagement map;			
					data = new DataManagement();
					

					map = new MapManagement(pathCells);
					
					ui = new UIManagement(pathCells);
					addEventHandlers(data, map, ui);
					ui.begin();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			private void addEventHandlers(DataManagement data, MapManagement map, UIManagement UI) {
				UI.events.addManagementListener(map);
				map.events.addListener(UI);
			}
		});
	}
	
	
}
//
//public class Bootstrapper {
//	
//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					UIManagement UI;
//					DataManagement data = new DataManagement(); //data will load all cells, in the meantime dummy data
//					ArrayList<PathCell> pathCells = new ArrayList<PathCell>();
//					for(int i=0; i< data.getCells().size(); i++) {
//						com.team1ofus.apollo.Cell current = data.getCells().get(i);
//						PathCell converted = new PathCell(current.getID(),  current.getWidth(), current.getHeight(), current.getScale(), current.getTiles());
//						
//						pathCells.add(converted);
//			
//					}
////					ArrayList<PathCell> dummyList = new ArrayList<PathCell>();
////					dummyList.add(new PathCell("Test", 10, 10, 1.0, TILE_TYPE.WALL));
//					MapManagement MapManager;
//					data = new DataManagement();
//					MapManager = new MapManagement(pathCells);
//					UI = new UIManagement(pathCells);
//					
//					UI.events.managementListeners.add(MapManager);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//	
//}
