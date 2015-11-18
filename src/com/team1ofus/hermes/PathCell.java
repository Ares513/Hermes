package com.team1ofus.hermes;
/*
 * Description:    	A map
 
Attributes
cellName  String  
Name of this map
cellShortName String The abbreviated name of this map
scale double Length in meters of the side of each tile
baseCostMultiplier double Multiplier to allow the cost of tiles to vary based on size
tiles Tile[][] 2D Array of tiles that make up the representation of this map
destinations LocationInfo[] Array of available destinations on this map
exits LocationInfo[] Array of exits this map has to another map
 
Methods
getPossibleTraversals(int, int) Point[] Gets all neighbor tiles that can be traveled to
 
Relationships
Interface: Tile 1-to-many A cell is made up of a grid of tiles
LocationInfo 1-to-many  A cell has many destinations and exits
 */

public class PathCell{


}
