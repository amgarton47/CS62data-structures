package game;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Rectangle;
import java.awt.ComponentOrientation;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JLabel;

public class PuzzleApp {

  private JFrame frame;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          PuzzleApp window = new PuzzleApp();
          window.frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the application.
   */
  public PuzzleApp() {
    initialize();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    frame = new JFrame();
    frame.setBounds(100, 100, 319, 339);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(new BorderLayout(0, 0));

    JPanel panel = new JPanel();
    frame.getContentPane().add(panel, BorderLayout.CENTER);
    panel.setLayout(null);

    JButton btn = new JButton("1");
    btn.setBounds(0, 0, 100,100);
    btn.setPreferredSize(new Dimension(100, 100));
    panel.add(btn);

    JButton btn7 = new JButton("7");
    btn7.setBounds(0, 200, 100, 100);
    btn7.setPreferredSize(new Dimension(100, 100));
    panel.add(btn7);

    JButton btn6 = new JButton("6");
    btn6.setBounds(200, 100,100, 100);
    panel.add(btn6);

    JButton btn8 = new JButton("8");
    btn8.setBounds(100, 200, 100, 100);
    panel.add(btn8);

    JButton btn9 = new JButton("9");
    btn9.setBounds(200, 200, 100,100);
    btn9.setVisible(false);
    panel.add(btn9);

    JButton btn3 = new JButton("3");
    btn3.setBounds(200, 0, 100, 100);
    panel.add(btn3);

    JButton btn2 = new JButton("2");
    btn2.setBounds(100, 0, 100, 100);
    panel.add(btn2);

    JButton btn5 = new JButton("5");
    btn5.setBounds(100,100, 100, 100);
    panel.add(btn5);

    JButton btn4 = new JButton("4");
    btn4.setBounds(0, 100, 100, 100);
    panel.add(btn4);


  }


}
