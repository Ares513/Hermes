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
		SplashScreen splash = new SplashScreen(1000); //X000 -> x seconds 
		splash.showSplashAndExit();	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {				    
				    // Normally, we'd call splash.showSplash() and get on with the program.
				    // But, since this is only a test...
					UIManagement ui;
					DataManagement data = new DataManagement();
					ArrayList<PathCell> pathCells = CellConverter.convertList(data.getCells());
					MapManagement map;			
					data = new DataManagement();
					map = new MapManagement();	
					ui = new UIManagement(pathCells);
					addEventHandlers(data, map, ui);
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
