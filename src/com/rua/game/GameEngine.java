package com.rua.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class GameEngine implements Serializable{
	/**
	 * Serialize object for saving
	 */
	private static final long serialVersionUID = 1L;
	public static final int COLLECTION = 25; // number of stuff in collection to win
	public static final int TIME = 200; // maximum time before bomb execute
	
	private Map map;
	private Player player;
	private InfoBar infoBar;
	private Flashlight flashlight;
	private Lifebuoy lifebuoy;
	private ArrayList<Thing> collection;  // contain Thing objects
	private HashMap<String, Key> keys;  // contain keys to access the rooms
	private Battery battery;
	private int timePlay;
	
	public GameEngine() {
		map = new Map();
		player = new Player();
		collection = new ArrayList<Thing>();
		keys = new HashMap<String,Key>();
		keys.put("#Room1", new Key("#Room1")); // Generate key for first room
		timePlay = TIME;
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
	 * load unsaved object when saving
	 * Typically is buffered image
	 */
	public void load() {
		map.load();
		player.load();
	}
	
	/*
	 * check collision and make the action
	 */

	public void checkCollision() {
		// check collision
		int[] collision = this.collision();
		
		/*************  Ground collision  **********************/	
		if( collision[0] == Map.GROUND) {
			if(lifebuoy != null && lifebuoy.isUsing() && !lifebuoy.isUsed())
				lifebuoy.setUsed();  // set lifeboy is used if it from water to ground
		}		
		else
		/*************  Wall collision  **********************/
		if( collision[0] == Map.WALL )  // if is wall => stop
			player.stop();
		else
		/*************  Tree collision  **********************/	
		if( collision[0] == Map.TREE || collision[0] == Map.TREE_HIDE)    
			player.stop();
		else
		/************** Water Collision ***********************/
		if( collision[0] == Map.WATER ) { 
			if(lifebuoy != null && !lifebuoy.isUsed() ) 
				lifebuoy.setUsing(); // set lifeboy is using if it from ground to water and new
			else 
				player.stop();
		}
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
				
				this.keys.put(roomName, new Key(roomName));  // create new obj key then put to keys map
				infoBar.addKey();
				infoBar.addStuff();
				map.setMapTile(collision[1], collision[2], Map.TILE);  // change key to TILE image
			}
		}
		else
		/************** Flashlight Collision ***********************/
		if( collision[0] == Map.FLASHLIGHT ) {
			this.flashlight = new Flashlight();
			this.collection.add(flashlight);  // add new flashlight object to collection
			map.setMapTile(collision[1], collision[2], Map.TILE);  // change flashlight to TILE image
			infoBar.addStuff();
			infoBar.message("Press SPACE to use Flashlight!"); 
		}
		else
		/************** Battery Collision ***********************/
		if( collision[0] == Map.BATTERY ) {
			this.battery.charge(10);
			this.collection.add(new Thing("Battery"));
			infoBar.addStuff();
			map.setMapTile(collision[1], collision[2], Map.TILE);  // change flashlight to Ground image
		}
		else
		/************** Clock Collision ***********************/
		if( collision[0] == Map.CLOCK ) {
			this.collection.add(new Thing("Clock"));
			infoBar.addStuff();
			infoBar.setTime(30); // plus 30s for time
			infoBar.message("Plus time before explosion!"); // minus -10s for time
			map.setMapTile(collision[1], collision[2], Map.TILE);  // change flashlight to Ground image
		}
		else
		/************** Lifebuoy Collision ***********************/
		if( collision[0] == Map.LIFEBUOY ) {
			this.lifebuoy = new Lifebuoy();
			this.collection.add(lifebuoy);
			infoBar.addStuff();
			infoBar.message("Use lifebuoy to swim, onetime only!"); 
			map.setMapTile(collision[1], collision[2], Map.TILE);  // change flashlight to Ground image
		}	
		else
		/************** Bomb Collision ***********************/
		if( collision[0] == Map.BOMB ) {
			this.collection.add(new Thing("Bomb"));
			infoBar.addStuff();
			infoBar.message("Cheer! You have removed a bomb!"); 
			map.setMapTile(collision[1], collision[2], Map.TILE);  // change flashlight to Ground image
		}	
		/************** Others Collision ***********************/
		else 
		if( collision[0] != 0 ){
			this.collection.add(new Thing("Stuff"));
			infoBar.addStuff();
			map.setMapTile(collision[1], collision[2], Map.GROUND);  // change flashlight to Ground image
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
		else
		if( player.isLeft() ) { // if is going to left
			tileX = ( GamePanel.WIDTH/2 - map.getX() - playerTile.getWidth()/2 )/Map.TILE_SIZE;
			tileY = ( GamePanel.HEIGHT/2 - map.getY() )/Map.TILE_SIZE;
		}
		else
		if( player.isUp() ) { // if is going to right
			tileX = ( GamePanel.WIDTH/2 - map.getX()  )/Map.TILE_SIZE;
			tileY = ( GamePanel.HEIGHT/2 - map.getY() - playerTile.getHeight()/2 )/Map.TILE_SIZE;
		}
		else
		if( player.isDown() ) { // if is going to right
			tileX = ( GamePanel.WIDTH/2 - map.getX() )/Map.TILE_SIZE;
			tileY = ( GamePanel.HEIGHT/2 - map.getY() + playerTile.getHeight()/2 )/Map.TILE_SIZE;
		}
		else {
			int tileKey = 0;
			int[] tilePos = {tileKey, tileY, tileX};
			return tilePos;
		}
		
		int tileKey = mapTiles[tileY][tileX];
		int[] tilePos = {tileKey, tileY, tileX};
		return tilePos;
	}	
	
	
	/*
	 * Game listen, get all the event from object and process
	 */
	
	public void listen() {
		if( collection.size() == COLLECTION ) // if collect enough stuffs
			System.out.println("Win!!");
		
		if( battery.checkConsume() ) {  // listen for battery if is consuming
			infoBar.setBattery( battery.getLevel() );
			battery.consume(1);
		}
		
		if( this.timePlay > 0){
			this.timePlay--;   // minus timePlay every second
			infoBar.setTime(-1);
		}
		else
			System.out.println("Loose!!");
	}
	
	public void waterUp() {
		int[][] mapTiles = map.getMapTiles();
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
