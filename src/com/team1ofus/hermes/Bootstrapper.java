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
					UIManagement ui;
					DataManagement data = new DataManagement();
					ArrayList<PathCell> pathCells = CellConverter.convertList(data.getCells());
					
					MapManagement map;			
					data = new DataManagement();
					

					map = new MapManagement();
					
					ui = new UIManagement(pathCells);
					addEventHandlers(data, map, ui);
					ui.begin();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			private void addEventHandlers(DataManagement data, MapManagement map, UIManagement ui) {
				ui.events.addManagementListener(map);
				map.events.addListener(ui);
			}
		});
	}
	
	
}
