import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Fruit extends Projectile {


	public static final int SIZE = 85;
	public static final int INIT_VEL_X = 1;
	public static final int INIT_VEL_Y = -35;
	
//	private FruitType type;
	public static final String appleImg = "Red_Apple.png";
	public static final String mangoImg = "Mango.png";
	public static final String pineappleImg = "Pineapple.png";
	public static final String strawberryImg = "Strawberry.png";
	public static final String watermelonImg = "Watermelon.png";

	public static double vx = Math.random() * 5;
	public static double vy = -1;
	public BufferedImage img;


	

	public Fruit(int courtWidth, int courtHeight) {
		super(INIT_VEL_X, INIT_VEL_Y, (int)(Math.random() * courtWidth + 1), courtHeight, SIZE, SIZE, courtWidth, courtHeight);
		
		
		
		FruitType fruitType = FruitType.getRandom();

		if(fruitType == FruitType.Apple)
			try {
				if (img == null) {
					img = ImageIO.read(new File(appleImg));
				}
			} catch (IOException e) {
				System.out.println("Internal Error:" + e.getMessage());
			}
		if(fruitType == FruitType.Mango)
			try {
				if (img == null) {
					img = ImageIO.read(new File(mangoImg));
				}
			} catch (IOException e) {
				System.out.println("Internal Error:" + e.getMessage());
			}
		if(fruitType == FruitType.Pineapple)
			try {
				if (img == null) {
					img = ImageIO.read(new File(pineappleImg));
				}
			} catch (IOException e) {
				System.out.println("Internal Error:" + e.getMessage());
			}
		if(fruitType == FruitType.Strawberry)
			try {
				if (img == null) {
					img = ImageIO.read(new File(strawberryImg));
				}
			} catch (IOException e) {
				System.out.println("Internal Error:" + e.getMessage());
			}
		if(fruitType == FruitType.Watermelon)
			try {
				if (img == null) {
					img = ImageIO.read(new File(watermelonImg));
				}
			} catch (IOException e) {
				System.out.println("Internal Error:" + e.getMessage());
			}
	}
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, pos_x, pos_y, width, height, null);
	}

}

