package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.team1ofus.apollo.TILE_TYPE;

import core.DebugManagement;
import core.SEVERITY_LEVEL;
import pathing.PathCell;
import tiles.Tile;
import ui.CellRenderer;

//JPanel that holds the rendered map
public class MapPane extends JPanel{
	public CellRenderer render;
	
	public MapPane(PathCell inCell) {
		if(inCell.getName().contains("World")) {
			File campusFile = new File("wpi_campus_map.jpg");
			Image img;
			try {
				img = ImageIO.read(campusFile);
				render = new CellRenderer(inCell, this.getWidth(), this.getHeight(), img);
				
			} catch (IOException e) {
				DebugManagement.writeLineToLog(SEVERITY_LEVEL.CRITICAL, "Unable to load wpi_campus_map.jpg.");
				render = new CellRenderer(inCell, this.getWidth(), this.getHeight(), null);
			}
			
		} else {
			render = new CellRenderer(inCell, this.getWidth(), this.getHeight(), null);
		}
		
	}
	
	public void paintComponent(Graphics g) {
		render.renderTiles(g, this.getWidth(), this.getHeight());
	}
}
