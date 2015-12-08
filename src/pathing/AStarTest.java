package pathing;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import com.team1ofus.apollo.TILE_TYPE;

public class AStarTest {

	@Test // should return the 1 cell.(start and end are the same)
	public void test1() {
		System.out.println("Test1: should return a path with 1 tile (2,2)");
		PathCell testCell = new PathCell("a", 5, 5, 16, TILE_TYPE.WALL);
		ArrayList<PathCell> testCellList = new ArrayList<PathCell>();
		testCellList.add(testCell);
		AStar test = new AStar(testCellList);
		ArrayList<CellPoint> AStarOut = test.getPath(new CellPoint("a", new Point(2, 2)),
				new CellPoint("a", new Point(2, 2)), false);
		if (AStarOut != null) {
			for (CellPoint each : AStarOut) {
				System.out.println(each.getPoint());
			}
			fail("shouldnt return a path because the start is a wall");
		} else {
			System.out.println("path start is wall");
		}
	}

	@Test
	public void test2() { // should return path not found, all walls
		System.out.println("test2: should return path from (50,50) to (55,40)");
		PathCell testCell = new PathCell("b", 100, 100, 16, TILE_TYPE.PEDESTRIAN_WALKWAY);
		ArrayList<PathCell> testCellList = new ArrayList<PathCell>();
		testCellList.add(testCell);
		AStar test = new AStar(testCellList);
		System.out.println("Path:");
		ArrayList<CellPoint> AStarOut = test.getPath(new CellPoint("b", new Point(50, 50)),
				new CellPoint("b", new Point(55, 40)), false);
		if (AStarOut.isEmpty()) {
			fail("A*.getPath() didnt return anything");
		}
		if (AStarOut != null) {
			System.out.println(AStarOut.size());
			System.out.println("getPath didnt break");
			for (CellPoint each : AStarOut) {
				System.out.println(each.getPoint());
			}
		} else {
			fail("A* returned null when it should not have");
		}
	}

	@Test
	public void test3() { // should return path not found, all walls
		System.out.println("Test 3: should return no path found");
		PathCell testCell = new PathCell("c", 5, 5, 32, TILE_TYPE.WALL);
		ArrayList<PathCell> testCellList = new ArrayList<PathCell>();
		testCellList.add(testCell);
		AStar test = new AStar(testCellList);
		ArrayList<CellPoint> AStarOut = test.getPath(new CellPoint("c", new Point(2, 2)),
				new CellPoint("c", new Point(2, 3)), false);
		assertEquals(AStarOut, null);
	}

	@Test
	public void test4() { // should return path not found, all walls
		System.out.println("test4: should return path across the two maps connected at (45,45)");
		PathCell testCell = new PathCell("a", 100, 100, 16, TILE_TYPE.PEDESTRIAN_WALKWAY);
		PathCell testCell2 = new PathCell("b", 100, 100, 16, TILE_TYPE.PEDESTRIAN_WALKWAY);
		testCell2.addEntryPoint(new EntryPoint("someDoor", new Point(45, 45)));
		testCell.addEntryPointReference(new EntryPointReference("someDoor", "b", new Point(45, 45)));
		ArrayList<PathCell> testCellList = new ArrayList<PathCell>();
		testCellList.add(testCell);
		testCellList.add(testCell2);
		AStar test = new AStar(testCellList);
		System.out.println("Path:");
		ArrayList<CellPoint> AStarOut = test.getPath(new CellPoint("a", new Point(50, 50)),
				new CellPoint("b", new Point(55, 40)), false);
		if (AStarOut.size() >= 5) {
			System.out.println("getPath didnt break");
			for (CellPoint each : AStarOut) {
				System.out.println(each.getCellName() + each.getPoint());
			}
		} else {
			fail("A* returned null when it should not have");
		}
	}

	@Test
	public void test5() {
		System.out.println("test5: should return path across the two maps connected at (45,45)");
		HashMap<Point, TILE_TYPE> hm1 = new HashMap<Point, TILE_TYPE>();
		for(int i =0; i<100;i++){
			for(int j = 0; j < 100; j++){
				hm1.put(new Point(i,j), TILE_TYPE.PEDESTRIAN_WALKWAY);
			}
		}
		EntryPoint ep = new EntryPoint("A", new Point(45,45));
		ArrayList<EntryPoint> eps = new ArrayList<EntryPoint>();
		eps.add(ep);
		
		PathCell pc1 = new PathCell("pc1", "pc1", 100, 100, hm1, eps, new ArrayList<LocationNameInfo>(), new ArrayList<EntryPointReference>());
		
		EntryPointReference epr = new EntryPointReference("A","pc1", new Point(45,45));
		ArrayList<EntryPointReference> eprs = new ArrayList<EntryPointReference>();
		eprs.add(epr);
		
		PathCell pc2 = new PathCell("pc2", "pc2", 100, 100, hm1, new ArrayList<EntryPoint>(), new ArrayList<LocationNameInfo>(), eprs);
		
		ArrayList<PathCell> pcs = new ArrayList<PathCell>();
		pcs.add(pc1);
		pcs.add(pc2);
		
		AStar test = new AStar(pcs);
		ArrayList<CellPoint> AStarOut = test.getPath(new CellPoint("pc2", new Point(50, 50)),
				new CellPoint("pc1", new Point(55, 40)), false);
		if (AStarOut.size() >= 5) {
			System.out.println("getPath didnt break");
			for (CellPoint each : AStarOut) {
				System.out.println(each.getCellName() + each.getPoint());
			}
		} else {
			fail("A* returned null when it should not have");
		}
	}
}
