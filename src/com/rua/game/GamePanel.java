package com.rua.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements ActionListener, KeyListener{
	// Window dimension 
	public static final int WIDTH = 320;  
	public static final int HEIGHT = 320;  

	private GameEngine game;
	private Menu menu;
	private Timer tm = new Timer(30, this); // Timer for Action Listener interval
	private boolean playing = false;
	private Thread gameLoop;
	private Thread gameListener;

		
	public GamePanel() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
		setBackground(Color.BLACK);
		
		menu = new Menu();
		//game = new GameEngine();
		tm.start(); 
		//loopStart();
		
		addKeyListener(this);  // Add key listener to this GamePanel
	}
	
	/*
	 * Game loop start
	 * Define thread. Control game action
	 */
	public void loopStart () {
		System.out.println("Starting ");
		
		// Thread for game loop
		gameLoop = new Thread ( new Runnable() {
			public void run() {
	
				while(true) {
					game.update(); // start moving player
					try {
						Thread.sleep(30); // ~30 fps
					}
					catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		gameLoop.start();
		
		// Thread for game listener
		gameListener = new Thread ( new Runnable() {
			public void run() {
	
				while(true) {
					game.listen(); // 
					try {
						Thread.sleep(1000); // listen every second
					}
					catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		gameListener.start();
	}
	
	
	/*
	 * Override paintComponent of JPanel
	 * Paint the Image type of Objects. 
	 */
	@Override	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// draw menu
		if( playing )
			game.draw(g);
		else
			menu.draw(g);
		//game.draw(g);
	}

	/*
	 * Perform action after each event
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	/*
	 * KeyEvent. Get key events to Key Listener
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
			/********** Pause Game *************/
		if (keyCode == KeyEvent.VK_ESCAPE && playing) {
			gameLoop.suspend();
			gameListener.suspend();
			playing = false;
		}
		else
			/********** Resume Game *************/
		if (keyCode == KeyEvent.VK_ESCAPE && !playing) {
			gameLoop.resume();
			gameListener.resume();
			playing = true;
		}
		else
			/********** Start New Game *************/
		if (keyCode == KeyEvent.VK_1 && !playing) {
			game = new GameEngine();
			loopStart();
			playing = true;
		}
		else
			/********** Save Game *************/
		if (keyCode == KeyEvent.VK_2 && !playing) {
			try {
		         FileOutputStream fileOut = new FileOutputStream("tmp/game.ser");
		         ObjectOutputStream out = new ObjectOutputStream(fileOut);
		         out.writeObject(game);
		         out.close();
		         fileOut.close();
		         System.out.printf("Serialized data is saved in tmp/game.ser");
		     }catch(IOException i) {
		          i.printStackTrace();
		     }
		}
		else
		if (keyCode == KeyEvent.VK_UP)
			game.keyUp(); 
		else
		if (keyCode == KeyEvent.VK_DOWN)
			game.keyDown(); 
		else
		if (keyCode == KeyEvent.VK_LEFT)
			game.keyLeft(); 
		else
		if (keyCode == KeyEvent.VK_RIGHT)
			game.keyRight();
		else
		if (keyCode == KeyEvent.VK_SPACE)
			game.keySpace();
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode != KeyEvent.VK_SPACE && playing)
			game.keyRelease();  // stop player when key realeased	
	}

	@Override
	public void keyTyped(KeyEvent e) {}

}
