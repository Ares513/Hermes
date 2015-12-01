package com.team1ofus.hermes;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.JTabbedPane;

public class MapTabbedPane<T extends Component> extends JTabbedPane {
	
	ArrayList<T> tabPanes;
	PathCell cell;

	public MapTabbedPane() {
		super();
		tabPanes = new ArrayList<T>();
	}
	
	public MapTabbedPane(int top) {
		super(top);
		tabPanes = new ArrayList<T>();
	}

	public MapTabbedPane(int top, PathCell currentCell) {
		super(top);
		tabPanes = new ArrayList<T>();
		cell = currentCell;
		System.out.println(currentCell.getName());
	}
	
	
	public void addNewTab(String title, Icon icon, T component, String tip) {
		tabPanes.add(component);
		super.addTab(title, icon, component, tip);
	}
	
	public T getSelectedTabPane() {
		return tabPanes.get(this.getSelectedIndex());
	}

}
