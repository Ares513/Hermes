package core;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;

public class BootstrapperConstants {
	public static final String APP_FILE_DIRECTORY = System.getProperty("user.dir");
	public static final int TILE_WIDTH = 32;
	public static final int TILE_HEIGHT= 32;
	public static final boolean DEBUG = false;
	public static final boolean WRITE_NOTIFICATIONS = false;
	public static final int LINES_TO_SCREEN = 10; //the number of console writes to show to the screen at any given time
	public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int FRAME_WIDTH= screenSize.width-200;
	public static final int FRAME_HEIGHT= screenSize.height-200;
	public static final int  PANEL_SIZE= 315;
	public static final String MALE_BATHROOM_IDENTIFIER = "Any Male Bathroom";
	public static final String FEMALE_BATHROOM_IDENTIFIER = "Any Female Bathroom";
	public static final String UNISEX_BATHROOM_IDENTIFIER = "Any Unisex Bathroom";
	public static final String BENCH_IDENTIFIER = "Any Bench";
	public static final String INFO = "Worcester Polytechnic Institute\n\nCS3733 2015 B-Term\n\nTeam1OfUs\n\nNicolas Adami-Sampson: Iteration 1&2 - UI/HCI Lead, Iteration 3&4 - Project Manager\nWill Barnard: Iteration 1&2 - Software Engineer, Iteration 3&4 - Product Owner\nMatt Beader: Iteration 1&2 - Software Engineer, Iteration 3&4 - Documentation and Testing Lead\nForrest Cinelli: Iteration 1&2 - Software Engineer, Iteration 3&4 - Lead Engineer\nElijah Gonzalez: Iteration 1&2 - Documentation and Testing Lead, Iteration 3&4 - Software Engineer\nAaron Jaeger: Iteration 1&2 - Product Owner, Iteration 3&4 - Software Engineer\nEvan King: Iteration 1&2 - Lead Engineer, Iteration 3&4 - Software Engineer\nCuong Nguyen: Iteration 1&2 - Software Engineer, Iteration 3&4 - UI/HCI Lead\nBryan Toribio: Iteration 1&2 - Project Manager, Iteration 3&4 - Software Engineer\n\nProf. Wilson Wong\nCoach Caitlin Malone";

	
}
