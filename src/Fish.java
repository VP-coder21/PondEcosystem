import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Fish extends Animal{
	public static final double ADULT_NOCTURNAL = .5; //Probabilities used in the move() method.  This means adult frogs only move 
	public static final double CHILD_NOCTURNAL = .3; //about 10% of the time in the daylight, and children 60% of the time in the daylight
	public static final int MINAGE = 2;
	public static final int OLDAGE = 4;
	public static final double SOLO_PERC = .42;
	public static final double AVE_NUM_BABIES = 3.3;
	public Fish(int x, int y)
	{
		super(x, y);
		type = "Fish";
		pic = new ImageIcon("babyfish.png");
		int size = 4*ANIMAL_RAD/5;   //this is 16 pixels if using the original values		
		space = new Rectangle(x - size, y - size, size*2, size*2);
		follow=true;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
    public void move() 
    {
    	if(!alive) return;       //dead frogs don't move
    	if(health < 10) return;  //unhealthy frogs don't move

    	//Here are some ways to control how often a critter might move around using probabilities and day times:
    	//Adult frogs don't move around much in the daytime...
    	if(gender > 0 && !Control.isDaytime() && Math.random() < ADULT_NOCTURNAL) return;  

    	//Baby frogs are a bit more active in the daytime...
    	if(gender == 0 && !Control.isDaytime() && Math.random() < CHILD_NOCTURNAL) return;  

    	//If it's not daytime, all frogs are always active...
    	//...

    	
    	//You could also write special movement commands that cause the frog to swim underwater or
    	// leap through the air.  Maybe it's a method that switches its ImageIcon picture based on 
    	// whether it is above water or underwater...
    	
    	
    	
    	//Decide to either move towards others, away from others, or randomly...
    	//You could make this better by using distances and have the solitary frogs move away from the
    	//  the closest target... or try to move from the average position if there are more than one.
    	int delx=0, dely=0;
    	Rectangle attemptedmove;
    	if(!follow)
    	{
    	Rectangle target = lookAround();
    	if(solitary && target != null) //moves away from others
    	{
    		delx = space.x - target.x;
    		dely = space.y - target.y;
    	}
    	else if(!solitary && target != null) //moves towards others
    	{
    		delx = target.x - space.x;
    		dely = target.y - space.y;
    	}
    	else //moving randomly
    	{
    		double ang = Math.random()*Math.PI*2;
    		delx = (int)(space.width*Math.cos(ang));
    		dely = (int)(space.height*Math.sin(ang));
    	}
    	}
    	else if(follow)
    	{
        	Rectangle target = lookForFollow();
        	if(!solitary && target != null) //moves towards others
        	{
        		delx = target.x - space.x;
        		dely = target.y - space.y;
        	}
        	else //moving randomly
        	{
        		double ang = Math.random()*Math.PI*2;
        		delx = (int)(space.width*Math.cos(ang));
        		dely = (int)(space.height*Math.sin(ang));
        	}
    	}
    	attemptedmove = new Rectangle(space.x + delx, space.y + dely, space.width, space.height);
    	
    	//Check if the move goes outside of the pond... if it does, just return home
    	if(outsidePond(attemptedmove)) return;

    	//Finally check if the move is clear...
    	if(Control.pondIsEmptyWater(attemptedmove))
    	{
    		//this is a good space to move to... update your space Rectangle.
    		space.x += delx;
    		space.y += dely;
    		health--; //it costs energy to move.
    	}
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    
    public void act() 
    {
        if(!alive) return;  //dead frogs don't act
        
        //Build a Rectangle to represent your reach:
        Rectangle reach = new Rectangle(space.x - space.width, space.y - space.height, space.width * 3, space.height * 3);

        for(int n = Control.bits.size()-1; n >= 0; n--)
            if(Control.bits.get(n).food && reach.intersects(Control.bits.get(n).space))
            {
                //Find food, eat food...
            	hungry = false;
                health += Control.bits.get(n).nutrients;
                Control.bits.remove(n);
                //BE CAREFUL -- you can remove food from Control.bits...  but if you eat/kill a critter **DO NOT REMOVE THEM** 
                //Instead just set their alive boolean to false:  Control.critters.get(n).alive = false; 
                //They will be removed later.  This is because the food does not ACT(), pulling one from its list doesn't hurt
                //anything, but if you do that to a critter it can REALLY mess up the arraylist during the act() sequence
            }

       if(gender==1 && Math.random()<.6) follow=false;
    }
    
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public void age() 
    {
        if(!alive) return;
        if(hungry) health -= DAILY_HUNGER;
        
        hungry = true;  //it will start each day hungry

        age++;
        if(gender == 0 && age > MINAGE) //Baby Frog is growing up!
        	growup();
        
        //Check if the frog is too old...
        if(age > OLDAGE)
        {
        	for(int n=0; n<3;n++)
        	{
                boolean morebabies = true;
                int attempts = 0;
                while(morebabies)
                {
                	attempts++;
                	//pick a random direction... and place a baby if its open
                	double ang = Math.random()*Math.PI*2;
                	int delx = (int)(space.width*Math.cos(ang));
                	int dely = (int)(space.height*Math.sin(ang));
                	Animal baby = new Fish(space.x+delx, space.y+dely);

                	//Checking for the border and rocks and other critters...
                	if(!outsidePond(baby.space) && Control.pondIsEmptyWater(baby.space))
                	{
                		Control.critters.add(baby);
                		Control.born++;
                		if(Math.random() > 1/AVE_NUM_BABIES) morebabies = false;
                	}
                	if(attempts > 100) morebabies = false;
                }
        	}
                alive = false;

        }
        
        //Finally, check if the frog is going to have babies.

        //If you're here, you are a pregnant frog about to have babies.
       
    }
//PS. An interesting challenge for part two of this task would be to create a variable data value that
//  is passed on from mother to child (or maybe even a combination of mother and father to child).  
//  For instance, maybe the perception value would be a random value and not a fixed value.  Then 
//  the mother's perception is passed to the children... OR maybe the activity range (XXX_NOCTURNAL) isn't
//  a fixed value but a random number and mother frogs pass on their values to their children.
//  Then you can track that statistic -- what is the average value in the pond (like genetics)
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    //HELPER METHODS::
    //A helper method that looks for targets nearby, returning their space or null if nothing is found:
    public Rectangle lookAround()
    {
    	//Make a perception rectangle around your space...
    	Rectangle view = new Rectangle(space.x - perception, space.y - perception, space.width + 2*perception, space.height + 2*perception);
    	for(int n = 0; n < Control.critters.size(); n++)
    	{
    		if(checkIf(Control.critters.get(n), "Fish", view))
    			return Control.critters.get(n).space;
    	}
    	return null;
    }
    public Rectangle lookForFollow()
    {
    	//Make a perception rectangle around your space...
    	Rectangle view = new Rectangle(space.x - perception, space.y - perception, space.width + 3*perception, space.height + 3*perception);
    	for(int n = 0; n < Control.critters.size(); n++)
    	{
    		if(checkIf(Control.critters.get(n), "Frog", view))
    			return Control.critters.get(n).space;
    	}
    	return null;
    }
    //changes a baby frog into an adult
    public void growup()
    {
        	gender = 1;
        	if(Math.random() < SOLO_PERC)
        	{
        		pic = new ImageIcon("adultFish.png");
        		solitary = false;
        	}
        	else
        	{
        		pic = new ImageIcon("adultFish.png");
        		solitary = false;
        	}
            //gets bigger
        	int x = space.x - 4;
        	int y = space.y - 4;
        	int size = space.width + 8;
            space = new Rectangle(x, y, size, size);
    }
}
