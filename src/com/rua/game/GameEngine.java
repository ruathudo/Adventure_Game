package com.rua.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class GameEngine {
	private Map map;
	private Player player;
	private ArrayList<Thing> collection;
	
	public GameEngine() {
		map = new Map();
		player = new Player();
		collection = new ArrayList<Thing>();
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
		int[] collision = collision();
		
		if( (player.isRight() || player.isLeft()) && collision[0] == Map.WALL )  // if is wall => stop
			player.setVx(0);
		
		if( (player.isUp() || player.isDown()) && collision[0] == Map.WALL )
			player.setVy(0);
		
		
		//if( collision == 3 )  // if is water => die
		if( collision[0] == Map.TILE ) {
			Room currentRoom = map.getRoom(collision[1], collision[2]); // check room
			if( currentRoom != null)
			System.out.println(currentRoom.getName());
		}
		
		if( collision[0] == Map.FLASHLIGHT ) {
			this.collection.add(new Flashlight("Flashlight"));  // add new flashlight object to collection
			map.setMapTile(collision[1], collision[2], Map.TILE);  // change flashlight to TILE image
		}
	}
	
	
	
	/*
	 * Handle the collision between player and other objects 
	 * return array tileKey, tileX, tileY in Map tiles array
	 */
	private int[] collision() {
		int[][] mapTiles = map.getMapTiles();
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
		
		int tileKey = mapTiles[tileY][tileX];
		int[] tilePos = {tileKey, tileY, tileX};
		return tilePos;
	}	
	
	
	/*
	 * KeyEvent. Get key events to Key Listener
	 */

	public void keyUp() {
		player.setVy(-4);
		player.goUp();
	}

	public void keyDown() {
		player.setVy(4);
		player.goDown();
	}

	public void keyLeft() {
		player.setVx(-4);
		player.turnLeft();
	}

	public void keyRight() {
		player.setVx(4);
		player.turnRight();
	}

	public void keyRelease() {
		player.stop();
	}

}
