import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Piranha extends Animal{

	public static final double ADULT_DAY = .9;  
	public static final double CHILD_DAY = .4; 
	public static final int MINAGE = 3;
	public static final int OLDAGE = 12;
	//public static final double SOLO_PERC = .42;
	public static final double AVE_NUM_BABIES = 1.5;
	
	public Piranha(int x, int y)
	{
		super(x, y);
		type = "Piranha";
		pic = new ImageIcon("piranha.png");
		int size = 5*ANIMAL_RAD/5;
		space = new Rectangle(x -size, y - size, size*2, size*2);
	}
	
	public void move()
	{
		if (!alive) return;
		if (health < 10) return;
		
		//if (Control.isNighttime() && Math.random() < ADULT_DAY) return;  //If it's nighttime, don't move too much
		
		//Swim under a frog and move the frog out of the way
		
		int delx, dely;
		Rectangle attemptedmove;
		
		Rectangle target = lookAround();
		if (solitary && target != null) 
		{
			delx = space.x - target.x;
			dely = space.y - target.y;
		}
		else if (!solitary && target != null)
		{
			delx = target.x - space.x;
			dely = target.y - space.y;
		}
		else
		{
			double ang = Math.random()*Math.PI*2;
			delx = (int)(space.width*Math.cos(ang));
			dely = (int)(space.height*Math.sin(ang));
		}
		
		attemptedmove = new Rectangle(space.x + delx, space.y + dely, space.width, space.height);
		
		if (outsidePond(attemptedmove)) return;
		
		if (Control.pondIsEmptyWater(attemptedmove)	)
		{
			space.x += delx;
			space.y += dely;
			health--;
		}
	}
	
	public void act()
	{
		if (!alive) return;
		
		Rectangle reach = new Rectangle(space.x - space.width, space.y - space.height, space.width * 3, space.height * 3);
		Rectangle freach = new Rectangle(space.x - space.width, space.y - space.height, space.width * 2, space.height * 2);
		
		   
		{
		for (int n = 0; n < Control.critters.size(); n++)
		{
			if (checkIf(Control.critters.get(n), "Frog", freach))
				eatAnimal(Control.critters.get(n));
		}
		}
		
		if (hungry) {
		for(int n = Control.bits.size()-1; n >= 0; n--)
            if(Control.bits.get(n).food && reach.intersects(Control.bits.get(n).space))
            {
            	hungry = false;
                health += Control.bits.get(n).nutrients;
                Control.bits.remove(n);
            }
		}
		
		
		
		if (gender != 2) return;  //baby, males done
		
		if (pregnant) return;
		
		boolean found = false;  //male in reach?
		 for(int n = 0; !found && n < Control.critters.size(); n++)
	        {
	            if(Control.critters.get(n).gender == 1 && checkIf(Control.critters.get(n), "Piranha", reach))
	                found = true;
	        }
	        if(found) pregnant = true;
	}
	
	public void eatAnimal(Animal f)
	{
		if (f.gender == 0) return; 
		
		hungry = false;  //Piranha can eat a frog within its reach but doesn't provide as much
		//					nutrition as food pellets
		
		int nutrition = (Mineral.FOODVAL-7) + f.gender; 
		if (nutrition < DAILY_HUNGER) hungry = true;
		
		health += nutrition;
		f.alive = false;
		
		Control.died++;
		
		space.x = f.space.x;
		space.y = f.space.y;   //add animation when frog is eaten??
	}
	
	public void age()
	{
		if(!alive) return;
		if (hungry) health -= (DAILY_HUNGER + 2);

		hungry = true;

		age++;
		if (gender == 0 && age > MINAGE)
			growup();

		if (age > OLDAGE)
		{
			if (Math.random()*100 > health) {
				alive = false;
				Control.died++;
			}
		}


		if (!pregnant) return;
		
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
        	Animal baby = new Piranha(space.x+delx, space.y+dely);

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
	
	public Rectangle lookAround()
    {
    	//Make a perception rectangle around your space...
    	Rectangle view = new Rectangle(space.x - perception, space.y - perception, space.width + 2*perception, space.height + 2*perception);
    	for(int n = 0; n < Control.critters.size(); n++)
    	{
    		if(checkIf(Control.critters.get(n), "Frogs", view))
    			return Control.critters.get(n).space;
    	}
    	return null;
    }
	
	
	public void growup()
	{
		if(Math.random() < .5) 
           gender = 1;
		else 
           gender = 2;
       
       //pic = new ImageIcon("");
       int x = space.x - 2;
       int y = space.y - 2;
       int size = space.width + 4;
       space = new Rectangle(x, y, size, size);
       
       
       
//       if(Math.random() < .5) //50% chance of boy frog
//       {
//       	gender = 1;
//       	if(Math.random() < SOLO_PERC)
//       	{
//       		pic = new ImageIcon("");
//       		solitary = true;
//       	}
//       	else
//       	{
//       		pic = new ImageIcon("");
//       		solitary = false;
//       	}
//           //gets bigger
//       	int x = space.x - 2;
//       	int y = space.y - 2;
//       	int size = space.width + 4;
//           space = new Rectangle(x, y, size, size);
//       }
//       else //it's a girl frog
//       {
//       	gender = 2;
//       	if(Math.random() < SOLO_PERC)
//       	{
//       		pic = new ImageIcon("");
//       		solitary = true;
//       	}
//       	else
//       	{
//       		pic = new ImageIcon("");
//       		solitary = false;
//       	}
//           //gets bigger
//       	int x = space.x - 4;
//       	int y = space.y - 4;
//       	int size = space.width + 8;
//           space = new Rectangle(x, y, size, size);
	}
	
}

