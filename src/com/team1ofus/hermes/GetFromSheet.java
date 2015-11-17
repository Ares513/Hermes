package com.team1ofus.hermes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GetFromSheet {
	
	final int width = 32;
	final int height = 32;
	final int rows = 1;
	final int cols = 3;
	BufferedImage[] spriteImages = new BufferedImage[rows * cols];
	
	
	public GetFromSheet(){
		try{
		 BufferedImage spriteSheet = ImageIO.read(new File("Sprites.png"));
		 
		 for (int i = 0; i < rows; i++)
			{
			    for (int j = 0; j < cols; j++){
			    	spriteImages[(i * cols) + j] = spriteSheet.getSubimage(j * width,i * height,width,height);
			    }
			}
		 
		 }
		catch (IOException e) {
	       throw new RuntimeException(e);
		}	
	}
	
	public BufferedImage[] ReturnSprites(){
		return spriteImages;
	}
	
}