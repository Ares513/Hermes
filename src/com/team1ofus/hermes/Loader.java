package com.team1ofus.hermes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.nio.file.FileSystemException;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;
import javax.swing.JSplitPane;
import javax.swing.JMenu;
import javax.swing.Box;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;


public class Loader extends JDialog {
	public LoaderInteractionEventObject events;
	/**
	 * Create the dialog.
	 */
	public Loader( ArrayList<PathCell> allCells) {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		events = new LoaderInteractionEventObject();
		int height = 50;
		int width = 500;
		setBounds((screen.width-width)/2, (screen.height-height)/2, 500, 80);
		getContentPane().setLayout(new BorderLayout());
			JPanel pane = new JPanel();
			pane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(pane, BorderLayout.CENTER);
				JComboBox mapChooser = new JComboBox();
				mapChooser.setModel(new DefaultComboBoxModel(getNames(allCells)));
				pane.add(mapChooser);
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						events.selectionMade(mapChooser.getSelectedIndex(), allCells);
					}
				});
				okButton.setActionCommand("OK");
				pane.add(okButton);
				getRootPane().setDefaultButton(okButton);
								
				
			setVisible(true);
			
	}
	private void renderSplashScreen(Graphics2D g, int frame) {
		
	}
	private String[] getNames(ArrayList<PathCell> allCells) {
		String[] result = new String[allCells.size()];
		for(int c = 0; c < allCells.size(); c++)
			result[c] = allCells.get(c).getName();
		return result;
	}
	

}
