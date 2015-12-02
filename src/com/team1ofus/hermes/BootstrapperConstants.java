package com.team1ofus.hermes;

import java.awt.Dimension;
import java.awt.Toolkit;

public class BootstrapperConstants {
	public static final String APP_FILE_DIRECTORY = System.getProperty("user.dir");
	public static final int TILE_WIDTH = 32;
	public static final int TILE_HEIGHT= 32;
	public static final boolean DEBUG = true;
	public static final boolean WRITE_NOTIFICATIONS = false;
	public static final int LINES_TO_SCREEN = 10; //the number of console writes to show to the screen at any given time
	public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int FRAME_WIDTH= screenSize.width-200;
	public static final int FRAME_HEIGHT= screenSize.height-200;
	public static final int  PANEL_SIZE= 300;
	
	
}
