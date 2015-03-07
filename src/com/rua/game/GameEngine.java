package com.rua.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class GameEngine {
	private Map map;
	private Player player;
	private ArrayList collection;
	
	public GameEngine() {
		map = new Map();
		player = new Player();
		collection = new ArrayList();
	}
	
	public void draw(Graphics g) {
		map.draw(g);
		player.draw(g);
	}
	
	public void update() {
		gameLogic();
		map.setX( map.getX() - player.getVx() );  // update new Map x, y
		map.setY( map.getY() - player.getVy() );
	}
	
	
	/*
	 * All the logic and game play action in here
	 */
	public void gameLogic() {
		// check collision
		int collision = collision();
		if( (player.isRight() || player.isLeft()) && collision == 1 )  // if is wall => stop
			player.setVx(0);
		if( (player.isUp() || player.isDown()) && collision == 1 )
			player.setVy(0);
		//if( collision == 3 )  // if is water => die
		
		//if( collision == 10 )
			
		
	}
	
	
	
	/*
	 * Handle the collision between player and other objects 
	 * return tileKey in Map tiles array
	 */
	private int collision() {
		int[][] mapTiles = map.getMapTiles();
		int tileKey;
		int tileX = 0;
		int tileY = 0;
		BufferedImage playerTile = player.getImage();
		if( player.isRight() ) { // if is going to right
			tileX = ( GamePanel.WIDTH/2 - map.getX() + playerTile.getWidth()/2 )/Map.TILE_SIZE;
			tileY = ( GamePanel.HEIGHT/2 - map.getY() )/Map.TILE_SIZE;
		}
		
		if( player.isLeft() ) { // if is going to left
			tileX = ( GamePanel.WIDTH/2 - map.getX() - playerTile.getWidth()/2 )/Map.TILE_SIZE;
			tileY = ( GamePanel.HEIGHT/2 - map.getY() )/Map.TILE_SIZE;
		}
		
		if( player.isUp() ) { // if is going to right
			tileX = ( GamePanel.WIDTH/2 - map.getX()  )/Map.TILE_SIZE;
			tileY = ( GamePanel.HEIGHT/2 - map.getY() - playerTile.getHeight()/2 )/Map.TILE_SIZE;
		}
		
		if( player.isDown() ) { // if is going to right
			tileX = ( GamePanel.WIDTH/2 - map.getX() )/Map.TILE_SIZE;
			tileY = ( GamePanel.HEIGHT/2 - map.getY() + playerTile.getHeight()/2 )/Map.TILE_SIZE;
		}
		
		tileKey = mapTiles[tileY][tileX];
		return tileKey;
	}	
	
	
	/*
	 * KeyEvent. Get key events to Key Listener
	 */

	public void keyUp() {
		player.setVy(-3);
		player.goUp();
	}

	public void keyDown() {
		player.setVy(3);
		player.goDown();
	}

	public void keyLeft() {
		player.setVx(-3);
		player.turnLeft();
	}

	public void keyRight() {
		player.setVx(3);
		player.turnRight();
	}

	public void keyRelease() {
		player.stop();
	}

}
