package com.team1ofus.hermes;

import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;
import com.team1ofus.hermes.Tile;
import com.team1ofus.hermes.TILE_TYPE;
import com.team1ofus.hermes.CellRenderer;


public class DrawMap extends JPanel{
	CellRenderer render;
	public DrawMap() {
		//test data
		TILE_TYPE[][] dummyData;
		dummyData = new TILE_TYPE[50][50];
		
		for(int i=0; i<50; i++) {
			for(int j=0; j<50; j++) {
				dummyData[i][j] = TILE_TYPE.WALL;
			}
		}
		dummyData[5][5] =  TILE_TYPE.PEDESTRIAN_WALKWAY;

		render = new CellRenderer(dummyData);
	}
	public void paintComponent(Graphics g) {
		render.renderTiles(g);
	}
}
