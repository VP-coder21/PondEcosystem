import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Gobbler extends Animal {
	
	public static final double AVE_NUM_BABIES = 3.3;
	
	public Gobbler(int x, int y) {
		super(x, y);
		type = "Notorious Frog Gobbler";
		pic = new ImageIcon("notoriousfroggobbler.gif");
		int size = 7*ANIMAL_RAD/5;   //this is 16 pixels if using the original values		
		space = new Rectangle(x - size, y - size, size*2, size*2);
		perception = 2*ANIMAL_RAD;
	}
	
	public void move() {
    	int delx, dely;
    	Rectangle attemptedmove;
    	
    	Rectangle target = lookAround();
    	if(target != null) {
    		delx = target.x - space.x;
    		dely = target.y - space.y;
    	}
    	else {
    		double ang = Math.random()*Math.PI*2;
    		delx = (int)(space.width*Math.cos(ang));
    		dely = (int)(space.height*Math.sin(ang));
    	}
    		
    	attemptedmove = new Rectangle(space.x + delx, space.y + dely, space.width, space.height);
    	
    	if(outsidePond(attemptedmove)) return;
    	if(Control.pondIsEmptyWater(attemptedmove)){
    		//this is a good space to move to... update your space Rectangle.
    		space.x += delx;
    		space.y += dely;
    		health--; //it costs energy to move.
    	}
    	if(health < 1)
    		health = 1;
	}
    public void act() {
    	Rectangle reach = new Rectangle(space.x - space.width, space.y - space.height, space.width * 3, space.height * 3);
    	if(Visual.TIMER < 200) return;
    	else {

    		for(int n = Control.critters.size()-1; n >= 0; n--)
    			if(Control.critters.get(n).type == "Frog" && reach.intersects(Control.critters.get(n).space))
    			{
    				//Find food, eat food...
    				hungry = false;
    				health += Control.bits.get(0).nutrients * 5;
    				Control.critters.get(n).alive = false;
    				Control.died++;
    				//BE CAREFUL -- you can remove food from Control.bits...  but if you eat/kill a critter **DO NOT REMOVE THEM** 
    				//Instead just set their alive boolean to false:  Control.critters.get(n).alive = false; 
    				//They will be removed later.  This is because the food does not ACT(), pulling one from its list doesn't hurt
    				//anything, but if you do that to a critter it can REALLY mess up the arraylist during the act() sequence
    			}
    	}
    		boolean found = false;
    		for(int n = 0; !found && n < Control.critters.size(); n++)
    		{
    			if(checkIf(Control.critters.get(n), "Notorious Frog Gobbler", reach))
    				found = true;
    		}
    		if(found) pregnant = true;
    	
    }
    public void age() {
    	if(hungry) health -= DAILY_HUNGER;
        
        hungry = true;  //it will start each day hungry

        age++;
        
        if(health < 1)
    		health = 1;
        
        if(!pregnant) return;

        pregnant = false;
        boolean morebabies = true;
        int attempts = 0;
        while(morebabies)
        {
        	attempts++;
        	//pick a random direction... and place a baby if its open
        	double ang = Math.random()*Math.PI*2;
        	int delx = (int)(space.width*Math.cos(ang));
        	int dely = (int)(space.height*Math.sin(ang));
        	Animal baby = new Gobbler(space.x+delx, space.y+dely);

        	//Checking for the border and rocks and other critters...
        	if(!outsidePond(baby.space) && Control.pondIsEmptyWater(baby.space))
        	{
        		Control.critters.add(baby);
        		Control.born++;
        		if(Math.random() > 1/AVE_NUM_BABIES) morebabies = false;
        	}
        	if(attempts > 10) morebabies = false;
        }
    }
    public Rectangle lookAround()
    {
    	//Make a perception rectangle around your space...
    	Rectangle view = new Rectangle(space.x - perception, space.y - perception, space.width + 2*perception, space.height + 2*perception);
    	for(int n = 0; n < Control.critters.size(); n++)
    	{
    		if(checkIf(Control.critters.get(n), "Frog", view))
    			return Control.critters.get(n).space;
    	}
    	return null;
    }
    public boolean checkIf(Animal a, String t, Rectangle r) {
    	if(this != a  //check to make sure the target animal isn't me  
    	    && a.alive  //check to make sure the target animal is alive
    	    && a.type.equals(t)  //check to make sure the animal's type matches the parameter
    	    && a.space.intersects(r)) //check to make sure the animal's space is inside the parameter rectangle.
    	    	return true;
    	else
    	    return false;}
}