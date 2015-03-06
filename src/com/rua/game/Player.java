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
	private Map map;
	private BufferedImage player;
	
	public Player(Map m) {
		try {
			// get the player image source
			this.player = ImageIO.read(new File("Assets/Sprites/player.png"));
			// make the player stand in the center of window
			this.x = (GamePanel.WIDTH - player.getWidth()) / 2; 
			this.y = (GamePanel.HEIGHT - player.getHeight()) / 2;
			this.map = m;
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
		// if the user go out the map
		if( map.getY() >= 0) {
			this.vY = 0;
			map.setY(0);
		} else {
			this.vY = -2;
		}
		
		this.vX = 0;
		this.up = true;
		this.down = false;
		this.left =false;
		this.right = false;
	}
	
	public void goDown() {
		// if the user go out the map
		if( map.getY() <=  GamePanel.HEIGHT - map.getHeight() ) {
			this.vY = 0;
			map.setY( GamePanel.HEIGHT - map.getHeight() );
		} else {
			this.vY = 2;
		}
		
		this.vX = 0;
		this.down = true;
		this.up = false;
		this.left = false;
		this.right = false;
	}
	 
	public void turnLeft() {
		// if the user go out the map
		if( map.getX() >= 0) {
			this.vX = 0;
			map.setX(0);
		} else {
			this.vX = -2;
		}
		
		this.vY = 0;
		this.left = true;
		this.right = false;
		this.up = false;
		this.down = false;
	}
	
	public void turnRight() {
		// if the user go out the map
		if( map.getX() <=  GamePanel.WIDTH - map.getWidth() ) {
			this.vX = 0;
			map.setX( GamePanel.WIDTH - map.getWidth() );
		} else {
			this.vX = 2;
		}

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
	public void update() {
		int mapX = map.getX();  // get current Map x, y
		int mapY = map.getY();
		this.collide();
		map.setX( mapX - vX );  // update new Map x, y
		map.setY( mapY - vY );
	}
	
	public void stop() {
		this.vX = 0;
		this.vY = 0;
	}
	

	/*
	 * Handle the collision between player and other objects 
	 */
	private void collide() {
		int[][] mapTiles = map.getMapTiles();
		
		if( this.right ) { // if is going to right
			int tileRight = ( GamePanel.WIDTH/2 - map.getX() + player.getWidth()/2 )/Map.TILE_SIZE;
			int tileUp = ( GamePanel.HEIGHT/2 - map.getY() )/Map.TILE_SIZE;

			if( mapTiles[tileUp][tileRight] == 1 ) { // check if the tile position is wall
				this.vX = 0;
			}
		}
		
		if( this.left ) { // if is going to left
			int tileLeft = ( GamePanel.WIDTH/2 - map.getX() - player.getWidth()/2 )/Map.TILE_SIZE;
			int tileUp = ( GamePanel.HEIGHT/2 - map.getY() )/Map.TILE_SIZE;

			if( mapTiles[tileUp][tileLeft] == 1 ) {
				this.vX = 0;
			}
		}
		
		if( this.up ) { // if is going to right
			int tileLeft = ( GamePanel.WIDTH/2 - map.getX()  )/Map.TILE_SIZE;
			int tileUp = ( GamePanel.HEIGHT/2 - map.getY() - player.getHeight()/2 )/Map.TILE_SIZE;
			
			if( mapTiles[tileUp][tileLeft] == 1) {
				this.vY = 0;
			}
		}
		
		if( this.down ) { // if is going to right
			int tileLeft = ( GamePanel.WIDTH/2 - map.getX() )/Map.TILE_SIZE;
			int tileDown = ( GamePanel.HEIGHT/2 - map.getY() + player.getHeight()/2 )/Map.TILE_SIZE;

			if( mapTiles[tileDown][tileLeft] == 1) {
				this.vY = 0;
			}
		}
		
	}
	
	
	
}
