package com.team1ofus.hermes;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;

import org.junit.Test;

public class AStarTest {

	@Test // should return the 1 cell.(start and end are the same)
	public void test() { 
		PathCell testCell = new PathCell("bag'o'bags",5,5,16, TILE_TYPE.WALL);
		ArrayList<PathCell> testCellList = new ArrayList<PathCell>();
		testCellList.add(testCell);
		AStar test = new AStar(testCellList);
		ArrayList<CellPoint> AStarOut = test.getPath(0, new Point(2,2), 0, new Point(2,2));
		for(CellPoint each:AStarOut){
			System.out.println(each.getPoint());
		}
		assertEquals(0,0);
	}
		
	@Test
	public void test2() { // should return path not found, all walls
		PathCell testCell = new PathCell("bag'o'schwifty",5,5,16, TILE_TYPE.WALL);
		ArrayList<PathCell> testCellList = new ArrayList<PathCell>();
		testCellList.add(testCell);
		AStar test = new AStar(testCellList);
		ArrayList<CellPoint> AStarOut = test.getPath(0, new Point(2,2), 0, new Point(2,3));
		assertEquals(AStarOut,null);
		
	}

}
