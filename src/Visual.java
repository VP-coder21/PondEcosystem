//The Standard Visual.
//This is very minimal... it uses the Control Class to do most of the action.
//The mouse commands are loaded into this visual if you want to try any of those...
//There is a feature already coded that allows you to click on the screen and pick out specific animals.

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.awt.event.KeyEvent; 
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame; 
import javax.swing.JPanel; 
import javax.swing.Timer;
    
public class Visual implements ActionListener, KeyListener, MouseListener, MouseMotionListener, Variables, Constants{
 
    private JFrame frame;       //REQUIRED! The outside shell of the window
    public DrawingPanel panel;  //REQUIRED! The interior window
    private Timer visualtime;   //REQUIRED! Runs/Refreshes the screen. 
   
    public boolean working;
    public boolean paused;
    public int counter;
    public static int TIMER;
        
    public Visual() 
    {
        frame = new JFrame("Your Happy Little Pond");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new DrawingPanel();
        panel.setPreferredSize(new Dimension(WIDE, HIGH));
        frame.getContentPane().add(panel);
        panel.setFocusable(true);
        panel.requestFocus();
        panel.addKeyListener(this);
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true); 
        
        Initialize();

        visualtime = new Timer(FR, this);     
        visualtime.start();
    } 
 
    public void Initialize() 
    {
    	paused = true;
    	working = true;
    	counter = 0;
    	
    }
    public void actionPerformed(ActionEvent e)
    {    
    	TIMER++;
        if(!paused && working)
        {
            counter++;
            if(counter > DELAY)
            {
                counter = 0;
                Control.action();
                Control.nextTurn();
            }
        }

        if(Control.critters.size() == 0) 
        {
            working = false;
        }
        
        panel.repaint();
    }
 
    public void keyPressed(KeyEvent e)
    {      
    	if(e.getKeyCode() == KeyEvent.VK_SPACE)
    		if(working) paused = !paused;
    	
    	if(e.getKeyCode() == KeyEvent.VK_HOME)
    	{
    		MainPond.setUp();
    		Initialize();
    	}
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            System.exit(0);         
    }

    public void mouseClicked(MouseEvent e) 
	{
		int x = e.getX();
		int y = e.getY();
		
		for(int n = 0; n < Control.critters.size(); n++)
		{
			if(Control.critters.get(n).space.contains(x, y))
			{
				Animal print = Control.critters.get(n);
				System.out.println("Animal #"+n+": Age = "+print.age+", Health = "+print.health+", Gender = "+print.gender);
			}
		}
		
	}
    
    //Additional Methods that can be used as needed...
    public void keyTyped(KeyEvent e) {  }   
    public void keyReleased(KeyEvent e) {  }
	public void mouseDragged(MouseEvent e) {  }
	public void mouseMoved(MouseEvent e) {  }
	public void mousePressed(MouseEvent e) {  }
	public void mouseReleased(MouseEvent e) {  }
	public void mouseEntered(MouseEvent e) {  }
	public void mouseExited(MouseEvent e) {  }
	
	    
    private class DrawingPanel extends JPanel implements Variables, Constants { 
        public void paintComponent(Graphics g)         
        {
            super.paintComponent(g);
            panel.setBackground(Color.BLACK);
            
            g.setColor(Color.YELLOW);
            g.setFont(LARGE_FONT);
            g.drawString("Day "+Control.day, MARGIN, MARGIN - 40);
            g.drawString("Hour "+Control.hour, PONDW - 25, MARGIN - 40);
            
            if(paused) g.drawString("PAUSED  <SPACEBAR TO CONTINUE>", PONDW/3, HIGH - 20);
            if(!working) g.drawString("It's a bleak day. All your Critters are Dead.", PONDW/3, HIGH - 20);
                        
            Control.drawInterface(g);
            
        }
    }
}
  

