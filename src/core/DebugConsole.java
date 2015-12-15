package core;

import java.awt.Graphics;
import java.util.ArrayList;

//Used for debugging purposes
public class DebugConsole {
	private static ArrayList<String> entries = new ArrayList<String>();
	

	private static void garbageCollect() {
		//nuke the list when it gets too big
	}
	public static void addLine(String s) {
		entries.add(s);
	}
	public static ArrayList<String> getEntries() {
		return entries;
	}
}

