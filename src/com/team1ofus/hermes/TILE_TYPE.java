package com.team1ofus.hermes;
//These are enums for all of the possible tile types
public enum TILE_TYPE {
	WALL, //cannot path through a wall
	PEDESTRIAN_WALKWAY, //can path through pedestrian hallway from any direction to any direction
	DOOR,    // Will indicate start and end locations for rooms
	GRASS,   //can path through grass. Will have a higher weight then walkway(harder to traverse)(Examples would include Quad)
	CONGESTED, //Indicates congested areas. Will have a higher weight then walkway
	VERTICAL_UP_STAIRS,    //Can path through stairs. Will be the tile that switches between maps
	VERTICAL_DOWN_STAIRS,  //Can path through stairs. Will be the tile that switches between maps
	HORIZONTAL_LEFT_STAIRS,//Can path through stairs. Will be the tile that switches between maps
	HORIZONTAL_RIGHT_STAIRS,//Can path through stairs. Will be the tile that switches between maps
	OUTDOOR_STAIRS, //can path through stairs.
	STAIRS_START,
	STAIRS_END,     //Will connect to nearby stair tiles to help determine end of staircase
	STEEP, //can path through. Represents hills(inclines in general)
	BATHROOM,  // Will indicate a start/destination tile.
	UNPLOWED,   //Will be for the instances of snow. Will have a higher weight associated with it. 
	CLASSROOM,  //Gives ability to restrict access during day or night time
	CELL_CONNECTOR, //This will be for traversing to other cells (different floors)
	EXTRA_TILE_TYPE_1, //Reserving more tile types in the case of potential tile types in the future
	EXTRA_TILE_TYPE_2, //Reserving more tile types in the case of potential tile types in the future
	EXTRA_TILE_TYPE_3  //Reserving more tile types in the case of potential tile types in the future
}
