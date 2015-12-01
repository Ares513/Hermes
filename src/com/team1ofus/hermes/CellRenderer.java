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
	private int width = BootstrapperConstants.TILE_WIDTH;
	private int height = BootstrapperConstants.TILE_HEIGHT;
	public double prevScale = 1D;
	int oldTotalWidth;
	int oldTotalHeight;
	int newTotalWidth;
	int newTotalHeight;
	int difWidth;
	int difHeight;	
	final int rows = 4;
	final int cols = 14;
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

	//Changes the width and height of tiles to corresponding to changes in the scale of zooming
	public void zoom(double scale, int fwidth, int fheight){
		width =  (int)(BootstrapperConstants.TILE_WIDTH * scale);
		height = (int)(BootstrapperConstants.TILE_HEIGHT * scale);

		DebugManagement.writeNotificationToLog("The previous scale was");
		System.out.println(prevScale);

		
		oldTotalWidth= (int)((BootstrapperConstants.TILE_WIDTH * drawnCell.tiles.length ) * prevScale);
		//DebugManagement.writeNotificationToLog("The previous total width was:");
		//System.out.println(oldTotalWidth);
		oldTotalHeight= (int)((BootstrapperConstants.TILE_HEIGHT * drawnCell.tiles[1].length ) * prevScale);
		//DebugManagement.writeNotificationToLog("The previous total height was:");
		//System.out.println(oldTotalHeight);
		newTotalWidth= (int)((BootstrapperConstants.TILE_WIDTH * drawnCell.tiles.length ) * scale);
		//DebugManagement.writeNotificationToLog("The current total width is:");
		//System.out.println(newTotalWidth);
		newTotalHeight= (int)((BootstrapperConstants.TILE_HEIGHT * drawnCell.tiles[1].length ) * scale);
		//DebugManagement.writeNotificationToLog("The current total height is:");
		//System.out.println(newTotalHeight);
		difWidth = (newTotalWidth - oldTotalWidth)/2;
		//DebugManagement.writeNotificationToLog("The current x offset is:");
		//System.out.println(difWidth);
		difHeight = (newTotalHeight - oldTotalHeight)/2;
		//DebugManagement.writeNotificationToLog("The current y offset is:");
		//System.out.println(difHeight);
		
		//NEED TO GET difWidth and difHeight over to pathpane to add to its offsets in drawLine
		incrementOffset(difWidth,difHeight, fwidth, fheight);
		prevScale = scale;

	}

	//Renders the tiles
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

				case DOOR:
					g.drawImage(spriteImages[2], i*width - offset.x, j*height - offset.y, width, height, null);
					break;
				case GRASS:
					g.drawImage(spriteImages[3], i*width - offset.x, j*height - offset.y, width, height, null);
					break;
				case CONGESTED:
					g.drawImage(spriteImages[4], i*width - offset.x, j*height - offset.y, width, height, null);
					break;
				case VERTICAL_UP_STAIRS:
					g.drawImage(spriteImages[5], i*width - offset.x, j*height - offset.y, width, height, null);
					break;
				case VERTICAL_DOWN_STAIRS:
					g.drawImage(spriteImages[6], i*width - offset.x, j*height - offset.y, width, height, null);
					break;
				case HORIZONTAL_LEFT_STAIRS:
					g.drawImage(spriteImages[7], i*width - offset.x, j*height - offset.y, width, height, null);
					break;
				case HORIZONTAL_RIGHT_STAIRS:
					g.drawImage(spriteImages[8], i*width - offset.x, j*height - offset.y, width, height, null);
					break;
				case IMPASSABLE:
					//9 is Steep, which is now deprecated
					g.drawImage(spriteImages[10], i*width - offset.x, j*height - offset.y, width, height, null);
				case MALE_BATHROOM:
					g.drawImage(spriteImages[11], i*width - offset.x, j*height - offset.y, width, height, null);
				case FEMALE_BATHROOM:
					g.drawImage(spriteImages[12], i*width - offset.x, j*height - offset.y, width, height, null);
					break;
				case UNISEX_BATHROOM:
					g.drawImage(spriteImages[13], i*width - offset.x, j*height - offset.y, width, height, null);
					break;
				case BENCH:
					g.drawImage(spriteImages[14], i*width - offset.x, j*height - offset.y, width, height, null);
					break;
				case TREE:
					g.drawImage(spriteImages[18], i*width - offset.x, j*height - offset.y, width, height, null);
					break;
				}
			}
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
	//Draws the tiles based on the sprites from our sprite sheet
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

	//Handles tile picking, which will then be passed to A*
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
		} else if(offset.x > drawnCell.tiles.length * width - (windowWidth)) {
			int tileCount = drawnCell.tiles.length;
			int maxX = drawnCell.tiles.length * width - (windowWidth);
			offset.x = maxX ;
		}
		//The panelSizae is the size of the side panel. If we need to change that, alter that variable.
		if(offset.y < 0) {
			offset.y = 0;
		} else if(offset.y > drawnCell.tiles[1].length * height - windowHeight) {
			offset.y = drawnCell.tiles[1].length * height - windowHeight; 

		}	
	}
}