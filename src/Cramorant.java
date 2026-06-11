
	import java.awt.Rectangle;
	import javax.swing.ImageIcon;
	
	public class Cramorant extends Animal {
		public static final double ADULT_NOCTURNAL = .8; //Probabilities used in the move() method.  This means adult frogs only move
		public static final int MINAGE = 7;
		public static final int OLDAGE = 20;
		public static final double SOLO_PERC = .42;
		public static final double AVE_NUM_BABIES = 1;
		public boolean shiny;
		public boolean fish;
		public boolean nested;
		public int countfish;
		
		public Cramorant(int x, int y)
		{
			super(x, y);
			type = "Cramorant";
			pic = new ImageIcon("CramorantEgg.gif");
			int size = 4*ANIMAL_RAD/5;   //this is 16 pixels if using the original values		
			space = new Rectangle(x+7, y+7, size*2, size*2);
			shiny = false;
			above = 2;
			fish = false;
			countfish = 0;
		}
		public Cramorant(int x, int y, boolean s) {
			super(x, y);
			type = "Cramorant";
			pic = new ImageIcon("CramorantEgg.gif");
			int size = 4*ANIMAL_RAD/5;   //this is 16 pixels if using the original values		
			space = new Rectangle(x +7, y +7, size*2, size*2);
			shiny = s;
			above = 2;
			fish = false;
			countfish = 0;
		}
		 public void move() 
		    {
		    	if(!alive) return;       //dead fish don't move
		    	if(health < 10) return;  //unhealthy frogs don't move
		    	if(gender == 0) {
		    		if(age >2 && age < 5) {
		    			pic = new ImageIcon("CramorantEggFaster.gif");
		    		}
		    		else if(age >=5) {
		    			pic = new ImageIcon("CramorantEggFastest.gif");
		    		}
		    	}
		    	//Here are some ways to control how often a critter might move around using probabilities and day times:
		    	//Adult frogs don't move around much in the daytime...
		    	if(gender > 0 && Control.isNighttime() && Math.random() < ADULT_NOCTURNAL) return;  

		    	//Baby frogs are a bit more active in the daytime...
		    	if(gender == 0) return;  

		    	//If it's not daytime, all frogs are always active...
		    	//...

		    	
		    	//You could also write special movement commands that cause the frog to swim underwater or
		    	// leap through the air.  Maybe it's a method that switches its ImageIcon picture based on 
		    	// whether it is above water or underwater...
		    	


		    	//Decide to either move towards others, away from others, or randomly...
		    	//You could make this better by using distances and have the solitary frogs move away from the
		    	//  the closest target... or try to move from the average position if there are more than one.
		    	int delx, dely;
		    	Rectangle attemptedmove;
		    	if(health <= 200 && above > 1) {
		    		above--;// go down
		    	}
		    	else {
		    		above++;
		    	}
		    	if(shiny) {
		    		if(nested) {
		    			pic = new ImageIcon("CramorantNestedShiny.png");
		    			if(space.width > 20) {
		    				int size = 4*ANIMAL_RAD/5;
		    				space = new Rectangle(space.x+7, space.y+7, size*2, size*2);
		    			}
		    		}
		    		else if(fish) {
		    			pic = new ImageIcon("CramorantFlyingFishShiny.gif");
		    			if(space.width <= 24) {
		    				int x = space.x - 5;
		    				int y = space.y - 5;
		    				int size = space.width + 10;
		    				space = new Rectangle(x, y, size, size);
		    			}
		    		}
		    		else {
		    			pic = new ImageIcon("CramorantFlyingShiny.gif");
		    			if(space.width <= 24) {
		    				int x = space.x - 5;
		    				int y = space.y - 5;
		    				int size = space.width + 10;
		    				space = new Rectangle(x, y, size, size);
		    			}
		    		}
		    	}
		    	else {
		    		if(nested) {
		    			pic = new ImageIcon("CramorantNested.png");
		    			if(space.width > 24) {
		    				int size = 4*ANIMAL_RAD/5;
		    				space = new Rectangle(space.x+7, space.y+7, size*2, size*2);
		    			}
		    		}
		    		else if(fish) {
		    			pic = new ImageIcon("CramorantFlyingFish.gif");
		    			if(space.width <= 24) {
		    				int x = space.x - 5;
		    				int y = space.y - 5;
		    				int size = space.width + 10;
		    				space = new Rectangle(x, y, size, size);
		    			}
		    		}
		    		else {
		    			pic = new ImageIcon("CramorantFlying.gif");
		    			if(space.width <= 24) {
		    				int x = space.x - 5;
		    				int y = space.y - 5;
		    				int size = space.width + 10;
		    				space = new Rectangle(x, y, size, size);
		    			}
		    		}
		    	}
		    	Rectangle target = lookAroundfish();
		    	Rectangle nest = lookAroundnest();
		    	if(nested && health > 200) {
		    		delx = 0;
		    		dely = 0;
		    	}
		    	else if(pregnant && nest != null) {
		    		delx = nest.x - space.x;
		    		dely = nest.y - space.y;
		    	}
		    	else if(target != null && health <= 200) //moves towards fish
		    	{
		    		delx = target.x - space.x;
		    		dely = target.y - space.y;
		    	}
		    	else if(nest != null && health > 200) {
		    		delx = nest.x - space.x;
		    		dely = nest.y - space.y;

		    	}
		    	else //moving randomly
		    	{
		    		double ang = Math.random()*Math.PI*2;
		    		delx = (int)((space.width - .5)*Math.cos(ang));
		    		dely = (int)((space.height-.5)*Math.sin(ang));
		    	}
		    	if(nest != null) {
		    		if((nest.x + 1 < delx+space.x || nest.x - 1 > delx + space.x) && (nest.y + 1 < dely+space.y || nest.y - 1 > dely + space.y) && fish) {
		    			nested = true;
		    		}
		    		else {
		    			nested = false;
		    		}
		    	}
		    	else{
		    		nested = false;
		    	}
		    	attemptedmove = new Rectangle(space.x + delx, space.y + dely, space.width, space.height);

		    	//Check if the move goes outside of the pond... if it does, just return home
		    	if(outsidePond(attemptedmove)) return;

		    	space.x += delx;
		    	space.y += dely;
		    	health--; //it costs energ
		    }
		 public void act() 
		    {
		    	if(!alive) return;  //dead frogs don't act
		    	if(gender == 0) return;
		    	//Build a Rectangle to represent your reach:
		    	Rectangle reach = new Rectangle(space.x - space.width, space.y - space.height, space.width * 3, space.height * 3);
		    	for(int n = Control.critters.size()-1; n >= 0; n--)
		    		if(Control.critters.get(n).type.equals("Catfish") && reach.intersects(Control.critters.get(n).space) && Control.critters.get(n).gender > 0 && Control.critters.get(n).alive && fish != true && Control.critters.get(n).above > 0)
		    		{
		    			//Find food, eat food...
		    			hungry = false;
		    			health += Control.critters.get(n).nutrients;
		        		Control.critters.get(n).alive = false;
		        		above = 1;
		        		fish = true;
		        		countfish = 3;
		                Control.died++;
		        		//BE CAREFUL -- you can remove food from Control.bits...  but if you eat/kill a critter **DO NOT REMOVE THEM** 
		        		//Instead just set their alive boolean to false:  Control.critters.get(n).alive = false; 
		        		//They will be removed later.  This is because the food does not ACT(), pulling one from its list doesn't hurt
		        		//anything, but if you do that to a critter it can REALLY mess up the arraylist during the act() sequence
		        	}

		        if(gender != 1) return;  

		        //See if there's a male frog within reach...
		        boolean found = false;
		        int foundn = 0;
		        for(int n = 0; !found && n < Control.critters.size(); n++)
		        {
		        	if(Control.critters.get(n).gender == 2 && !Control.critters.get(n).pregnant && checkIf(Control.critters.get(n), "Cramorant", reach)) {
		        		found = true;
		        		foundn = n;

		        	}
		        }
		        if(found) { 
		        	Control.critters.get(foundn).pregnant = true;
		        }
		    }
		 public void age() 
		    {
		    	if(!alive) return;
		    	if(fish) {
		    		countfish--;
		    		hungry = false;
		    		health += 20;
		    		if(countfish == 0)
		    			fish = false;
		    	}
		    	if(gender > 0) {
		    		if(hungry) health -= DAILY_HUNGER;


		    		hungry = true;  //it will start each day hungry
		    	}
		    	age++;
		    	if(gender == 0 && age > MINAGE) //Baby Frog is growing up!
		    		growup();

		        //Check if the frog is too old...
		        if(age > OLDAGE)
		        {
		        	if(Math.random()*100 > health) {
		        		alive = false;
		        		Control.died++;
		        	}
		        }

		        //Finally, check if the frog is going to have babies.
		        if(!pregnant && !nested) return;

		        //If you're here, you are a pregnant frog about to have babies.
		        pregnant = false;
		        boolean morebabies = true;
		        int attempts = 0;
		        Animal baby;
		        while(morebabies)
		        {
		        	double rand = Math.random();
		        	attempts++;
		        	Rectangle view = new Rectangle(space.x - perception, space.y - perception, space.width + 2*perception, space.height + 2*perception);
			    	for(int n = 0; n < Control.bits.size(); n++)
			    	{
			    		if(Control.bits.get(n).rock && Control.bits.get(n).nest && view.intersects(Control.bits.get(n).space) && !Control.bits.get(n).egg)
			    		{
			    			if(rand >= 0.00024414062) {
			    				baby = new Cramorant(Control.bits.get(n).space.x, Control.bits.get(n).space.y);
			    			}
			    			else {
			    				baby = new Cramorant(Control.bits.get(n).space.x, Control.bits.get(n).space.y,true);
			    			}
			    			Control.critters.add(baby);
			    			Control.bits.get(n).egg = true;
			    			morebabies = false;
			    			Control.born+=1;
			    			nested  = false;
			    		}
			    	}
		        	if(attempts > 100) morebabies = false;
		        }
		    }

		public Rectangle lookAroundfish()
	    {
	    	//Make a perception rectangle around your space...
			int perc = perception - above;
	    	Rectangle view = new Rectangle(space.x - perc, space.y - perc, space.width + 2*perc, space.height + 2*perc);
	    	for(int n = 0; n < Control.critters.size(); n++)
	    	{
	    		if(checkIf(Control.critters.get(n), "CatFish", view) && Control.critters.get(n).age > 0 && Control.critters.get(n).above > 0)
	    		{
	    			return Control.critters.get(n).space;
	        	}
	    	}
	    	return null;
	    }
		public Rectangle lookAroundnest()
	    {
	    	//Make a perception rectangle around your space...
			int perc = perception;
			Rectangle view = new Rectangle(space.x - perc, space.y - perc, space.width + 2*perc, space.height + 2*perc);
			for(int n = 0; n < Control.bits.size(); n++)
			{
				if(Control.bits.get(n).rock && Control.bits.get(n).nest && view.intersects(Control.bits.get(n).space))
				{
					return Control.bits.get(n).space;
				}
			}
			return null;
	    }
		public void growup()
		{
			if(Math.random() < .5) //50% chance of boy frog
			{
				gender = 1;
				if(shiny) {
					pic = new ImageIcon("CramorantFlyingShiny.gif");
				}
				else {
					pic = new ImageIcon("CramorantFlying.gif");
				}
				int x = space.x - 5;
				int y = space.y - 5;
				int size = space.width + 10;
				space = new Rectangle(x, y, size, size);
			}
			else //it's a girl frog
			{
				gender = 2;
				if(shiny) {
					pic = new ImageIcon("CramorantFlyingShiny.gif");
				}
				else {
					pic = new ImageIcon("CramorantFlying.gif");
				}
				nutrients+=10;
				//gets bigger
				int x = space.x - 5;
				int y = space.y - 5;
				int size = space.width + 10;
				space = new Rectangle(x, y, size, size);
			}
		}
	}
