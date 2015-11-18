package com.team1ofus.hermes;

import java.awt.Point;

interface IUIManagementInteractionListener { 
	void onPathReady(int cellIndex, Point first, Point second);
}