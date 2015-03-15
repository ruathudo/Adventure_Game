package com.rua.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;


public class GameEngine {
	private Map map;
	private Player player;
	private InfoBar infoBar;
	private Flashlight flashlight;
	private ArrayList<Thing> collection;  // contain Thing objects
	private HashMap<String, Key> keys;  // contain keys to access the rooms
	private Battery battery;
	
	public GameEngine() {
		map = new Map();
		player = new Player();
		collection = new ArrayList<Thing>();
		keys = new HashMap<String,Key>();
		keys.put("#Room1", new Key("#Room1")); // Generate key for first room
		battery = new Battery();
		infoBar = new InfoBar(); // Game information Bar
	}
	
	public void draw(Graphics g) {
		map.draw(g);
		player.draw(g);
		infoBar.draw(g);
	}
	
	public void update() {
		checkCollision();
		map.setX( map.getX() - player.getVx() );  // update new Map x, y
		map.setY( map.getY() - player.getVy() );
	}
	
	
	/*
	 * check collision and make the action
	 */

	public void checkCollision() {
		// check collision
		int[] collision = this.collision();
		
		/*************  Wall collision  **********************/
		if( collision[0] == Map.WALL )  // if is wall => stop
			player.stop();
		else
		/*************  Tree collision  **********************/	
		if( collision[0] == Map.TREE || collision[0] == Map.TREE_HIDE)    
			player.stop();
		/************** Water Collision ***********************/
		if( collision[0] == Map.WATER ) 
			player.stop();
		else
		/************** Room Collision ***********************/
		if( collision[0] == Map.TILE ) {
			Room currentRoom = map.getRoom(collision[1], collision[2]); // check room
			if( currentRoom != null){ 
				String roomName = currentRoom.getName();
				if( this.keys.containsKey(roomName) == false ) { // if don't have key to access
					player.stop();
				}
			}
		}
		else
		/************** Key Collision ***********************/
		if( collision[0] == Map.KEY ) {
			Room currentRoom = map.getRoom(collision[1], collision[2]); // check room
			if( currentRoom != null){
				int roomNum = Integer.parseInt(currentRoom.getName().substring(5));
				String roomName = "#Room" + (roomNum + 1);  // Generate room name for new key
				
				if(this.keys.containsKey(roomName) == false) { // if haven't contained key for next room
					this.keys.put(roomName, new Key(roomName));  // create new obj key then put to keys map
					infoBar.addKey();
				}
				map.setMapTile(collision[1], collision[2], Map.TILE);  // change key to TILE image
			}
		}
		else
		/************** Flashlight Collision ***********************/
		if( collision[0] == Map.FLASHLIGHT ) {
			this.flashlight = new Flashlight("Flashlight");
			this.collection.add(flashlight);  // add new flashlight object to collection
			map.setMapTile(collision[1], collision[2], Map.TILE);  // change flashlight to TILE image
			infoBar.addStuff();
		}
		
		
	}
	
	
	
	/*
	 * Handle the collision between player and other objects 
	 * return array(tileKey, tileY, tileX) in Map tiles array
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
	 * Game listen, get all the event from object and process
	 */
	
	public void listen() {
		if( collection.size() == 1 ) {
			System.out.println(collection.size());
		}
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
	
	public void keySpace() { 
		if( flashlight != null ) {
			if ( flashlight.isOn() ) {
				flashlight.turnOff();
				battery.setConsume(false);
				player.changePlayer(false); // change player image without flashlight
				map.hideTrees();
			} else { 
				flashlight.turnOn();
				battery.setConsume(true); 
				player.changePlayer(true); // change player image with flashlight
				map.showTrees();
			}
		}
	}

}
