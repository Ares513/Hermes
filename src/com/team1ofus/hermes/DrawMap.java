package com.team1ofus.hermes;

import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;
import com.team1ofus.hermes.Tile;
import com.team1ofus.hermes.TILE_TYPE;
import com.team1ofus.hermes.CellRenderer;


public class DrawMap extends JPanel{
	CellRenderer render;
	public DrawMap(PathCell inCell) {
		render = new CellRenderer(inCell);
	}
	public void paintComponent(Graphics g) {
		render.renderTiles(g);
	}
}
