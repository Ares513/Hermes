package ui;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.JTabbedPane;

import core.DebugManagement;
import pathing.PathCell;

public class MapTabbedPane<T extends Component> extends JTabbedPane {
	
	private ArrayList<T> tabPanes;
	private PathCell cell;
	private String building;

	public MapTabbedPane() {
		super();
		tabPanes = new ArrayList<T>();
	}
	
	public MapTabbedPane(int top) {
		super(top);
		tabPanes = new ArrayList<T>();
	}
	
	public MapTabbedPane(int top, String name) {
		super(top);
		tabPanes = new ArrayList<T>();
		building = name;
		System.out.println(building);
	}

	public MapTabbedPane(int top, PathCell currentCell) {
		super(top);
		tabPanes = new ArrayList<T>();
		cell = currentCell;
		DebugManagement.writeNotificationToLog(currentCell.getName());
	}
	
	
	public void addNewTab(String title, Icon icon, T component, String tip) {
		tabPanes.add(component);
		super.addTab(title, icon, component, tip);
	}
	
	public T getSelectedTabPane() {
		return tabPanes.get(this.getSelectedIndex());
	}
	
	public int getIndexOfTab(String name) {
		for(int i = 0; i < this.getTabCount(); i++){
			if(this.getTitleAt(i).equals(name))
				return i;
		}
		return 0;
	}
	
	public String getName() {
		return building;
	}

}
