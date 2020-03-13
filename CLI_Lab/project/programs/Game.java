package com.jusbmx.app;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;

import javax.swing.JFrame;

public class Game extends Canvas{
	private JFrame frame;
	private Graphics g;
	
	private BufferedImage image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public Game(){
		frame = new JFrame("Videogame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(400, 400));
		frame.add(this);
		frame.pack();
		frame.setVisible(true);
	}
	
	public void render(){
		for(int i = 0; i < pixels.length; i++){
			pixels[i] = new Random().nextInt(0xFFFFFF);
		}
		g = getGraphics();
		g.drawImage(image, 0, 0, null);
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		while(true){
			game.render();
		}
	}
	
}
