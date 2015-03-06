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
		try {
			// get the player image source
			this.player = ImageIO.read(new File("Assets/Sprites/player.png"));
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
			g.drawImage(player, getX(), getY(), null);	
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
		collide(map);
		map.setX( mapX - vX );  // update new Map x, y
		map.setY( mapY - vY );
	}
	
	public void stop() {
		this.vX = 0;
		this.vY = 0;
	}
	
	private void collide(Map map) {
		int[][] mapTiles = map.getMapTiles();
		
		if( this.right ) { // if is going to right
			int tileRight = ( Math.abs(map.getX() - player.getWidth()/2) + GamePanel.WIDTH/2 )/Map.TILE_SIZE;
			int tileUp = ( Math.abs(map.getY()) + GamePanel.HEIGHT/2 )/Map.TILE_SIZE;

			if(mapTiles[tileUp][tileRight] == 1) {
				this.vX = 0;
			}
		}
		
		if( this.left ) { // if is going to left
			int tileLeft = ( Math.abs(map.getX() - player.getWidth()) + GamePanel.WIDTH/2 )/Map.TILE_SIZE - 1;
			int tileUp = ( Math.abs(map.getY()) + GamePanel.HEIGHT/2 )/Map.TILE_SIZE;

			if(mapTiles[tileUp][tileLeft] == 1) {
				this.vX = 0;
			}
		}
		
		if( this.up ) { // if is going to right
			int tileLeft = ( Math.abs(map.getX()) + GamePanel.WIDTH/2 )/Map.TILE_SIZE;
			int tileUp = ( Math.abs(map.getY() - player.getHeight()) + GamePanel.HEIGHT/2 )/Map.TILE_SIZE - 1;
			System.out.println(tileLeft+ "-" + tileUp);
			System.out.println(mapTiles[tileUp][tileLeft]);
			if(mapTiles[tileUp][tileLeft] == 1) {
				this.vY = 0;
			}
		}
	}
	
}
