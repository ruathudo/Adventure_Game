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
	Timer tm = new Timer(5, this); // Timer for Action Listener interval
	
	// Window dimension 
	public static final int WIDTH = 320;  
	public static final int HEIGHT = 320;  

	private GameEngine game = new GameEngine();
	
		
	public GamePanel() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
		setBackground(Color.BLACK);
		tm.start(); 
		loopStart();
		addKeyListener(this);  // Add key listener to this GamePanel
	}
	
	/*
	 * Game loop start
	 * Define thread. Control game action
	 */
	public void loopStart () {
		System.out.println("Starting ");
		
		// Thread for player
		Thread gameLoop = new Thread ( new Runnable() {
			public void run() {
	
				while(true) {
					game.update(); // start moving player
					try {
						Thread.sleep(10);
					}
					catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		gameLoop.start();
	}
	
	
	/*
	 * Override paintComponent of JPanel
	 * Paint the Image type of Objects. 
	 */
	@Override	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// draw player
		game.draw(g);
	}

	/*
	 * Perform action after each event
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

//	/*
//	 * KeyEvent. Get key events to Key Listener
//	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();

		if (keyCode == KeyEvent.VK_UP) {
			game.keyUp(); 
		}

		if (keyCode == KeyEvent.VK_DOWN) {
			game.keyDown(); 
			
		}
		if (keyCode == KeyEvent.VK_LEFT) {
			game.keyLeft(); 
		}

		if (keyCode == KeyEvent.VK_RIGHT) {
			game.keyRight();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		game.keyRelease();  // stop player when key realeased
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
