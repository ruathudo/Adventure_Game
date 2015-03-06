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

	
	private Player player = new Player();
	private Map map = new Map();
		
	public GamePanel() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
		setBackground(Color.LIGHT_GRAY);
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
		Thread playerUpdate = new Thread ( new Runnable() {
			public void run() {
	
				while(true) {
					player.update(map); // start moving player
					try {
						Thread.sleep(10);
					}
					catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		playerUpdate.start();
	}
	
	
	/*
	 * Override paintComponent of JPanel
	 * Paint the Image type of Objects. 
	 */
	@Override	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// draw player
		map.draw(g);
		player.draw(g);
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

		if (keyCode == KeyEvent.VK_UP) {
			player.goUp(); 
		}

		if (keyCode == KeyEvent.VK_DOWN) {
			player.goDown(); 
			
		}
		if (keyCode == KeyEvent.VK_LEFT) {
			player.turnLeft(); 
		}

		if (keyCode == KeyEvent.VK_RIGHT) {
			player.turnRight();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		player.stop();  // stop player when key realeased
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	
}
