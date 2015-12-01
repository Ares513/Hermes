package com.team1ofus.hermes;

import java.awt.Point;
import java.util.ArrayList;

//TODO Comments1
interface IUIManagementInteractionListener { 
	void onPathReady(int cellIndex, Point first, Point second);
	void onWindowReady(int cellIndex, ArrayList<PathCell> loaded);
	
}