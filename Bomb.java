import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Bomb extends Projectile {


	public static final int SIZE = 75;
	public static final int INIT_VEL_X = 1;
	public static final int INIT_VEL_Y = 3;
	public static double vx = Math.random() * 5;
	public static double vy = -1;
	public BufferedImage img;
	public static final String bombImg = "Bomb.png";
	
	public Bomb(int courtWidth, int courtHeight) {
		super(INIT_VEL_X, INIT_VEL_Y, (int)(Math.random() * courtWidth + 1), 0, SIZE, SIZE, courtWidth, courtHeight);
		
		try {
			if (img == null) {
				img = ImageIO.read(new File(bombImg));
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