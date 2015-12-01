package com.team1ofus.hermes;

import java.io.IOException;

import javax.swing.JLayeredPane;
import javax.swing.border.EtchedBorder;

public class MapTabPane extends JLayeredPane {
	
	MapPane gridMap;
	PathPane pathPanel;
	TextPane textPanel;
	PointPane pointPanel;
	String name;
	PathCell currentCell;
	
	public MapTabPane(PathCell cell) {
		currentCell = cell;
		gridMap = new MapPane(currentCell);
		pathPanel = new PathPane();
		textPanel = new TextPane();
		try {
			pointPanel = new PointPane();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	
	private void initialize() {
		gridMap.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		gridMap.setBounds(0, 0, BootstrapperConstants.FRAME_WIDTH-BootstrapperConstants.PANEL_SIZE, BootstrapperConstants.FRAME_HEIGHT);
		pathPanel.setBounds(0, 0, BootstrapperConstants.FRAME_WIDTH-BootstrapperConstants.PANEL_SIZE, BootstrapperConstants.FRAME_HEIGHT);
		textPanel.setBounds(0, 0, BootstrapperConstants.FRAME_WIDTH-BootstrapperConstants.PANEL_SIZE, BootstrapperConstants.FRAME_HEIGHT);
		pointPanel.setBounds(0, 0, BootstrapperConstants.FRAME_WIDTH-BootstrapperConstants.PANEL_SIZE, BootstrapperConstants.FRAME_HEIGHT);
		textPanel.labelAllTiles(currentCell);
		
		add(gridMap);
		add(pathPanel);
		add(textPanel);
		add(pointPanel);
		setComponentZOrder(gridMap, 0);
		setComponentZOrder(pathPanel, 0);
		setComponentZOrder(textPanel, 0);
		setComponentZOrder(pointPanel, 0);
	}

	public MapPane getGridMap() {
		return gridMap;
	}

	public PathPane getPathPanel() {
		return pathPanel;
	}

	public TextPane getTextPanel() {
		return textPanel;
	}

	public PointPane getPointPanel() {
		return pointPanel;
	}

	public String getName() {
		return name;
	}

	public PathCell getCurrentCell() {
		return currentCell;
	}
	
	

}
