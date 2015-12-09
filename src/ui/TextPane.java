package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import core.BootstrapperConstants;
import core.DebugConsole;
import core.DebugManagement;
import core.SEVERITY_LEVEL;
import pathing.LocationNameInfo;
import pathing.PathCell;

/*
 * Intended to be efficient at drawing text in a standardized fashion over the screen.
 * Add support for offset needed as a future task.
 */
public class TextPane extends JPanel {
	ArrayList<TextLocation> locations = new ArrayList<TextLocation>();
	public Point offset = new Point(0,0);
	public double zoomScale = 1.0;
	public TextPane(PathCell parent) {
		setOpaque(true);
		for(LocationNameInfo l : parent.getLocationNameInfo()) {
			boolean ignore = false;
			for(String s: l.getNames()) {
				if(s.contains("AutoGen")) {
					ignore = true;
						
				}
			}
			if(!ignore) {
				locations.add(new TextLocation(l.getNames(), l.getPoint(), Color.BLACK));
					
			}
		}
	}
	public void paintComponent(Graphics g) {
		this.paintComponents(g);
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform transformer = new AffineTransform();
		transformer.translate(this.getWidth()/2, this.getHeight()/2);
		DebugManagement.writeNotificationToLog("text's zoomScale is:" + zoomScale);
		transformer.scale(zoomScale, zoomScale);
		transformer.translate(-this.getWidth()/2, -this.getHeight()/2);
		transformer.translate(offset.x, offset.y);
		g2d.setTransform(transformer);
		for(TextLocation l : locations) {
			g2d.setColor(l.drawnColor);
			//we now need to center the text.
			//double yShift = g.getFont().getSize()/2;
			for(int i=0; i<l.lines.size(); i++) {
				
				//double xShift = 0;
				FontMetrics metrics = g.getFontMetrics(g.getFont());
				int stringLength;
				if(metrics == null) {
					DebugManagement.writeLineToLog(SEVERITY_LEVEL.ERROR, "FontMetrics was unable to be loaded in TextPane.");
					stringLength = 0;
				} else {
					stringLength = (int) metrics.getStringBounds(l.lines.get(i), g).getWidth();
					
				}
				g2d.setFont(new Font("TimesRoman", Font.PLAIN, 16));
				double start = stringLength/2;
				g2d.drawString(l.lines.get(i), (int)(l.location.x*BootstrapperConstants.TILE_WIDTH)-(int)(start+((BootstrapperConstants.TILE_WIDTH))), (int)(l.location.y*BootstrapperConstants.TILE_HEIGHT)+g.getFont().getSize()*i);
				
			}
		}
		if(BootstrapperConstants.WRITE_NOTIFICATIONS) {
			showConsole(g);
		}
	}
	/*
	 * For debug purposes marks all cells.
	 */
	public void labelAllTiles(PathCell target) {
		int i=0;
		int j=0;
		while(i < target.getWidth()) {
			j = 0;
			while(j < target.getHeight()) {
				//locations.add(new TextLocation(i + "," + j, new Point(i*BootstrapperConstants.TILE_WIDTH, j*BootstrapperConstants.TILE_HEIGHT)));
				j = j + 5;
			}
			i = i + 5;
		}
				
			
	}
	public void showConsole(Graphics g) {
		g.setColor(Color.BLACK);
		if(DebugConsole.getEntries().size() > 1000) {
			DebugConsole.getEntries().clear();
		}
		if(DebugConsole.getEntries().size() <= 0) {
			return;
		}
		int count = 0; //number of lines drawn.
		if(DebugConsole.getEntries().size() < BootstrapperConstants.LINES_TO_SCREEN) {
			//There are fewer lines on the list, so just start at the end.
			
			for(int i=DebugConsole.getEntries().size()-1; i>0;i--) {
				g.drawString(DebugConsole.getEntries().get(i), 0, g.getFont().getSize()*count);
				count++;
			}
		} else {
			//more, start at the end up to LINES_TO_SCREEN lower
			for(int i=DebugConsole.getEntries().size()-1; i>DebugConsole.getEntries().size()-1-BootstrapperConstants.LINES_TO_SCREEN;i--) {
				g.drawString(DebugConsole.getEntries().get(i), 0, g.getFont().getSize()*count);
				count++;
			}
		}
	
		//draw the number of lines onscreen that are specified, but don't remove them
	}
}
