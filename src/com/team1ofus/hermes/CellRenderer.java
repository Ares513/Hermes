package com.team1ofus.hermes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/*
 * Pasted from Apollo
 */
public class CellRenderer {
	final int width = 32;
	final int height = 32;
	final int rows = 1;
	final int cols = 3;
	BufferedImage[] spriteImages = new BufferedImage[rows * cols];
	TILE_TYPE[][] tiles;

	
	
	public CellRenderer(TILE_TYPE[][] tiles) {
		this.tiles = tiles;
		getFromSheet();
	}
	
	public void renderTiles(Graphics g) {
		ControlPanel cp = new ControlPanel();
		for(int i=0; i<tiles[0].length; i++) {
			for(int j=0; j<tiles[1].length; j++) {
				switch(tiles[i][j]) {
				case WALL:
					g.drawImage(spriteImages[0], i*width + cp.p1.x, j*height + cp.p1.y, width, height, null);
					break;
					
				case PEDESTRIAN_WALKWAY:
					g.drawImage(spriteImages[1], i*width + cp.p1.x, j*height + cp.p1.y, width, height, null);
					break;
				}
			}
		}
	}
	
	private void getFromSheet(){
		try{
		 BufferedImage spriteSheet = ImageIO.read(new File("Sprites.png"));
		 
		 for (int i = 0; i < rows; i++){
			    for (int j = 0; j < cols; j++){
			    	spriteImages[(i * cols) + j] = spriteSheet.getSubimage(j * width,i * height,width,height);
			    }
			}
		 }
		catch (IOException e) {
	       throw new RuntimeException(e);
		}	
	}
}
