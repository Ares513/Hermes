package com.team1ofus.hermes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

//This class creates splash screen that will be shown on application launch
public class SplashScreen extends JWindow {
	  private int duration;
	  public SplashScreen(int d) {
	    duration = d;
	  }

	  // A simple little method to show a title screen in the center
	  // of the screen for the amount of time given in the constructor
	  public void showSplash() {
	    JPanel content = (JPanel)getContentPane();
	    content.setBackground(Color.white);

	    // Set the window's bounds, centering the window
	    int width = 650;
	    int height =262;
	    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (screen.width-width)/2;
	    int y = (screen.height-height)/2;
	    setBounds(x,y,width,height);

	    // Build the splash screen
	    JLabel label = new JLabel(new ImageIcon("DapperMapper.png"));
	    JLabel copyrt = new JLabel
	      ("Team 1 of us", JLabel.CENTER);
	    copyrt.setFont(new Font("Sans-Serif", Font.BOLD, 12));
	    content.add(label, BorderLayout.CENTER);
	    content.add(copyrt, BorderLayout.SOUTH);
	    Color oraRed = new Color(156, 20, 20,  255);
	    //content.setBorder(BorderFactory.createLineBorder(oraRed, 10));

	    // Display it
	    setVisible(true);

	    // Wait a little while, maybe while loading resources
	    try { Thread.sleep(duration); } catch (Exception e) {}

	    setVisible(false);
	  }

	  public void showSplashAndExit() {
	    showSplash();
	  }

	}
