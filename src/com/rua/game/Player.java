package com.rua.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Player {
	private int x, y;   // position
	private int vX = 0;  // velocity
	private int vY = 0;
	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	private BufferedImage player;
	private ArrayList<BufferedImage> playerAssets = new ArrayList<BufferedImage>();
	
	public Player() {
		try {
			// get the player image source
			BufferedImage playerDefault = ImageIO.read(new File("Assets/Sprites/player.png"));
			BufferedImage playerFlash = ImageIO.read(new File("Assets/Sprites/player_2.png")); 
			playerAssets.add(playerDefault);
			playerAssets.add(playerFlash);
			
			this.player = playerDefault;
			// make the player stand in the center of window
			this.x = (GamePanel.WIDTH - player.getWidth()) / 2; 
			this.y = (GamePanel.HEIGHT - player.getHeight()) / 2;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Draw player image
	 */
	public void draw(Graphics g) {
		g.drawImage(player, x, y, null);	
	}

	public BufferedImage getImage() {
		return this.player;
	}
	
	// change player image when have flash in hand
	public void changePlayer(boolean hasFlash){
		if( hasFlash )
			this.player = playerAssets.get(1);
		else 
			this.player = playerAssets.get(0);
	}
	
	// Get and set moving information
	//////////////////////////////////////////
	
	public int getX() { return this.x; }
	public int getY() { return this.y; }
	public int getVx() { return this.vX; }
	public int getVy() { return this.vY; }
	public void setVx(int v) { vX = v; }
	public void setVy(int v) { vY = v; }
	public boolean isUp() { return this.up; }
	public boolean isDown() { return this.down; }
	public boolean isLeft() { return this.left; }
	public boolean isRight() { return this.right; }
	
	///////////////////////////////////////////
	
	
	/*
	 * Moving function
	 */
	public void goUp() {
		this.vX = 0;
		this.up = true;
		this.down = false;
		this.left =false;
		this.right = false;
	}
	
	public void goDown() {
		this.vX = 0;
		this.down = true;
		this.up = false;
		this.left = false;
		this.right = false;
	}
	 
	public void turnLeft() {
		this.vY = 0;
		this.left = true;
		this.right = false;
		this.up = false;
		this.down = false;
	}
	
	public void turnRight() {
		this.vY = 0;
		this.right = true;
		this.left = false;
		this.up = false;
		this.down = false;
	}
		
	public void stop() {
		this.vX = 0;
		this.vY = 0;
		this.left = false;
		this.right = false;
		this.up = false;
		this.down = false;
	}

	
	
	
}
