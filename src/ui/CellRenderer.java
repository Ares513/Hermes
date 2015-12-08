package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.sun.org.apache.bcel.internal.generic.MULTIANEWARRAY;
import com.team1ofus.apollo.TILE_TYPE;

import core.BootstrapperConstants;
import core.DebugManagement;
import pathing.PathCell;
import tiles.Tile;

/*
 * Pasted from Apollo
 */
public class CellRenderer {
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int frameWidth = screenSize.width - 200;
	private int frameHeight = screenSize.height - 200;
	private int width = BootstrapperConstants.TILE_WIDTH;
	private int height = BootstrapperConstants.TILE_HEIGHT;
	public double prevScale = 1;
	int oldTotalWidth;
	int oldTotalHeight;
	int newTotalWidth;
	int newTotalHeight;
	int difWidth;
	int difHeight;
	int finalHeight;
	int finalWidth;
	final int rows = 2;
	final int cols = 14;
	BufferedImage[] spriteImages = new BufferedImage[rows * cols];
	Point offset = new Point(0, 0);
	PathCell drawnCell;
	private Point first;
	private Point second;
	private int panelSize = 230;
	private double scale = 1;
	private HashMap<Point, TILE_TYPE> bufferedTiles = new HashMap<Point, TILE_TYPE>();
	private Image campusMap;
	private boolean isCampusMap;
	public CellRenderer(PathCell inCell, int windowWidth, int windowHeight, Image campusMap) {
		drawnCell = inCell;
		if(campusMap != null) {
			this.campusMap = campusMap;
			isCampusMap = true;
		}
		for(Point p : drawnCell.tiles.keySet()) {
			bufferedTiles.put(p, drawnCell.tiles.get(p).getTileType());
		}
	
		// might need to
		//offset = getMapCenter();
		//offset = new Point(offset.x - windowWidth/2, offset.y - windowHeight/2);
		
		getFromSheet();
	}

	// Changes the width and height of tiles to corresponding to changes in the
	// scale of zooming
	public void zoom(double scale, int windowWidth, int windowHeight) {
		prevScale = this.scale;
		//width = (int) (BootstrapperConstants.TILE_WIDTH * scale);
		//height = (int) (BootstrapperConstants.TILE_HEIGHT * scale);
		oldTotalWidth = (int) ((BootstrapperConstants.TILE_WIDTH * drawnCell.getWidth()) * prevScale);
		oldTotalHeight = (int) ((BootstrapperConstants.TILE_HEIGHT * drawnCell.getHeight()) * prevScale);
		Point2D.Double sizePercentage = new Point2D.Double(offset.x / getMapWidth(), offset.y / getMapHeight());
		DebugManagement.writeNotificationToLog("Size percentage " + sizePercentage.x  + " " + sizePercentage.y);
		this.scale = scale;
		newTotalWidth = (int) ((BootstrapperConstants.TILE_WIDTH * drawnCell.getWidth()) * scale);
		newTotalHeight = (int) ((BootstrapperConstants.TILE_HEIGHT * drawnCell.getHeight()) * scale);
		
		Point2D.Double postScaling = new Point2D.Double(sizePercentage.x * getMapWidth(), sizePercentage.y * getMapHeight());
		DebugManagement.writeNotificationToLog("Post scaling " + sizePercentage.x + " " + sizePercentage.y);
		Point2D.Double center = new Point2D.Double(offset.x + windowWidth / 2 , offset.y + windowHeight / 2);
		Point2D.Double preciseCenter = getPreciseMapCenter();
		Point2D.Double difference = new Point2D.Double(preciseCenter.x - center.x, preciseCenter.y - center.y);
		Point mapCenter = getMapCenter();
		Point oldOffset = offset;
		difHeight = ((newTotalHeight - oldTotalHeight) / 2);
		difWidth = (((newTotalWidth - oldTotalWidth) / 2));
		finalWidth = difWidth + (int) (difWidth * (scale - 1));
		finalHeight = difHeight + (int) (difHeight * (scale - 1));
	
		int newX = (int)(center.x*(scale - 3) + scale*offset.x);
		 int newY = (int)(center.y*(scale - 3) + scale*offset.y);
		 //offset = new Point(newX, newY);
		//offset = new Point((int)(mapCenter.x - windowWidth / 2 - difWidth), (int) (mapCenter.y - windowHeight / 2 - difHeight));
		//offsetToTile(pickTile(center.x, center.y), windowWidth, windowHeight);
		//offset = new Point(oldOffset.x - mapCenter.x - windowWidth/2, oldOffset.y - mapCenter.y - windowHeight/2);
	}
	private Point tilesOnScreen(int windowWidth, int windowHeight) {
		int lowerX = -1 + (int) Math.ceil(offset.x / (BootstrapperConstants.TILE_WIDTH * scale));
		int lowerY = -1 + (int) Math.ceil(offset.y / (BootstrapperConstants.TILE_HEIGHT * scale));
		int higherX = 1 + (int) (Math.ceil(offset.x + windowWidth) / (BootstrapperConstants.TILE_WIDTH * scale));
		int higherY = 1 + (int) (Math.ceil(offset.y + windowHeight) / (BootstrapperConstants.TILE_HEIGHT * scale));
		return new Point(higherX - lowerX, higherY - lowerY);
	}
	//Snaps the offset to a specified tile location.
	private void offsetToTile(Point tile, int windowWidth, int windowHeight) {
		Point pixelTileCoords = new Point((int)(tile.x * BootstrapperConstants.TILE_WIDTH * scale), (int)(tile.y * BootstrapperConstants.TILE_HEIGHT * scale));
		Point adjusted = new Point(pixelTileCoords.x - windowWidth/2 , pixelTileCoords.y - windowHeight / 2);
		offset = adjusted;
	}
	// Renders the tiles
	public void renderTiles(Graphics g, int windowWidth, int windowHeight) {
		//render based on what's most efficient for the zoom level.
		if (isCampusMap) {
			//do campus specific rendering
			renderByImage(g, windowWidth, windowHeight);
		} else {
			renderByKeySet(g, windowWidth, windowHeight);
		}
	}
	public Point getCenter(int windowWidth, int windowHeight) {
		return new Point(offset.x + windowWidth / 2 , offset.y + windowHeight / 2);
	}
	public Point getMapCenter() {
		return new Point(getMapWidth()/2, getMapHeight()/2);
	}
	public Point2D.Double getPreciseMapCenter() {
		return new Point2D.Double((drawnCell.getWidth() * BootstrapperConstants.TILE_WIDTH * scale), (drawnCell.getHeight() * BootstrapperConstants.TILE_HEIGHT * scale));
		
	}
	public int getMapWidth() {
		return (int)(drawnCell.getWidth() * BootstrapperConstants.TILE_WIDTH * scale);
	}
	public int getMapHeight() {
		return (int)(drawnCell.getHeight() * BootstrapperConstants.TILE_HEIGHT * scale);
	}
	private void renderByImage(Graphics g, int windowWidth, int windowHeight) {
		Graphics2D g2d = (Graphics2D) g;
	
		//g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		AffineTransform transformer = new AffineTransform();
		transformer.translate(windowWidth/2, windowHeight/2);
		
		transformer.scale(scale, scale);
		transformer.translate(-windowWidth/2, -windowHeight/2);
		transformer.translate(offset.x, offset.y);
		g2d.setTransform(transformer);
		g2d.drawImage(campusMap, 0, 0, drawnCell.getWidth() * BootstrapperConstants.TILE_WIDTH, drawnCell.getHeight() * BootstrapperConstants.TILE_HEIGHT, null);
	}
	private void renderByKeySet(Graphics g, int windowWidth, int windowHeight) {
		Graphics2D g2d = (Graphics2D) g;
		long startTime = System.nanoTime(); //measure time for each run
		g2d.setColor(new Color(128, 0, 0));
		g2d.fillRect(0,0, windowWidth, windowHeight);
	
		//g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		AffineTransform transformer = new AffineTransform();
		transformer.translate(windowWidth/2, windowHeight/2);
		
		transformer.scale(scale, scale);
		transformer.translate(-windowWidth/2, -windowHeight/2);
		transformer.translate(offset.x, offset.y);
		g2d.setTransform(transformer);
		System.out.println(bufferedTiles.entrySet().size());
		Point2D scaled = null;
		Point2D origin = null;
		Point2D target = null;
		
		
		for (Map.Entry<Point, TILE_TYPE> e : bufferedTiles.entrySet()) {
		
				
				
				
		
		
				
				origin = transformer.transform(new Point2D.Float(-offset.x,-offset.y), origin);
				scaled = transformer.transform(new Point2D.Float(-offset.x + windowWidth, -offset.y + windowHeight), scaled);
				target = transformer.transform(new Point2D.Float(e.getKey().x * width, e.getKey().y * height), target);
				if(origin.getX() < target.getX() && scaled.getX() > target.getX() && origin.getY() < target.getY() && scaled.getY() > target.getY()) {
					
				}
				g2d.drawImage(spriteImages[e.getValue().ordinal()],	 e.getKey().x*width, e.getKey().y*height, width, height, null);
				
			
			
				
		}
		System.out.println(scaled.toString() + " " + origin.toString() + target.toString());
		System.out.println((System.nanoTime() - startTime)/1000000);
	}
	//TODO: make it happen
	private void drawMiniMap(Graphics2D g2d, int windowWidth, int windowHeight, int mapWidth, int mapHeight) {
		//need to have a minimap for sanity's sake
		g2d.setColor(Color.WHITE);
		//takes up 10% of screen
		
	}
	public void setFirst(Point inPoint) {
		DebugManagement.writeNotificationToLog("First point in CellRenderer set.");
		first = inPoint;
	}

	public void setSecond(Point inPoint) {
		DebugManagement.writeNotificationToLog("Second point in CellRenderer set.");
		second = inPoint;
	}

	// Draws the tiles based on the sprites from our sprite sheet
	private void getFromSheet() {
		try {
			BufferedImage spriteSheet = ImageIO
					.read(HermesUI.class.getResource("/com/team1ofus/hermes/resources/Sprites.png"));
			 GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
			    GraphicsDevice device = env.getDefaultScreenDevice();
			    GraphicsConfiguration config = device.getDefaultConfiguration();
			    BufferedImage buffy = config.createCompatibleImage(width, height, Transparency.TRANSLUCENT);
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					spriteImages[(i * cols) + j] = spriteSheet.getSubimage(j * (width + 8), i * (height + 8), width,
							height);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// Handles tile picking, which will then be passed to A*
	public Point pickTile(int mouseX, int mouseY) {
		int x = (int) (Math.round((mouseX + offset.x) / width)*scale);
		int y = (int) (Math.round((mouseY + offset.y) / height)*scale);
		return new Point(x, y);
	}

	public Tile getTile(int x, int y) {
		int xActual = x;
		int yActual = y;
		int actualWidth = drawnCell.getWidth() - 1;
		if (x > actualWidth) {
			xActual = actualWidth;
		}
		int actualHeight = drawnCell.getHeight() - 1;
		if (y > actualHeight) {
			yActual = actualHeight;
		}

		return drawnCell.getTile(new Point(xActual, yActual));
	}

	private void drawWall(Point p, Graphics g) {
		if (p.getX() > 1 && p.getY() > 1) {
			boolean[] pt = new boolean[8];
			pt[0] = getTile((int) p.getX() - 1, (int) p.getY() - 1).getTileType() == TILE_TYPE.WALL;
			pt[1] = getTile((int) p.getX(), (int) p.getY() - 1).getTileType() == TILE_TYPE.WALL;
			pt[2] = getTile((int) p.getX(), (int) p.getY() - 1).getTileType() == TILE_TYPE.WALL;
			pt[3] = getTile((int) p.getX() + 1, (int) p.getY() - 1).getTileType() == TILE_TYPE.WALL;
			pt[4] = getTile((int) p.getX() - 1, (int) p.getY()).getTileType() == TILE_TYPE.WALL;
			pt[5] = getTile((int) p.getX() + 1, (int) p.getY()).getTileType() == TILE_TYPE.WALL;
			pt[6] = getTile((int) p.getX() - 1, (int) p.getY()).getTileType() == TILE_TYPE.WALL;
			pt[7] = getTile((int) p.getX() - 1, (int) p.getY() + 1).getTileType() == TILE_TYPE.WALL;
			pt[8] = getTile((int) p.getX() + 1, (int) p.getY() + 1).getTileType() == TILE_TYPE.WALL;
			// now, draw the segments based on t/f values.
			Point pixelPt = new Point(p.x * BootstrapperConstants.TILE_WIDTH, p.y * BootstrapperConstants.TILE_HEIGHT);

			// g.drawImage(spriteImages[0], pixelPt.x, dy1, pixelPt.y, dy2, sx1,
			// sy1, sx2, sy2, observer)
		} else {
			// map edge
		}
	}

	public void incrementOffset(int dx, int dy, int windowWidth, int windowHeight) {
		// some optimizations to be made here
		DebugManagement.writeNotificationToLog("Offset is : " + offset.toString());
		double scaleFactor = 1;
		if(isCampusMap) {
			scaleFactor = (double)50*(1 - scale/3.3);
		}
		offset.translate((int)(-dx)*(int)Math.round(scaleFactor), (int)(-dy)*(int)Math.round(scaleFactor));
		if(isCampusMap) {
			
		}
		
	}
}



/*switch (tile.getTileType()) {
case PEDESTRIAN_WALKWAY:
	g2d.drawImage(spriteImages[1], x, y, width, height, null);
	break;
case DOOR:
	g2d.drawImage(spriteImages[2], x, y, width, height, null);
	break;
case GRASS:
	g2d.drawImage(spriteImages[3], x, y, width, height, null);
	break;
case CONGESTED:
	g2d.drawImage(spriteImages[4], x, y, width, height, null);
	break;
case VERTICAL_UP_STAIRS:
	g2d.drawImage(spriteImages[5], x, y, width, height, null);
	break;
case VERTICAL_DOWN_STAIRS:
	g2d.drawImage(spriteImages[6], x, y, width, height, null);
	break;
case HORIZONTAL_LEFT_STAIRS:
	g2d.drawImage(spriteImages[7], x, y, width, height, null);
	break;
case HORIZONTAL_RIGHT_STAIRS:
	g2d.drawImage(spriteImages[8], x, y, width, height, null);
	break;
case CLASSROOM:
	g2d.drawImage(spriteImages[9], x, y, width, height, null);
	break;
case IMPASSABLE:
	g2d.drawImage(spriteImages[10], x, y, width, height, null);
	break;
case MALE_BATHROOM:
	g2d.drawImage(spriteImages[11], x, y, width, height, null);
	break;
case FEMALE_BATHROOM:
	g2d.drawImage(spriteImages[12], x, y, width, height, null);
	break;
case UNISEX_BATHROOM:
	g2d.drawImage(spriteImages[13], x, y, width, height, null);
	break;
case BENCH:
	g2d.drawImage(spriteImages[14], x, y, width, height, null);
	break;
case BUSH:
	g2d.drawImage(spriteImages[16], x, y, width, height, null);
	break;
case TREE:
	g2d.drawImage(spriteImages[17], x, y, width, height, null);
	break;
case EXTRA_TILE_TYPE_1:
	g2d.drawImage(spriteImages[18], x, y, width, height, null);
	break;
}*/