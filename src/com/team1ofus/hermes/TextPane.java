package com.team1ofus.hermes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
/*
 * Intended to be efficient at drawing text in a standardized fashion over the screen.
 * Add support for offset needed as a future task.
 */
public class TextPane extends JPanel {
	ArrayList<TextLocation> locations = new ArrayList<TextLocation>();
	public TextPane() {
		setOpaque(true);

	}
	void drawPath(CellPoint[] path){
	}
	public void paintComponent(Graphics g) {
		this.paintComponents(g);
		for(TextLocation l : locations) {
			g.drawString(l.input, l.location.x, l.location.y);
		}
	}
	/*
	 * For debug purposes marks all cells.
	 */
	public void labelAllTiles(PathCell target) {
		int i=0;
		int j=0;
		while(i < target.tiles.length) {
			j = 0;
			while(j < target.tiles[i].length) {
				//locations.add(new TextLocation(i + "," + j, new Point(i*BootstrapperConstants.TILE_WIDTH, j*BootstrapperConstants.TILE_HEIGHT)));
				j = j + 5;
			}
			i = i + 5;
		}
				
			
	}
	class TextLocation {
		String input;
		Point location;
		public TextLocation(String input, Point location) {
			this.input = input;
			this.location = location;
		}
	}
}
