package com.rua.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
		Thread gameLoop = new Thread ( new Runnable() {
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
		Thread gameListener = new Thread ( new Runnable() {
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
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if (keyCode == KeyEvent.VK_1 && !playing) {
			game = new GameEngine();
			loopStart();
			playing = true;
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
