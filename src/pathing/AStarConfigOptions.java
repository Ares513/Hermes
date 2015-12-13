package pathing;
/* Author Forrest Cinelli and Will Barnard
 * A big class full of params to be passed to A*
 * */
public class AStarConfigOptions {
	boolean isLateForClass;
	boolean isHandicapped;
	boolean isSillyUnits;
	
	public AStarConfigOptions(){
		isLateForClass = false;
		isHandicapped = false;
		isSillyUnits = false;
	}
	
	public void setIsLateForClass(boolean setting){
		this.isLateForClass = setting;
	}
	public void setIsHandicapped(boolean setting){
		this.isHandicapped = setting;
	}
	public void setIsStupidUnits(boolean setting){
		this.isSillyUnits = setting;
	}
	
	public boolean getIsLateForClass(){
		return this.isLateForClass;
	}
	public boolean getIsHandicapped(){
		return this.isHandicapped;
	}
	public boolean getIsStupidUnits(){
		return this.isSillyUnits;
	}
}
