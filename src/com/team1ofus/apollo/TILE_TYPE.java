package com.team1ofus.apollo;

public enum TILE_TYPE {
	WALL, //cannot path through a wall
	PEDESTRIAN_WALKWAY, //can path through pedestrian hallway from any direction to any direction
	DOOR,    // Will indicate start and end locations for rooms
	GRASS,   //can path through grass. Will have a higher weight then walkway(harder to traverse)
	CONGESTED, //Indicates congested areas. Will have a higher weight then walkway
	STAIRS,    //Can path through stairs. Will be the tile that switches between maps
	OUTDOOR_STAIRS, //can path through stairs. 
	STEEP, //can path through. Represents hills(inclines in general)
	BATHROOM  // Will indicate a start/destination tile. 
	
}
