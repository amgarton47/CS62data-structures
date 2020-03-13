import java.applet.Applet;
import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

public class Ball extends Applet implements MouseMotionListener{
	
	   int speedx = 5;
	   int speedy = 5;
	   int ballx = 180;
	   int bally = 490;
	   int batx;
	   
	   BufferedImage offScreenImage;
	   Graphics offScreenGraphics;
	   
	   public void init(){
		   setSize(800,600);
		   offScreenImage = new BufferedImage(getWidth(),
				   getHeight(),BufferedImage.TYPE_INT_RGB);
		   offScreenGraphics = offScreenImage.getGraphics();
		   setBackground(Color.BLACK);
		   addMouseMotionListener(this);
	   }
	   public void paint(Graphics  g){
		   render(offScreenGraphics);
		   g.drawImage(offScreenImage, 0, 0, null);
		   
		   updateState();
		   repaint();
		   
		   try{
			   Thread.sleep(30);
		   }catch(InterruptedException e){
		   }
	   }
	   private void render (Graphics g){
		   g.clearRect(0, 0, getWidth(), getHeight());
		   g.setColor(Color.WHITE);
		   g.fillOval(ballx,  bally,  20,  20);
		   
		   g.setColor(Color.GREEN);
		   g.fillRect(batx, 580, 70, 25);
		   
		   g.setColor(Color.RED);
		   g.drawString("x= " + ballx, 10, 10);
		   
		   g.setColor(Color.RED);
		   g.drawString("x= " + bally, 10, 30);
		   
	   }
	   private void updateState(){
	   
	   ballx= ballx+speedx;
	   bally= bally+speedy;
	   
	 if(ballx >= batx && ballx <=batx+140 && bally ==580){
		 speedy = -speedy;
	 }
	 if(ballx >=batx && ballx<=batx+70 && bally>580 && bally <=605){
		 bally -=bally-580+10;
	 }
	 if(ballx <= 0 && bally <600){
		 speedx = -speedx;
	 }
	 if (ballx >=800 && bally < 600){
		 speedx =-speedx;
	 }
	 if (ballx >0 && bally <800 && bally ==0){
		 speedy = -speedy;
	 }
	 if (ballx > 0 && ballx >= 0 && bally == 610){
		 JOptionPane.showMessageDialog(null,"Sorry GameOver");
		 ballx = 180;
		 bally = 400;
	 }
   }
	   public void mouseMoved(MouseEvent e){
		   batx =e.getX();
	   }
	   public void mouseDragged(MouseEvent e){}
}
