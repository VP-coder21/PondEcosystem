// THE POND TASK  
//
// This is the Main for the Task
// Note this sets up the Pond and then launches the Visual.

import java.util.ArrayList;

public class MainPond implements Variables, Constants {

	public static void main(String[] args) {

		setUp();
		new Visual();

	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void setUp()
	{
		//Setting up your Pond
		Control.hour = 1;
		Control.day = 1;
		Control.critters = new ArrayList<Animal>();
		Control.bits = new ArrayList<Mineral>();
		
		//Some values used in placing the rocks...
		double totalarea = PONDW*PONDH;
		double rockarea = 0;
		
		//Adding ROCKS into the Pond:
		//Uses total area to judge if enough rocks have been added.
		//First generate a random rock int he pond (temprock).
		//Then check if it should be connected to another rock, if it is connected to another rock, and
		//  finally determine if it's in a good spot or not.  If it's a good spot, add the rock and update
		//  how much area the rock takes up (using rough estimates).
		while(rockarea < totalarea * ROCK_PERC)
		{
			int x = (int)(Math.random()*(PONDW - RAD*2) + MARGIN + RAD);
			int y = (int)(Math.random()*(PONDH - RAD*2) + MARGIN + RAD);
			
			Mineral temprock = new Mineral(x, y, false);   //make a rock

			boolean connected;
			if(Math.random() < CONNECTED_ROCK_PROB) connected = true;
			else connected = false;
			
			boolean empty_spot = true;
			for(int n = 0; n < Control.bits.size(); n++)
			{
				if(temprock.space.intersects( Control.bits.get(n).space ) )
				{
					empty_spot = false;
				}
			}
			
			boolean good_spot = false;
			if(connected && !empty_spot) good_spot = true;
			if(!connected && empty_spot) good_spot = true;
			
			if(good_spot)  
			{
				Control.bits.add(temprock);
				if(connected) rockarea += (RAD*RAD*1.8);
				else rockarea += (RAD*RAD*3.14159);
			}
		}
			
		
		//Adding FOOD into the Pond:
		//Simply go through every "square" and check if food should be added or not 
		for(int x = MARGIN + FOOD_RAD; x < MARGIN + PONDW - FOOD_RAD; x += FOOD_RAD)
			for(int y = MARGIN + FOOD_RAD; y < MARGIN + PONDH - FOOD_RAD; y += FOOD_RAD)
			{
				if(Math.random() < FOOD_PROB)
					Control.bits.add(new Mineral(x, y, true));
			}
		
		
		//Adding Baby Animals to the Pond:
		//Generate a random location.
		//Check if that location is water (not rock) and check if there are no other animals in that spot...
		//If it is empty, add a Frog at that random location.  There is a counter built in just in case the 
		// Pond gets too crowded and new critters won't fit.  Then there will be less than INOA animals, but the
		// Pond won't crash!
		for(int n = 0; n < INOA; n++)
		{
			int attempts = 0;
			boolean good_spot = false;
			double rand;
			rand = Math.random();
			while(!good_spot && attempts < MAX_ATTEMPTS)
			{
				attempts++;
				int x = (int)(Math.random()*(PONDW - ANIMAL_RAD*2) + MARGIN + ANIMAL_RAD);
				int y = (int)(Math.random()*(PONDH - ANIMAL_RAD*2) + MARGIN + ANIMAL_RAD);
				Animal tempanimal;
				if(rand<0.16666666667) {
					tempanimal = new Frog(x, y);
				}
				else if(rand >= 0.16666666667 && rand < 0.3333333333){
					tempanimal = new Catfish(x, y);
				}
				else if(rand >= 0.3333333333 && rand < .5) {
					tempanimal = new Fish(x, y);
				}
				else if(rand >= .5 && rand < 0.66666666667){
					tempanimal = new Gobbler(x, y);
				}
				else if(rand >=0.66666666667 && rand < 0.83333333333){
					tempanimal = new Bird(x, y);
				}
				else {
					tempanimal = new Piranha(x, y);
				}

				good_spot = true;
				for(int i = 0; good_spot && i < Control.bits.size(); i++)
					if(tempanimal.space.intersects(Control.bits.get(i).space) && Control.bits.get(i).rock)
						good_spot = false;
				for(int i = 0; good_spot && i < Control.critters.size(); i++)
					if(tempanimal.space.intersects(Control.critters.get(i).space))
						good_spot = false;

				if(good_spot)
				{
					Control.critters.add(tempanimal);
				}
			}
		}
		//rock living animals\
		int nestalive = 0;
		for(int n = 0; n <Control.bits.size(); n++) {
			if(Control.bits.get(n).nest) {
				nestalive++;
			}
		}
		int counter =0;
		boolean done;
		for(int n = 0; n <nestalive; n++) {
			double rand = Math.random();
			int x = 0;
			int y = 0;
			done = false;
			while(counter < Control.bits.size() && !done) {
				if(Control.bits.get(counter).nest) {
					x = (int)(Control.bits.get(counter).space.x);
					y = (int)(Control.bits.get(counter).space.y);
					done = true;
				}
				counter++;
			}
			Animal tempanimal;
			if(rand >= 0.00024414062) {
				tempanimal = new Cramorant(x,y);
			}
			else{
				tempanimal = new Cramorant(x,y,true);
			}
			Control.critters.add(tempanimal);
		}

		System.out.println("The Pond is now ready...  Starting Visual.");
	}
}


