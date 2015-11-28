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
public class CellRenderer implements IZoomCellRenderListener {
	//I changed this from a final int, to a private double
	private int width = BootstrapperConstants.TILE_WIDTH;
	private int height = BootstrapperConstants.TILE_HEIGHT;
	final int rows = 1;
	final int cols = 3;
	BufferedImage[] spriteImages = new BufferedImage[rows * cols];
	Point offset = new Point(0, 0);
	PathCell drawnCell;
	private Point first;
	private Point second;
	private int panelSize = 230;
	public CellRenderer(PathCell inCell) {
		drawnCell = inCell;
		getFromSheet();
	}
	
	public void onZoomPass(double scale){
		width =  (int)(BootstrapperConstants.TILE_WIDTH * scale);
		height = (int)(BootstrapperConstants.TILE_HEIGHT * scale);
	}
	
	public void zoom(double scale){
		width =  (int)(BootstrapperConstants.TILE_WIDTH * scale);
		height = (int)(BootstrapperConstants.TILE_HEIGHT * scale);
	}
	
	public void renderTiles(Graphics g) {
		
		for(int i=0; i<drawnCell.tiles.length; i++) {
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
		
		if(first != null) {
			g.drawImage(spriteImages[2], first.x*width-offset.x, first.y*height-offset.y, width, height, null);
		}
		if(second != null) {
			g.drawImage(spriteImages[2], second.x*width-offset.x, second.y*height-offset.y, width, height, null);
		}
	}
	
	public void setFirst(Point inPoint) {
		DebugManagement.writeNotificationToLog("First point in CellRenderer set.");
		first = inPoint;
	}
	public void setSecond(Point inPoint) {
		DebugManagement.writeNotificationToLog("Second point in CellRenderer set.");
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
		int x = (int) (Math.round((mouseX + offset.x)/width));
		int y = (int) (Math.round((mouseY + offset.y)/height));
		return new Point(x,y);
	}
	public Tile getTile(int x, int y) {
		int xActual = x;
		int yActual = y;
		int actualWidth = drawnCell.tiles.length - 1;
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
		} else if(offset.x > drawnCell.tiles.length * width - (windowWidth -panelSize)) {
			int tileCount = drawnCell.tiles.length;
			int maxX = drawnCell.tiles.length * width - (windowWidth - panelSize);
			offset.x = maxX ;
		}
		//the panelSizae is the size of the side panel. If we need to change that, alter that variable.
		if(offset.y < 0) {
			offset.y = 0;
		} else if(offset.y > drawnCell.tiles[1].length * height - windowHeight) {
			offset.y = drawnCell.tiles[1].length * height - windowHeight; 
		
		}	
	}
}
