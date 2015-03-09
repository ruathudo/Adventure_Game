package com.rua.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Map {
	public static final int TILE_SIZE = 32;
	public static final int MAP_SIZEX  = 40; // tile numbers
	public static final int MAP_SIZEY  = 40;
	
	public static final int GROUND = 10;  // stuff key
	public static final int WALL = 11;
	public static final int TILE = 12;
	public static final int WATER = 13;
	public static final int KEY = 20;
	public static final int FLASHLIGHT = 21;
	public static final int BATTERY = 22;
	
	
	private int[][] mapTiles;
	private HashMap<Integer,BufferedImage> tileImages;
	private ArrayList<Room> rooms;
	private int x, y;  // Map position
	
	public Map() {
		this.x = - (MAP_SIZEX * TILE_SIZE - GamePanel.WIDTH)/2;
		this.y = - (MAP_SIZEY * TILE_SIZE - GamePanel.HEIGHT)/2;
		this.rooms = new ArrayList<Room>(); // create rooms map
		createMap();
		loadImage();
	}
	
	// Generate background title
	@SuppressWarnings("resource")
	private void createMap() {
		this.mapTiles = new int[MAP_SIZEY][MAP_SIZEX];
		String line;
		String[] tokens;
		try {
			Scanner mapFile = new Scanner(new File("Assets/Maps/map.txt")); // read map file
			
			for(int row=0; row < MAP_SIZEY; row++) {
				line = mapFile.nextLine();
				tokens =  line.split(" ");
				for(int col=0; col < MAP_SIZEX; col++ ) {
					this.mapTiles[row][col] = Integer.parseInt(tokens[col]);
				}
				
			}
			
			// Create 5 rooms object
			for(int row=MAP_SIZEY+1; row <= MAP_SIZEY+5; row++) {
				if( mapFile.hasNextLine() ) {
					line = mapFile.nextLine();
					tokens =  line.split(" ");
					
					String roomName = tokens[0];
					int roomX1 = Integer.parseInt(tokens[1]);
					int roomY1 = Integer.parseInt(tokens[2]);
					int roomX2 = Integer.parseInt(tokens[3]);
					int roomY2 = Integer.parseInt(tokens[4]);

					this.rooms.add( new Room(roomName,roomX1,roomY1,roomX2,roomY2) );
					System.out.println(tokens[0]);
				}
				
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}
	
	// Load image asset
	private void loadImage(){
		this.tileImages = new HashMap<Integer,BufferedImage>();
		
		try {
			BufferedImage ground = ImageIO.read(new File("Assets/Sprites/background.jpg"));
			BufferedImage wall = ImageIO.read(new File("Assets/Sprites/wall.png"));
			BufferedImage tile = ImageIO.read(new File("Assets/Sprites/tile.jpg"));
			BufferedImage water = ImageIO.read(new File("Assets/Sprites/water.gif"));
			BufferedImage key = ImageIO.read(new File("Assets/Sprites/key.jpg"));
			BufferedImage flashlight = ImageIO.read(new File("Assets/Sprites/flashlight.jpg"));
			
			//map start from 10
			this.tileImages.put(GROUND, ground);
			this.tileImages.put(WALL, wall);
			this.tileImages.put(TILE, tile);
			this.tileImages.put(WATER, water);
			
			//stuff start from 10
			this.tileImages.put(KEY, key);
			this.tileImages.put(FLASHLIGHT, flashlight);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	////////////////////////////////////////////////////
	public int getX(){ return this.x;}
	public int getY(){ return this.y;}
	public void setX(int n) { this.x = n; }
	public void setY(int n) { this.y = n; }
	
	public int getWidth(){ return MAP_SIZEX*TILE_SIZE; }
	public int getHeight(){ return MAP_SIZEY*TILE_SIZE; }
	 
	public int[][] getMapTiles() { return mapTiles; }
	
	public void setMapTile(int row, int col, int val) { 
		mapTiles[row][col] = val;
	}
	
	///////////////////////////////////////////////////
	
	public void draw(Graphics g) {
		for(int row=0; row < MAP_SIZEY; row++) {
			for(int col=0; col < MAP_SIZEX; col++ ) {
				BufferedImage tile = this.tileImages.get( mapTiles[row][col] );
				g.drawImage(tile, x + col*TILE_SIZE, y + row*TILE_SIZE , null);
			}
		}
	}
	
	/*
	 * Get Room Return Room object
	 */
	public Room getRoom(int row, int col) {
		for(Room r : this.rooms  ) {  
			if( col >= r.getX1() && col <= r.getX2() && row >= r.getY1() && row <= r.getY2() ) 
				return r;
		}
		return null;
	}
	
}
