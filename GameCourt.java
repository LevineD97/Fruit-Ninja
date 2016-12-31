import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 * 
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

	// the state of the game logic
	public boolean playing = false; // whether the game is running
	private JLabel status; // Current status text (i.e. Running...)
	private JLabel scoreLabel; // Current score text (i.e Score: 0)
    private LinkedList<Fruit> fruits = new LinkedList<Fruit>();
    private LinkedList<Bomb> bombs = new LinkedList<Bomb>();
    private int fails = 0;
    
	// Game constants
	public static final int COURT_WIDTH = 750;
	public static final int COURT_HEIGHT = 750;

	// Update interval for timer, in milliseconds
	public static final int INTERVAL = 35;
	public static BufferedImage bgIMG;
	public static final String backgroundImg = "fruit_ninja_great_wave_w1.jpg";
	private final MouseDrag drag;
	private final int INIT_SCORE;
    private int score;
    private static JTextField textField;
    private TreeMap<Integer, String> orderedScores = 
    		new TreeMap<Integer, String>(Collections.reverseOrder());
    private BufferedWriter writer;

	public GameCourt(JLabel status, JLabel scoreLabel, int score, 
			JTextField textField) {
		// creates border around the court area, JComponent method
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		INIT_SCORE = score;
		this.score = score;
		this.scoreLabel = scoreLabel;
		this.textField = textField;
		
		try {
			if (bgIMG == null) {
				bgIMG = ImageIO.read(new File(backgroundImg));
			}
		} catch (IOException e) {
			System.out.println("Internal Error:" + e.getMessage());
		}
		// The timer is an object which triggers an action periodically
		// with the given INTERVAL. One registers an ActionListener with
		// this timer, whose actionPerformed() method will be called
		// each time the timer triggers. We define a helper method
		// called tick() that actually does everything that should
		// be done in a single timestep.
		Timer timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
		timer.start(); // MAKE SURE TO START THE TIMER!
        
		// Enable keyboard focus on the court area.
		// When this component has the keyboard focus, key
		// events will be handled by its key listener.
		setFocusable(true);


		this.status = status;
		drag = new MouseDrag();
		addMouseListener(mouseListener);
	}

	/**
	 * (Re-)set the game to its initial state.
	 */
	
	 private class MouseDrag {
	        private Point2D start;
	        private Point2D end;	       
	        
	        private void start(Point2D start) {
	            this.start = start;
	        }

	        private void stop(Point2D end) {
	            this.end = end;
	        }

	        private void reset() {
	            start = null;
	            end = null;
	            
	        }

	        private Point2D getStart() {
	            return start;
	        }

	        private Point2D getEnd() {
	            return end;
	        }

	    }
	
	private MouseAdapter mouseListener = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            drag.start(e.getPoint());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e);
            drag.stop(e.getPoint());

            // find intersected shapes
            Point2D p1 = drag.getStart();
        	Point2D p2 = drag.getEnd();
            for (Iterator<Fruit> iterator = fruits.iterator(); iterator.hasNext();) {
				Fruit element = iterator.next();
				 if (!element.isCutUp() && element.intersects(p1, p2)) {                 
					element.setCutUp(true);
                	incScore();
                	scoreLabel.setText("Score: " + score);
					iterator.remove();
				}
            }        
            for (Iterator<Bomb> iterator = bombs.iterator(); iterator.hasNext();) {
				Bomb element = iterator.next();
				 if (!element.isCutUp() && element.intersects(p1, p2)) {                 
					element.setCutUp(true);
					playing = false;
					status.setText("Game Over...");
				}
            }  
            drag.reset();
        }
    };
	
	
	public void reset() {
        fruits.clear();
        bombs.clear();
        
        add(new Fruit(COURT_WIDTH, COURT_HEIGHT));
		
		playing = true;
		status.setText("Running...");
		resetFails();
		resetScore();
        
		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
	}
	
	public void add(Fruit s) {
        fruits.add(s);
    }

	public void add(Bomb b) {
        bombs.add(b);
    }
	/**
	 * This method is called every time the timer defined in the constructor
	 * triggers.
	 */
	void tick() {
		if (playing) {
			// update the display
			int ticker = (int)(Math.random() * 100) + 1;

			for (Iterator<Fruit> iterator = fruits.iterator(); iterator.hasNext();) {
				Fruit element = iterator.next();
				if (element.pos_y == COURT_HEIGHT - Fruit.SIZE && 
						element.v_y > 0){
					iterator.remove();
					if (!element.isCutUp()) {
						failed();
					}
					else if (element.isCutUp()) { 
						incScore();
						scoreLabel.setText("Score: " + score);
					}
				}
				//			System.out.println(element.pos_x+" "+element.pos_y);
				element.move();
			}

			for (Iterator<Bomb> iterator = bombs.iterator(); iterator.hasNext();) {
				Bomb element = iterator.next();
				if (element.pos_y == COURT_HEIGHT - Bomb.SIZE && 
						element.v_y > 0){
					iterator.remove();
					if (element.isCutUp()) {
						playing = false;	
						status.setText("Game Over...");
					}
				}
	//			System.out.println(element.pos_x+" "+element.pos_y);
				element.move();
			}
			if(ticker >= 97) {
		        add(new Fruit(COURT_WIDTH, COURT_HEIGHT));
            }
			
			if(ticker >= 99) {
				add(new Bomb(COURT_WIDTH, COURT_HEIGHT));
            }			
			repaint();
		}
	}


	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
        g.drawImage(bgIMG, 0, 0, this.getWidth(), this.getHeight(), null);

		
		for (Fruit element : fruits) {
			element.draw(g);
		}
		int fruit_num =  5 - getFails();
		
		for (Bomb element : bombs) {
			element.draw(g);
		}
        
		for (Iterator<Bomb> iterator = bombs.iterator(); iterator.hasNext();) {
			Bomb element = iterator.next();
				if (element.isCutUp()) {
					drawHighScores(g);
				}
			}
		
        g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        g.setColor(Color.RED);
		switch(fruit_num){
		case 5:
			break;
		case 4:
			g.drawString("X", this.getWidth()-40, 50); 
			break;
		case 3:
			g.drawString("X", this.getWidth()-80, 50);
			g.drawString("X", this.getWidth()-40, 50);
			break;
		case 2:
			g.drawString("X", this.getWidth()-120, 50);
			g.drawString("X", this.getWidth()-80, 50);
			g.drawString("X", this.getWidth()-40, 50);
			break;
		case 1:
			g.drawString("X", this.getWidth()-160, 50);
			g.drawString("X", this.getWidth()-120, 50);
			g.drawString("X", this.getWidth()-80, 50);
			g.drawString("X", this.getWidth()-40, 50);
			break;
		case 0:
			g.drawString("X", this.getWidth()-200, 50);
			g.drawString("X", this.getWidth()-160, 50);
			g.drawString("X", this.getWidth()-120, 50);
			g.drawString("X", this.getWidth()-80, 50);
			g.drawString("X", this.getWidth()-40, 50);
			break;
		default:
			g.drawString("X", this.getWidth()-240, 50);
			g.drawString("X", this.getWidth()-200, 50);
			g.drawString("X", this.getWidth()-160, 50);
			g.drawString("X", this.getWidth()-120, 50);
			g.drawString("X", this.getWidth()-80, 50);
			g.drawString("X", this.getWidth()-40, 50);

			playing = false;	
			status.setText("Game Over...");
			drawHighScores(g);
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	}
	public int getFails() {
		return fails;
	}
	public  void resetFails() {
		fails = 0;
	}

	public void failed() {
		fails++;
	}
	public LinkedList<Fruit> getFruits() {
		return fruits;
	}

	public LinkedList<Bomb> getBombs() {
		return bombs;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int newScore) {
		score = newScore;
	}

	public void incScore() {
		score++;
	}

	public void resetScore() {
		score = INIT_SCORE;
		scoreLabel.setText("Score: " + score);
	}

	public TreeMap<Integer, String> gameOver() throws IOException {
		writer = new BufferedWriter(new FileWriter("Scores.txt", true));
		BufferedReader in = new BufferedReader(new FileReader("Scores.txt"));
		if (orderedScores.size() < 5) {
			orderedScores.put(score, textField.getText());
			//writer = new BufferedWriter(new FileWriter("Scores.txt"));
			writer.write(textField.getText()); 
			writer.newLine();
			writer.write(String.valueOf(score));
			writer.newLine();
			writer.close();
		}
		else {
			Integer key = orderedScores.lastKey();
			if (score > key) {
				orderedScores.remove(key);
				orderedScores.put(score, textField.getText());
				writer.write(textField.getText()); 
				writer.newLine();
				writer.write(String.valueOf(score));
				writer.newLine();
				writer.close();   			
			}
			return orderedScores;
		}
		return orderedScores;
	}

	public void drawHighScores(Graphics g) {
		Iterator entries;
		try {
			entries = gameOver().entrySet().iterator();
			int i = 1;
			while (entries.hasNext()) {			
				Entry pair = (Entry)entries.next();
				g.setColor(Color.WHITE);
				g.drawString("Top 5 High Scores:", this.getWidth() - 280, 90);
				g.drawString(pair.getValue() + ": " + pair.getKey(), this.getWidth() - 200, 80 + 50*i);
				i++;
			} 
		}
		catch (IOException e) {}	
	}

}
