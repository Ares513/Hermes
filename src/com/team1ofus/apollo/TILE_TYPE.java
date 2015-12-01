package com.team1ofus.apollo;

public enum TILE_TYPE {
	WALL, //cannot path through a wall
	PEDESTRIAN_WALKWAY, //can path through pedestrian hallway from any direction to any direction
	DOOR,    // Will indicate start and end locations for rooms
	GRASS,   //can path through grass. Will have a higher weight then walkway(harder to traverse)(Examples would include Quad)
	CONGESTED, //Indicates congested areas. Will have a higher weight then walkway
	VERTICAL_UP_STAIRS, 
	VERTICAL_DOWN_STAIRS, 
	HORIZONTAL_LEFT_STAIRS,
	HORIZONTAL_RIGHT_STAIRS,
	IMPASSABLE, //not a wall, but you definitely can't walk through it
	MALE_BATHROOM,
	FEMALE_BATHROOM,
	UNISEX_BATHROOM,
	BENCH,
	TREE,
	BUSH,
	LINOLEUM, //for tiled/bathrooms, etc
	ELEVATOR,
	UNPLOWED,   //Will be for the instances of snow. Will have a higher weight associated with it. 
	CLASSROOM,  //Gives ability to restrict access during day or night time
	EXTRA_TILE_TYPE_1, //Reserving more tile types in the case of potential tile types in the future
	EXTRA_TILE_TYPE_2, //Reserving more tile types in the case of potential tile types in the future
	EXTRA_TILE_TYPE_3  //Reserving more tile types in the case of potential tile types in the future
	
 }
