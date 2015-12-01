package com.team1ofus.apollo;

import java.awt.Point;
import java.io.Serializable;

public class EntryPoint implements Serializable {
	private String name;
	private Point location;
	public EntryPoint(String name, Point location) {
		this.name = name;
		this.location = location;
	}
	public String getName() {
		return name;
	}
	public Point getLocation() {
		return location;
	}
	
	
}
