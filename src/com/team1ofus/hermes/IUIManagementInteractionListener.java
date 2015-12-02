package com.team1ofus.hermes;

import java.awt.Point;
import java.util.ArrayList;

//TODO Comments1
interface IUIManagementInteractionListener { 
	void onPathReady(CellPoint first, CellPoint second);
	void onWindowReady(ArrayList<PathCell> loaded);
	
}