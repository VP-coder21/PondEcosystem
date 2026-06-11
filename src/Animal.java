//The Parent Class to all animals in the pond... it should contain all of the data members AND methods for all of its children.
//You will never instantiate an Animal Object (it's an abstraction), but you need these methods to help the polymorphism.
//Document any changes you make to this class!!

import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Animal implements Variables, Constants {

    public Rectangle space; //tracks the animal's position (and size)
    public int posz;  //a z-coordinate in case you want to use it.
    public int nutrients;
    public int above;
    public boolean follow;
    
    public String type;
    public int age;
    public int health;
    public int gender;  //0 = baby, 1 = male, 2 = female ...
    
    public ImageIcon pic;
    public boolean alive;
    public boolean pregnant;
	
    public int perception;
    public boolean solitary;
    public boolean hungry;
    
    public Animal(int x, int y)
    {
    	nutrients = 20;
        type = "Animal";
        age = 1;
        health = 100;
        gender = 0;
        follow = false;
    
        //The (x,y) parameters are translated to the center of the Animal's space:
        space = new Rectangle(x - ANIMAL_RAD, y - ANIMAL_RAD, ANIMAL_RAD*2, ANIMAL_RAD*2);
        posz = 0;
        alive = true;
        pregnant = false;
        
        perception = 3*ANIMAL_RAD;
        solitary = true;
        hungry = true;
    }
    
    public void draw(Graphics g)
    {
        if(!alive) return;
        g.drawImage(pic.getImage(), space.x, space.y, space.width, space.height, null);
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    //You will need to override/overwrite each of these in child classes of Animal.
    //They are here to help with the polymorphism.
    public void move() {}
    public void act() {}
    public void age() {}
    public boolean checkIf(Animal a, String t) {return false;}
    

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    //HELPER METHODS THAT CAN BE USED IN CHILD CLASSES FROM HERE or OVERWRITTEN:
    //A helper method that can be used for checking on other animals in the pond.
    public boolean checkIf(Animal a, String t, Rectangle r) 
    {
    	if(this != a  //check to make sure the target animal isn't me  
    	  && a.alive  //check to make sure the target animal is alive
    	  && a.type.equals(t)  //check to make sure the animal's type matches the parameter
    	  && a.space.intersects(r)) //check to make sure the animal's space is inside the parameter rectangle.
    	  	return true;
    	
    	else
    		return false;
    }
    //A helper method to check if you're out of the pond:
    public boolean outsidePond(Rectangle check)
    {
    	if(check.x < MARGIN) return true;
    	if(check.y < MARGIN) return true;
    	if(check.x + check.width  > MARGIN + PONDW) return true;
    	if(check.y + check.height > MARGIN + PONDH) return true;
		
		return false;
    }
}
