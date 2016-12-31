import static org.junit.Assert.*;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.junit.Test;

public class FruitTests {

	public final JLabel status = new JLabel("Running...");
	public final static int INIT_SCORE = 0;
	public final JLabel scoreLabel = new JLabel("Score: " + INIT_SCORE);
	public final JTextField textField = new JTextField("");
	public final int courtWidth = 750; 
	public final int courtHeight = 750;
	
	GameCourt gc = new GameCourt(status, scoreLabel, INIT_SCORE, textField);
	
	@Test
	public void testScoreIncrementsUponFruitSlice() {
		gc.reset();
		Fruit fruit = new Fruit(courtWidth, courtHeight);
		fruit.setCutUp(true);
		assertTrue("Score incremented!", gc.getScore() == 1);
	}
	
	@Test
	public void testGameEndsUponBombSlice() {
		Bomb bomb = new Bomb(courtWidth, courtHeight);
		bomb.setCutUp(true);	
		assertTrue("Game over!", status.getText().equals("Game Over..."));
	}
	
	@Test
	public void cursorIntersectsFruit() {
		Fruit fruit = new Fruit(courtWidth, courtHeight);
		Point2D start = new Point();
        Point2D end = new Point();
        start.setLocation(400, 400);
        end.setLocation(600,600);
		fruit.pos_x = 500;
		fruit.pos_y = 500;
		fruit.width = 85;
		fruit.height = 85;
		assertTrue("Intersection successful!", fruit.intersects(start, end));
	}
	
	@Test
	public void offScreenFruitRemovedFromList() {
		LinkedList<Fruit> fruits = gc.getFruits();
		gc.reset();
		Fruit fruit = new Fruit(courtWidth, courtHeight);
		fruits.add(fruit);
		// test to see that, after an appropriate amount of iterations, the
		// fruit has been removed from the LinkedList
		// i <= 70 calculated using d_y = v_y*t + (1/2)a_y*t^2
		for(int i = 0; i <= 70; i++) { 
			gc.tick();			
		}
		assertFalse("Fruit removed from LinkedList!", fruits.contains(fruit));
		// ensure that the number of fails has been incremented by at least 1
		assertTrue("Missed a fruit!", gc.getFails() >= 1); 
	}

	@Test
	public void offScreenBombRemovedFromList() {
		LinkedList<Bomb> bombs = gc.getBombs();
		gc.reset();
		Bomb bomb = new Bomb(courtWidth, courtHeight);
		bombs.add(bomb);
		for(int i = 0; i <= 50; i++) { 
			gc.tick();			
		}
		assertFalse("Bomb removed from LinkedList!", bombs.contains(bomb));
	}
	
	@Test
	public void fruitFallsBackDown() {
		LinkedList<Fruit> fruits = gc.getFruits();
		gc.reset();
		Fruit fruit = new Fruit(courtWidth, courtHeight);
		fruits.add(fruit);
			gc.tick();			
		assertTrue(fruit.v_y < 0);
		for(int i = 0; i <= 70; i++) { 
			gc.tick();			
		}
		assertTrue(fruit.v_y > 0);
	}
	
	
}
