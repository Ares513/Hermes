package com.team1ofus.hermes;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

public class TestHermes {
	

	@Test
	public void testCellRenderer() {
		TILE_TYPE[][] array = {{TILE_TYPE.WALL, TILE_TYPE.WALL}, {TILE_TYPE.PEDESTRIAN_WALKWAY, TILE_TYPE.WALL}};
		CellRenderer rend = new CellRenderer(array);
		//Test Tilepicking
		assertEquals(new Point(1,1), rend.pickTile(18, 18));
		assertEquals(new Point(0,1), rend.pickTile(2, 20));
		assertEquals(new Point(1,0), rend.pickTile(31, 7));
		assertEquals(new Point(0,0), rend.pickTile(6, 4));
		//Test Offset
		rend.incrementOffset(5, 24, 200, 200);
		assertEquals(new Point(5,24), rend.offset);
		rend.incrementOffset(-76, 500, 200, 200);
		assertEquals(new Point(0,524), rend.offset);
	}
	
	

}
