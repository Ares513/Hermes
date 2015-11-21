package com.team1ofus.hermes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
	Point offset = new Point(0, 0);
	PathCell drawnCell;
	private Point first;
	private Point second;
	public CellRenderer(PathCell inCell) {
		drawnCell = inCell;
		getFromSheet();
	}
	
	public void renderTiles(Graphics g) {
		
		for(int i=0; i<drawnCell.tiles[0].length; i++) {
			for(int j=0; j<drawnCell.tiles[1].length; j++) {
				switch(drawnCell.tiles[i][j].getTileType()) {
				case WALL:
						g.drawImage(spriteImages[0], i*width - offset.x, j*height - offset.y, width, height, null);
					
					break;
					
				case PEDESTRIAN_WALKWAY:
						g.drawImage(spriteImages[1], i*width - offset.x, j*height - offset.y, width, height, null);
					
					break;
				}
			}
		}
		
		g.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		g.drawString(offset.toString(), 0, 0);
	}
	
	public void setFirst(Point inPoint) {
		first = inPoint;
	}
	public void setSecond(Point inPoint) {
		second = inPoint;
	}
	private void getFromSheet(){
		try{
		 BufferedImage spriteSheet = ImageIO.read(new File("Sprites.png"));
		 
		 for (int i = 0; i < rows; i++){
			    for (int j = 0; j < cols; j++){
			    	spriteImages[(i * cols) + j] = spriteSheet.getSubimage(j * (width + 8),i * (height + 8),width,height);
			    }
			}
		 }
		catch (IOException e) {
	       throw new RuntimeException(e);
		}	
	}
	
	public Point pickTile(int mouseX, int mouseY) {
		int x = (int) (Math.floor((mouseX - offset.x)/(width/2)));
		int y = (int) (Math.floor((mouseY - offset.y)/(height/2)));
		return new Point(x,y);
	}
	public Tile getTile(int x, int y) {
		int xActual = x;
		int yActual = y;
		int actualWidth = drawnCell.tiles[0].length - 1;
		if(x > actualWidth) {
			xActual = actualWidth;
		}
		int actualHeight = drawnCell.tiles[1].length - 1;
		if(y > actualHeight) {
			yActual = actualHeight;
		}

		return drawnCell.tiles[xActual][yActual];
	}
	public void incrementOffset(int dx, int dy, int windowWidth, int windowHeight) {
		//some optimizations to be made here
		DebugManagement.writeNotificationToLog("Offset is : " + offset.toString());
		offset.translate(dx, dy);
		if(offset.x < 0) {
			offset.x = 0;
		} else if(offset.x > drawnCell.tiles[0].length * width - windowHeight) {
			int tileCount = drawnCell.tiles[0].length;
			int maxX = drawnCell.tiles[0].length * width - windowHeight;
			offset.x = maxX ;
		}
		if(offset.y < 0) {
			offset.y = 0;
		} else if(offset.y > drawnCell.tiles[1].length * height - windowHeight) {
			offset.y = drawnCell.tiles[1].length * height - windowHeight; 
		
		}
		
	}
}
