import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Window extends JFrame{
  
  // Makes 3 button components
  JButton buttonRelics, buttonNews, buttonExit;
  
  private int width, height;
  
  public Window(int width, int height){
    
    // class variables are equal to the constructors variables in the parameter
    this.width = width;
    this.height = height;
    
    // Makes a JLabel component and references the image to use as a background
    JLabel background = new JLabel(new ImageIcon("C:/Users/jo/Documents/eclipse/workspace/HeroSiegeRelics/src/Images/HeroSiegeBackground.png")); 
    
    // Makes a JPanel component
    JPanel panelButtonLocation = new JPanel();
    
    // Sets JPanel layout
    panelButtonLocation.setLayout(null);
    
    // Sets the layout for the JLabel background
    background.setLayout(new FlowLayout(FlowLayout.CENTER));
    
    // adds background to JFrame
    add(background); 
    
    // JPanel bounds
    panelButtonLocation.setBounds(325, 447, 100, 200);
    
    //adds JPanel to JFrame
    background.add(panelButtonLocation);
    
    // Creates new buttons
    buttonRelics = new JButton("Relics"); 
    buttonNews = new JButton("News");
    buttonExit = new JButton("Exit");
    
    // Sets button size
    buttonRelics.setPreferredSize(new Dimension(80, 30));
    buttonNews.setPreferredSize(new Dimension(80, 30));
    buttonExit.setPreferredSize(new Dimension(80, 30));
    
    // Sets button positioning  (PosX, PosY, SizeX, SizeY)
    buttonRelics.setBounds(137, 110, 80, 30);
    buttonNews.setBounds(137, 210, 80, 30);
    buttonExit.setBounds(137, 310, 80, 30);
    
    // Adds JButtons to JPanel
    panelButtonLocation.add(buttonRelics);
    panelButtonLocation.add(buttonNews);
    panelButtonLocation.add(buttonExit);
  }

  // returns width
  public int getWidth(){
    return width;
  }

  // returns height
  public int getHeight(){
    return height;
  }
}
