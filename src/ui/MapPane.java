package ui;

import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;

import com.team1ofus.apollo.TILE_TYPE;

import pathing.PathCell;
import tiles.Tile;
import ui.CellRenderer;

//JPanel that holds the rendered map
public class MapPane extends JPanel{
	public CellRenderer render;
	
	public MapPane(PathCell inCell) {
		render = new CellRenderer(inCell);
	}
	
	public void paintComponent(Graphics g) {
		render.renderTiles(g, this.getWidth(), this.getHeight());
	}
}
