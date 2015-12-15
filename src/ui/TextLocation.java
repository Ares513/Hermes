package ui;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class TextLocation {
	ArrayList<String> lines = new ArrayList<String>();
	Point location;
	Color drawnColor;
	public TextLocation(String input, Point location, Color inColor) {
		lines.add(input);
		this.location = location;
		drawnColor = inColor;
	}
	public TextLocation(ArrayList<String> input, Point location, Color inColor) {
		lines = input;
		this.location = location;
		drawnColor = inColor;
	}
	public void addString(String input) {
		lines.add(input);
	}
	public ArrayList<String> getLines() {
		return lines;
	}
}
