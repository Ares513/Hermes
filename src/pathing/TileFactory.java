package pathing;

import java.awt.Point;
import java.util.ArrayList;

import com.team1ofus.apollo.TILE_TYPE;

import tiles.Bench;
import tiles.Bush;
import tiles.Classroom;
import tiles.Congested;
import tiles.Door;
import tiles.Elevator;
import tiles.FemaleBathroom;
import tiles.Grass;
import tiles.HorizontalLeftStairs;
import tiles.HorizontalRightStairs;
import tiles.Impassable;
import tiles.Linoleum;
import tiles.MaleBathroom;
import tiles.Road;
import tiles.Tile;
import tiles.Tree;
import tiles.UnisexBathroom;
import tiles.Unplowed;
import tiles.VerticalDownStairs;
import tiles.VerticalUpStairs;
import tiles.Walkway;
import tiles.Wall;

public class TileFactory {
	public static Tile MakeTile(TILE_TYPE tt, String name, Point p){
		if(tt.equals(TILE_TYPE.PEDESTRIAN_WALKWAY)){
			return(new Walkway(name, p));
		}
		else if(tt.equals(TILE_TYPE.DOOR)){
			return(new Door(name, p));
		}
		else if(tt.equals(TILE_TYPE.GRASS)){
			return(new Grass(name, p));
		}
		else if(tt.equals(TILE_TYPE.CONGESTED)){
			return(new Congested(name, p));
		}
		else if(tt.equals(TILE_TYPE.VERTICAL_UP_STAIRS)){
			return(new VerticalUpStairs(name, p));
		}
		else if(tt.equals(TILE_TYPE.VERTICAL_DOWN_STAIRS)){
			return(new VerticalDownStairs(name, p));
		}
		else if(tt.equals(TILE_TYPE.HORIZONTAL_LEFT_STAIRS)){
			return(new HorizontalLeftStairs(name, p));
		}
		else if(tt.equals(TILE_TYPE.HORIZONTAL_RIGHT_STAIRS)){
			return(new HorizontalRightStairs(name, p));
		}
		else if(tt.equals(TILE_TYPE.IMPASSABLE)){
			return(new Impassable(name, p));
		}
		else if(tt.equals(TILE_TYPE.MALE_BATHROOM)){
			return(new MaleBathroom(name, p));
		}
		else if(tt.equals(TILE_TYPE.FEMALE_BATHROOM)){
			return(new FemaleBathroom(name, p));
		}
		else if(tt.equals(TILE_TYPE.UNISEX_BATHROOM)){
			return(new UnisexBathroom(name, p));
		}
		else if(tt.equals(TILE_TYPE.BENCH)){
			return(new Bench(name, p));
		}
		else if(tt.equals(TILE_TYPE.TREE)){
			return(new Tree(name, p));
		}
		else if(tt.equals(TILE_TYPE.BUSH)){
			return(new Bush(name, p));
		}
		else if(tt.equals(TILE_TYPE.LINOLEUM)){
			return(new Linoleum(name, p));
		}
		else if(tt.equals(TILE_TYPE.ELEVATOR)){
			return(new Elevator(name, p));
		}
		else if(tt.equals(TILE_TYPE.UNPLOWED)){
			return(new Unplowed(name, p));
		}
		else if(tt.equals(TILE_TYPE.CLASSROOM)){
			return(new Classroom(name, p));
		}
		else if(tt.equals(TILE_TYPE.EXTRA_TILE_TYPE_1)){
			return(new Road(name, p));
		}
		else{
			return(new Wall(name, p));
		}
	}
}
