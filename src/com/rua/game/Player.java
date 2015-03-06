package com.rua.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
	
	public Player() {
		this.x = GamePanel.WIDTH / 2;
		this.y = GamePanel.HEIGHT / 2;
	}
	
	/*
	 * Draw player image
	 */
	public void draw(Graphics g) {
		try {
			this.player = ImageIO.read(new File("Assets/Sprites/player.png"));
			g.drawImage(player, getX(), getY(), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BufferedImage getImage() {
		return this.player;
	}
	
	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}
	
	public void goUp() {
		this.vY = -2;
		this.vX = 0;
		this.up = true;
		this.down = false;
		this.left =false;
		this.right = false;
	}
	
	public void goDown() {
		this.vY = 2;
		this.vX = 0;;
		this.down = true;
		this.up = false;
		this.left = false;
		this.right = false;
	}
	 
	public void turnLeft() {
		this.vX = -2;
		this.vY = 0;
		this.left = true;
		this.right = false;
		this.up = false;
		this.down = false;
	}
	
	public void turnRight() {
		this.vX = 2;
		this.vY = 0;
		this.right = true;
		this.left = false;
		this.up = false;
		this.down = false;
	}
	
	/*
	 * Keep the position of player always in the center of window
	 * move the map position
	 */
	public void update(Map map) {
		int mapX = map.getX();  // get current Map x, y
		int mapY = map.getY();
		map.setX( mapX - vX );  // update new Map x, y
		map.setY( mapY - vY );
	}
	
	public void stop() {
		this.vX = 0;
		this.vY = 0;
	}
	
	private void collide(Map map) {
		int[][] mapTiles = map.getMapTiles();
		// tileleft = (abs(map.getX()) + player.x ) / tileSize + 1;	
		// tileRight = (abs(map.getX()) + player.x ) / tileSize - 1;
		// Or tileLeft = (map.Width - window.Width) / 2 + 1
		// if the player always in the middle of window
	}
	
}
