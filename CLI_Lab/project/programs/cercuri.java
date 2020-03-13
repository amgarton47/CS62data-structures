package Proj1;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class cercuri extends JFrame implements KeyListener{
  
  double angle;
  int x, y, radius;
  
  cercuri(){
    x = y = 15;
    radius = 70;
    angle = 0;
    this.setContentPane(new DrawPanel());
    this.setSize(600, 400);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.addKeyListener(this);
    this.setVisible(true);
  }
  
  
  @Override
  public void keyPressed(KeyEvent key){
    if(key.getKeyCode() == KeyEvent.VK_R){
      angle += Math.PI/8;
      repaint();
    }
    if(key.getKeyCode() == KeyEvent.VK_E){
      angle -= Math.PI/8;
      repaint();
    }
  }
  
  @Override
  public void keyReleased(KeyEvent key){}
  
  @Override
  public void keyTyped(KeyEvent key){}
  
  
  class DrawPanel extends JPanel{
    @Override
    public void paintComponent(Graphics g){
      Graphics2D g2 = (Graphics2D)g;
        
      AffineTransform affine = new AffineTransform();
      affine.setToTranslation(getWidth()/6, getHeight()/6);
      affine.concatenate(AffineTransform.getRotateInstance(angle));
      g2.setTransform(affine);
      GeneralPath p = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
      
    
      
      
    
      affine.setToTranslation(0,0);
      g2.setTransform(affine);
      g2.setColor(Color.BLACK);
      g2.drawOval(100, 100, 100, 100);
      g2.drawOval(180, 100, 100, 100);
      g2.drawOval(260, 100, 100, 100);
      g2.drawOval(340, 100, 100, 100);
    }
  } 
  public static void main(String[] args)
  {
    cercuri p = new cercuri();
  }
}
