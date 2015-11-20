package com.team1ofus.hermes;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;

import org.junit.Test;

public class AStarTest {

	@Test
	public void test() {
		PathCell testCell = new PathCell("bag'o'dicks",5,5,16, TILE_TYPE.WALL);
		ArrayList<PathCell> testCellList = new ArrayList<PathCell>();
		testCellList.add(testCell);
		AStar test = new AStar(testCellList);
		System.out.println(test.getPath(0, new Point(2,2), 0, new Point(2,2)));
//		ArrayList<PathCell> expected = new ArrayList<PathCell>();
//		expected.add(
//		assertEquals(test.getPath(0, new Point(2,2), 0, new Point(2,2)), )
	}

}
