package com.team1ofus.hermes;

//TODO Comments1
interface IHumanInteractionListener { 
	void onTileClicked(CellPoint input);
	void findNearestLocation(CellPoint start, String filter);
}