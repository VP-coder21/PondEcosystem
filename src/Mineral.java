//A simple class that can be used to make inanimate objects on the screen...  rocks, food, trees... whatever.
//This can be done without making subclasses of this, just use data members to distinguish the difference between
// the different object types.

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Mineral implements Variables, Constants {

	public static final int FOODVAL = 10;   //the standard nutritional value of food
	
	public boolean rock;
	public boolean food;
	public int nutrients;
	public Color tint;
	public Rectangle space;
	public boolean nest;
	public boolean egg;
	public ImageIcon pic;
			
	public Mineral(int x, int y, boolean food_)
	{
		pic = new ImageIcon("PlaceHolder.png");
        //The (x,y) parameters are translated to the center of the Mineral's Rectangle space
		if(food_)
		{
			food = true;
			rock = false;
			nutrients = FOODVAL;
			tint = FOOD_TINT;
			space = new Rectangle(x - FOOD_RAD, y - FOOD_RAD, 2*FOOD_RAD, 2*FOOD_RAD);
			egg = false;
		}
		else
		{
			food = false;
			rock = true;
			nutrients = -1;
			tint = ROCK_TINT;
			egg = false;
			if(Math.random() < .05) {
				nest = true;
				pic = new ImageIcon("Nest.png");
				egg = true;
			}
			space = new Rectangle(x - RAD, y - RAD, 2*RAD, 2*RAD);
		}
	}
	public void draw(Graphics g)
	{
		g.setColor(tint);
		g.fillOval(space.x, space.y, space.width, space.height);
	}
	public void nests(Graphics g)
	{
		if(nest) {
			g.drawImage(pic.getImage(), space.x + 5, space.y + 5, space.width - 10, space.height- 10, null);
		}
	}
}
