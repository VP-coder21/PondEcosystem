//This Interface File contains the values of various elements of randomness in your program
//  From controlling how many rocks and animals you start with, to controlling the 
//  chance that new food will show up each day... 
//Note:  Some variables are located in each class, like the specific lifespan of a frog or
//  the nutritional value of food... 

//You **SHOULD** change these values to find a starting configuration that you like.
//AND BE SURE TO LOOK THROUGH EACH CLASS'S VARIABLES - you can/should adjust those too.

public interface Variables {

	//Program Running Variables:
	public static final int RAD = 20;           //the standard RADIUS (a square of land is TWO*RAD number of pixels across)
	public static final int ANIMAL_RAD = RAD-4; //the standard size (radius) of a standard animal.
	public static final int FOOD_RAD = RAD/5;   //the standard size (radius) of a standard food pellet
	public static final int DELAY = 15;         //number of cycles between hours... lower numbers speed up the program
	
	
	//Set-Up Values:
	public static final int INOA = 15;  //Initial Number Of Animals 
	public static final int MAX_ATTEMPTS = 10000;  //When placing animals initially, this prevents infinite loops
	public static final double FOOD_PROB = .003;    //the probability that EACH "square" of land will contain food 
	public static final double ROCK_PERC = .10;    //the total percentage of the pond that will be rocks (roughly)
	public static final double CONNECTED_ROCK_PROB = .90;   //the probability that any two rocks will be connected/touching 
	
	
	//Regarding Food in the environment:	
	public static final int ADD_FOOD = 10;  //roughly the amount of new food added each new day
	public static final int DAILY_HUNGER = -4; //How much health/energy is lost each day if you don't eat
}
