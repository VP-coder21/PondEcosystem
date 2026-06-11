//This Class does all of the work of keeping track of variables and calling various methods...


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Control implements Variables, Constants {

	public static int born = 0;
	public static int died = 0;
	public static int hour;
	public static int day;
	public static ArrayList<Animal> critters;
	public static ArrayList<Mineral> bits;
	public static int maxpop;
	public static int maxpopday;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//Critter/Daily Controls:
	public static void action() 
	{
		for(int n = critters.size() - 1; n >= 0; n--)
		{
			Animal moving = critters.remove(n);
			moving.move();
			critters.add(moving);
		}
		for(int n = 0; n < critters.size(); n++)
			critters.get(n).act();
	}
	public static void nextTurn() 
	{
        hour++;
        if(hour == 24) 
        {
            hour = 0;
            day++;
            nextDay();
        }
	}
	public static void nextDay() 
	{
        for(int n = 0; n < critters.size(); n++)
            critters.get(n).age();
        
        for(int n = critters.size()-1; n >= 0; n--)
            if(!critters.get(n).alive) critters.remove(n);
        
        
        //Adding new food to empty (water only) spaces
        //  You may need to alter this method to add food to rocks if you want that!
        for(int n = 0; n < ADD_FOOD; n++)
        {
        	int attempts = 0;
        	boolean done = false;
        	
        	while(!done && attempts < MAX_ATTEMPTS)
        	{
        		attempts++;
        		
        		int x = (int)(Math.random()*(PONDW - 2*FOOD_RAD)) + MARGIN + FOOD_RAD;
        		int y = (int)(Math.random()*(PONDH - 2*FOOD_RAD)) + MARGIN + FOOD_RAD;
            
        		Mineral tempfood = new Mineral(x, y, true);
        		if(pondIsEmptyWater(tempfood.space))
        		{
        			bits.add(tempfood);
        			done = true;
        		}
        	}
        }
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    //Helper method to check the spaces of the Pond:
	//Checks if the space is open water (though it can contain food...)
	public static boolean pondIsEmptyWater(Rectangle target)
    {
		for(int n = 0; n < bits.size(); n++)
			if(target.intersects(bits.get(n).space) && bits.get(n).rock)
				return false;
		
		for(int n = 0; n < critters.size(); n++)
			if((target.intersects(critters.get(n).space)))
				return false;
		
		return true;
    }    
        
	
	//Helper methods to check the time of day...
	public static boolean isDaytime()
	{
		if(hour > 7 && hour < 19) return true;
		else return false;
	}
	public static boolean isNighttime()
	{
		if(hour > 20 || hour < 6) return true;
		else return false;
	}

	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//This method paints the screen (some text is drawn in Visual... the rest is drawn here)
	public static void drawInterface(Graphics g)
	{
		//Draw the beach and water
		g.setColor(BEACH_TWILIGHT_TINT);
		if(isDaytime()) g.setColor(BEACH_DAY_TINT);
		if(isNighttime()) g.setColor(BEACH_NIGHT_TINT);
		g.fillRect(MARGIN - BEACH, MARGIN - BEACH, PONDW + 2*BEACH, PONDH + 2*BEACH);
		
		g.setColor(WATER_TWILIGHT_TINT);
		if(isDaytime()) g.setColor(WATER_DAY_TINT);
		if(isNighttime()) g.setColor(WATER_NIGHT_TINT);
		g.fillRect(MARGIN, MARGIN, PONDW, PONDH);
		
		
		//draw the rocks and food and animals
		for(int n = 0; n < bits.size(); n++)
			bits.get(n).draw(g);
		for(int n = 0; n < bits.size(); n++)
			bits.get(n).nests(g);
		
		for(int n = 0; n < critters.size(); n++)
			critters.get(n).draw(g);
				
		
		//draw the right side of the screen
		int currentpop = (critters.size());
		int currentpopfrog = 0;
		for(int n = 0; n < critters.size(); n++) {
			if(critters.get(n).type.equals("Frog")) {
				currentpopfrog++;
			}
		}
		int currentpopcatfish = 0;
		for(int n = 0; n < critters.size(); n++) {
			if(critters.get(n).type.equals("Catfish")) {
				currentpopcatfish++;
			}
		}
		int currentpopcramorant = 0;
		for(int n = 0; n < critters.size(); n++) {
			if(critters.get(n).type.equals("Cramorant")) {
				currentpopcramorant++;
			}
		}
		int currentpopfish = 0;
		for(int n = 0; n < critters.size(); n++) {
			if(critters.get(n).type.equals("Fish")) {
				currentpopfish++;
			}
		}
		int currentpopnfg = 0;
		for(int n = 0; n < critters.size(); n++) {
			if(critters.get(n).type.equals("Notorious Frog Gobbler")) {
				currentpopnfg++;
			}
		}
		int currentpopbird = 0;
		for(int n = 0; n < critters.size(); n++) {
			if(critters.get(n).type.equals("Bird")) {
				currentpopbird++;
			}
		}
		int currentpoppiranha = 0;
		for(int n = 0; n < critters.size(); n++) {
			if(critters.get(n).type.equals("Piranha")) {
				currentpoppiranha++;
			}
		}
		
		int marginadder = 0;
		int tlehealth = 0;
		int tleage = 0;
		int births = 0;
		int deaths = died;
		if(day == 1) {
			births =0;
		}
		else {
			births = born;
		}
		if(currentpop > maxpop) {
			maxpop = currentpop;
			maxpopday = day;
		}
		else if(currentpop == maxpop) {
			maxpopday = day;
		}
		int oldcritage = 0;
		int oldcrit = 0;
		
		for(int n = 0; n < critters.size(); n++) {
			if(oldcritage < critters.get(n).age) {
				oldcritage = critters.get(n).age;
				oldcrit = n;
			}
		}
		for(int n = 0; n < critters.size(); n++) {
			tlehealth += critters.get(n).health;
		}
		for(int n = 0; n < critters.size(); n++) {
			tleage += critters.get(n).age;
		}
		double avghealth = tlehealth/(double)critters.size();
		double avgage = tleage/(double)critters.size();
		double birthrate = births /(double) day;
        g.setColor(Color.LIGHT_GRAY);
        g.drawLine(VERT_BAR, 0, VERT_BAR, HIGH);
        g.setFont(MEDIUM_FONT);
        g.drawString("Pond Statistics", STATSx + MARGIN - 40, STATSy);
        g.setFont(SMALL_FONT);
        g.drawString("The current Population of animals is " + currentpop, STATSx + MARGIN/2 -40, STATSy + MARGIN);
        marginadder+=30;
        g.drawString("The current Population of Frogs is " + currentpopfrog, STATSx + MARGIN/2 -40, STATSy + MARGIN + marginadder);
        marginadder+=30;
        g.drawString("The current Population of Catfish is " + currentpopcatfish, STATSx + MARGIN/2 -40, STATSy + MARGIN+marginadder);
        marginadder+=30;
        g.drawString("The current Population of Cramorant is " + currentpopcramorant, STATSx + MARGIN/2 -40, STATSy + MARGIN+marginadder);
        marginadder+=30;
        g.drawString("The current Population of Fish is " + currentpopfish, STATSx + MARGIN/2 -40, STATSy + MARGIN + marginadder);
        marginadder+=30;
        g.drawString("The current Population of The NFG's is " + currentpopnfg, STATSx + MARGIN/2 -40, STATSy + MARGIN + marginadder);
        marginadder+=30;
        g.drawString("The current Population of Birds is " + currentpopbird, STATSx + MARGIN/2 -40, STATSy + MARGIN+ marginadder);
        marginadder+=30;
        g.drawString("The current Population of Piranhas is " + currentpoppiranha, STATSx + MARGIN/2 -40, STATSy + MARGIN+ marginadder);
        marginadder+=30;
        g.drawString("The max Population ever was " + maxpop +" on day "+ maxpopday, STATSx + MARGIN/2 -40, STATSy + MARGIN + marginadder);
        marginadder+=30;
        g.drawString("The oldest critter alive is Animal #" + oldcrit +" at "+ oldcritage +" days old", STATSx + MARGIN/2 -40, STATSy + MARGIN + marginadder);
        marginadder+=30;
        g.drawString("The birth rate of the Animals is " + String.format("%.2g%n", birthrate), STATSx + MARGIN/2 -40, STATSy + MARGIN + marginadder);
        marginadder+=30;
        g.drawString("The Average Health of the Animals is " + String.format("%.5g%n", avghealth), STATSx + MARGIN/2 -40, STATSy + MARGIN + marginadder);
        marginadder+=30;
        g.drawString("The Average Age of the Animals is " +String.format("%.3g%n", avgage) , STATSx + MARGIN/2 -40, STATSy + MARGIN + marginadder);
        marginadder+=30;
        g.drawString("The amount of Animals that have died is " + deaths, STATSx + MARGIN/2 -40, STATSy + MARGIN + marginadder);
        marginadder+=30;
        g.drawString("The amount of Animals that have been born is " + births, STATSx + MARGIN/2 -40, STATSy + MARGIN + marginadder);
        //YOUR POND STATS GO HERE.
        //LIST IMPORTANT DATA PIECES -- either in Strings or Graphically
        //Some info we may be concerned with:
        //   -- Current Pop x
        //   -- Max pop (day) x
        //   -- Oldest Critter x
        //   -- Birth Rate...
        //   -- Current Ave Health x
        //   -- Current Ave Age x
        //   ......
	}
}
