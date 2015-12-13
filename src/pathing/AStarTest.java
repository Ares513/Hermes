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
		PathCell testCell = new PathCell("aa", 5, 5, 16, TILE_TYPE.WALL);
		ArrayList<PathCell> testCellList = new ArrayList<PathCell>();
		testCellList.add(testCell);
		AStar test = new AStar(testCellList, "aa");
		ArrayList<CellPoint> AStarOut = test.getPath(new CellPoint("aa", new Point(2, 2)),
				new CellPoint("aa", new Point(2, 2)), false, new AStarConfigOptions());
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
		PathCell testCell = new PathCell("bb", 100, 100, 16, TILE_TYPE.PEDESTRIAN_WALKWAY);
		ArrayList<PathCell> testCellList = new ArrayList<PathCell>();
		testCellList.add(testCell);
		AStar test = new AStar(testCellList, "bb");
		System.out.println("Path:");
		ArrayList<CellPoint> AStarOut = test.getPath(new CellPoint("bb", new Point(50, 50)),
				new CellPoint("bb", new Point(55, 40)), false, new AStarConfigOptions());
		
		if (AStarOut != null) {
			if (AStarOut.isEmpty()) {
				fail("A*.getPath() didnt return anything");
			}
			else {
				System.out.println(AStarOut.size());
				System.out.println("getPath didnt break");
				for (CellPoint each : AStarOut) {
					System.out.println(each.getPoint());
				}
			}
		} else {
			fail("A* returned null when it should not have");
		}
	}

	@Test
	public void test3() { // should return path not found, all walls
		System.out.println("Test 3: should return no path found");
		PathCell testCell = new PathCell("cc", 5, 5, 32, TILE_TYPE.WALL);
		ArrayList<PathCell> testCellList = new ArrayList<PathCell>();
		testCellList.add(testCell);
		AStar test = new AStar(testCellList);
		ArrayList<CellPoint> AStarOut = test.getPath(new CellPoint("cc", new Point(2, 2)),
				new CellPoint("cc", new Point(2, 3)), false, new AStarConfigOptions());
		assertEquals(AStarOut, null);
	}

	@Test
	public void test4() { // should return path not found, all walls
		System.out.println("test4: should return path across the two maps connected at (45,45)");
		PathCell testCell = new PathCell("aa", 100, 100, 16, TILE_TYPE.PEDESTRIAN_WALKWAY);
		PathCell testCell2 = new PathCell("bb", 100, 100, 16, TILE_TYPE.PEDESTRIAN_WALKWAY);
		testCell2.addEntryPoint(new EntryPoint("someDoor", new Point(45, 45)));
		testCell.addEntryPointReference(new EntryPointReference("someDoor", "bb", new Point(45, 45)));
		ArrayList<PathCell> testCellList = new ArrayList<PathCell>();
		testCellList.add(testCell);
		testCellList.add(testCell2);
		AStar test = new AStar(testCellList, "aa");
		System.out.println("Path:");
		ArrayList<CellPoint> AStarOut = test.getPath(new CellPoint("aa", new Point(50, 50)),
				new CellPoint("bb", new Point(55, 40)), false, new AStarConfigOptions());
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
				hm1.put(new Point(i,j), TILE_TYPE.ELEVATOR);
			}
		}
		EntryPoint ep1 = new EntryPoint("A", new Point(45,45));
		EntryPoint ep11 = new EntryPoint("B", new Point(45,45));
		ArrayList<EntryPoint> eps1 = new ArrayList<EntryPoint>();
		eps1.add(ep1);
		eps1.add(ep11);
		
		EntryPointReference epr1 = new EntryPointReference("C","pc1", new Point(45,45));
		EntryPointReference epr11 = new EntryPointReference("D","pc1", new Point(45,45));
		ArrayList<EntryPointReference> eprs1 = new ArrayList<EntryPointReference>();
		eprs1.add(epr1);
		eprs1.add(epr11);
		
		PathCell pc1 = new PathCell("pc1", "pc1", 100, 100, hm1, eps1, new ArrayList<LocationNameInfo>(), eprs1);
		
		EntryPoint ep2 = new EntryPoint("C", new Point(45,45));
		EntryPoint ep22 = new EntryPoint("D", new Point(45,45));
		EntryPoint ep222 = new EntryPoint("J", new Point(45,45));
		ArrayList<EntryPoint> eps2 = new ArrayList<EntryPoint>();
		eps2.add(ep2);
		eps2.add(ep22);
		eps2.add(ep222);
		
		EntryPointReference epr2 = new EntryPointReference("A","pc1", new Point(45,45));
		EntryPointReference epr22 = new EntryPointReference("B","pc1", new Point(45,45));
		EntryPointReference epr222 = new EntryPointReference("I","pc3", new Point(45,45));
		ArrayList<EntryPointReference> eprs2 = new ArrayList<EntryPointReference>();
		eprs2.add(epr2);
		eprs2.add(epr22);
		eprs2.add(epr222);
		
		PathCell pc2 = new PathCell("pc2", "pc2", 100, 100, hm1, eps2, new ArrayList<LocationNameInfo>(), eprs2);
		
		EntryPoint ep3 = new EntryPoint("I", new Point(45,45));
		ArrayList<EntryPoint> eps3 = new ArrayList<EntryPoint>();
		eps3.add(ep3);
		
		EntryPointReference epr3 = new EntryPointReference("J","pc2", new Point(45,45));
		ArrayList<EntryPointReference> eprs3 = new ArrayList<EntryPointReference>();
		eprs3.add(epr3);
		
		PathCell pc3 = new PathCell("pc3", "pc3", 100, 100, hm1, eps3, new ArrayList<LocationNameInfo>(), eprs3);
		
		
		ArrayList<PathCell> pcs = new ArrayList<PathCell>();
		pcs.add(pc1);
		pcs.add(pc2);
		pcs.add(pc3);
		
		AStar test = new AStar(pcs, "pc2");
		ArrayList<CellPoint> AStarOut = test.getPath(new CellPoint("pc2", new Point(50, 50)),
				new CellPoint("pc1", new Point(55, 40)), false, new AStarConfigOptions());
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
