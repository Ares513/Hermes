package com.team1ofus.hermes;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

//TODO Delete? Comments1
public class ControlPanel extends JPanel {

    private static final int DELTA = 10;
    public Point p1 = new Point(0, 0);

    public ControlPanel() {
        //this.add(new MoveButton("\u2190", KeyEvent.VK_LEFT, -DELTA, 0));
        //this.add(new MoveButton("\u2191", KeyEvent.VK_UP, 0, -DELTA));
        //this.add(new MoveButton("\u2192", KeyEvent.VK_RIGHT, DELTA, 0));
        //this.add(new MoveButton("\u2193", KeyEvent.VK_DOWN, 0, DELTA));
    }

    public int getX(){
    	return p1.x;
    }
    
    public int getY(){
    	return p1.y;
    }
    
    private class MoveButton extends JButton {

        KeyStroke k;
        int dx, dy;

        public MoveButton(String name, int code, final int dx, final int dy) {
            super(name);
            this.k = KeyStroke.getKeyStroke(code, 0);
            this.dx = dx;
            this.dy = dy;
            this.setAction(new AbstractAction(this.getText()) {

                @Override
                public void actionPerformed(ActionEvent e) {
                	ControlPanel.this.p1.translate(dx, dy);
                    ControlPanel.this.repaint();
                }
            });
            ControlPanel.this.getInputMap(
                WHEN_IN_FOCUSED_WINDOW).put(k, k.toString());
            ControlPanel.this.getActionMap().put(k.toString(), new AbstractAction() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    MoveButton.this.doClick();
                }
            });
        }
    }
}	
