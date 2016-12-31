
// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
	private static final int INIT_SCORE = 0;
	
	@Override
	public void run() {
		
		final JFrame startFrame = new JFrame("Fruit Ninja");
		startFrame.setLocation(300, 300);
		
		
		// Top-level frame in which game components live
		final JFrame frame = new JFrame("Fruit Ninja");
		
		frame.setLocation(300, 300);

		// Status panel
		final JPanel status_panel = new JPanel();
		
		frame.add(status_panel, BorderLayout.SOUTH);
		final JLabel status = new JLabel("Running...");
		final JLabel prompt = new JLabel("Enter Name to Start");
		final JTextField textField = new JTextField("");
		textField.setColumns(25);
        status_panel.add(prompt);
		status_panel.add(textField); 
		status_panel.add(status);

		// Initialize score button
		final JLabel scoreLabel = new JLabel("Score: " + INIT_SCORE);
		
		
		// Score panel
		final JPanel score_panel = new JPanel();
		score_panel.add(scoreLabel);
		
		// Main playing area
		final GameCourt court = new GameCourt(status, scoreLabel, INIT_SCORE, textField);
		frame.add(court, BorderLayout.CENTER);
		
		// Reset button
		final JPanel control_panel = new JPanel();
		
		frame.add(control_panel, BorderLayout.NORTH);

		// Note here that when we add an action listener to the reset
		// button, we define it as an anonymous inner class that is
		// an instance of ActionListener with its actionPerformed()
		// method overridden. When the button is pressed,
		// actionPerformed() will be called.
		final JButton reset = new JButton("Restart");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				court.reset();
			}
		});

		final String instruct = "Instructions:\n" +
		"Hello and welcome to Fruit Ninja! The rules for the game are as\n"
		+ "follows: slice as many fruits as you can and avoid bombs at all\n"
		+ "costs! Once you have either failed to slice 6 fruits in time or hit\n"
		+ "a bomb, the game will be over and our score will be recorded! In order\n"
		+ "to slice a fruit, click and drag the cursor through it! A swipe is not\n"
		+ "registered until after you release your click, so make sure to be quick!\n";
		
		final JButton instructions = new JButton("Instructions");
		instructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(new JFrame(), instruct);
			}
		});

		
		final JPanel toolbar = new JPanel();
		toolbar.add(scoreLabel);
		toolbar.add(reset);
		toolbar.add(instructions);
		control_panel.add(toolbar);


		// Put the frame on the screen
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		
		
		textField.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent event) {
		        //System.out.println("The entered text is: " + textField.getText());
		        court.reset();
		    }
		});
	}

	/*
	 * Main method run to start and run the game Initializes the GUI elements
	 * specified in Game and runs it IMPORTANT: Do NOT delete! You MUST include
	 * this in the final submission of your game.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Game());
	}
}

