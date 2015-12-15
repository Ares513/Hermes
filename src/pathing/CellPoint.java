package pathing;

import java.awt.Point;
import java.util.ArrayList;

/* contains a set of unique points (in the form (Cell, (x, y))) which specify a complete path. The exact form this data will take has not yet been determined. 
 */
public class CellPoint {
	private String cellName;
	private Point point;
	
	public CellPoint(String aCellName, Point aPoint){
		cellName = aCellName;
		point = aPoint;
	}
	
	public String getCellName(){
		return cellName;
	}
	
	public Point getPoint(){
		return point;
	}
	
	public boolean equals(CellPoint that){
		return (this.getCellName().equals(that.getCellName()) && this.getPoint().equals(that.getPoint()));
	}

	public boolean isIn(ArrayList<CellPoint> frontier) {
		for(CellPoint each: frontier){
			if(this.equals(each)){
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cellName == null) ? 0 : cellName.hashCode());
		result = prime * result + ((point == null) ? 0 : point.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CellPoint other = (CellPoint) obj;
		if (cellName == null) {
			if (other.cellName != null)
				return false;
		} else if (!cellName.equals(other.cellName))
			return false;
		if (point == null) {
			if (other.point != null)
				return false;
		} else if (!point.equals(other.point))
			return false;
		return true;
	}
	
	
}