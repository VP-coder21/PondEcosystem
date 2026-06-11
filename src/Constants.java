//This Interface File contains all of the preset values for 
//  Screen Sizes, Display Margins, Colors, Fonts,...  
//  BE CAREFUL!  Changing these values, can mess up the look of your visual.

import java.awt.Color;
import java.awt.Font;

public interface Constants {
	
	//Screen Dimensions:
	public static final int WIDE = 1500;
	public static final int HIGH = 800;
	
	//Screen Margins and Display Positions:
	public static final int MARGIN = 75;   //margin around pond.
	public static final int VERT_BAR = 3*WIDE/4;  //the x-position of the vertical bar that separates the pond from the stats.
	public static final int BEACH = 25;    //width of beach boarder (this cuts into the Margin, meaning the Pond edge starts at Margin)
	public static final int PONDW = VERT_BAR - 2*MARGIN;  //pond's actual dimensions.
	public static final int PONDH = HIGH - 2*MARGIN;
	public static final int STATSx = VERT_BAR + MARGIN;   //the position of the stats title banner
	public static final int STATSy = 2*MARGIN;
	
	//Visual's Frame (Refresh) Rate
	public static final int FR = 5;
	
	//Fonts:
	public static final Font LARGEST_FONT = new Font("Times New Roman", Font.BOLD, 34);
	public static final Font LARGE_FONT = new Font("Times New Roman", Font.BOLD, 26);
	public static final Font MEDIUM_FONT = new Font("Times New Roman", Font.BOLD, 20);
	public static final Font SMALL_FONT = new Font("Times New Roman", Font.PLAIN, 14);
	public static final Font SMALLEST_FONT = new Font("Times New Roman", Font.PLAIN, 10);
	
	//Colors:
	public static final Color WATER_DAY_TINT = new Color(80, 175, 195); 
	public static final Color WATER_TWILIGHT_TINT = new Color(40, 150, 130); 
	public static final Color WATER_NIGHT_TINT = new Color(50, 100, 90); 
	public static final Color ROCK_TINT = new Color(100, 80, 40);
	public static final Color FOOD_TINT = new Color(50, 255, 50);
	public static final Color BEACH_DAY_TINT = new Color(200, 140, 0);
	public static final Color BEACH_TWILIGHT_TINT = new Color(190, 120, 0);
	public static final Color BEACH_NIGHT_TINT = new Color(150, 80, 0);
	
}
