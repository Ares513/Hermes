package com.team1ofus.hermes;

import java.util.Arrays;
import java.util.List;

import completely.data.Indexable;

/* This class is required by the 'completely' library (available on git) which we are using to implement searching. It wraps a java string.
 * Author Forrest Cinelli
 * */
public class Record implements Indexable {
	
	private final String val; //the value the record holds
	private final String cellName; //the cell that holds this information
	
	public Record(String aVal, String aCellName){
		this.val = aVal;
		this.cellName = aCellName;
	}
	
	@Override
	public List<String> getFields() {
		return Arrays.asList(val);
	}
	
	public String getVal(){
		return val;
	}
	
	public String getCellName(){
		return cellName;
	}

}
