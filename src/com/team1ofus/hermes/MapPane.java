package com.team1ofus.hermes;

import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;
import com.team1ofus.hermes.Tile;
import com.team1ofus.hermes.TILE_TYPE;
import com.team1ofus.hermes.CellRenderer;


public class MapPane extends JPanel implements IZoomInteractionListener{
	public CellRenderer render;
	private ZoomDrawMapEventObject zoomPass;
	
	public MapPane(PathCell inCell) {
		render = new CellRenderer(inCell);
	}
	public void onZoomEvent(double scale){
		zoomPass.addListener(render);
		zoomPass.doZoomPass(scale);
		
		//calls event  to pass on scale to CellRenderer
		//add render to listeners for next object
		//call other event objecst function
	}
	public void paintComponent(Graphics g) {
		render.renderTiles(g);
	}
}
