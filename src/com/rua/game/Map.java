package com.rua.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Map {
	private int[][] mapTiles;
	public static final int TILE_SIZE = 32;
	public static final int MAP_SIZEX  = 40;
	public static final int MAP_SIZEY  = 40;
	private HashMap<Integer,BufferedImage> tileImages;
	private HashMap<String, Room> rooms;
	private int x, y;  // Map position
	
	public Map() {
		this.x = - (MAP_SIZEX * TILE_SIZE - GamePanel.WIDTH)/2;
		this.y = - (MAP_SIZEY * TILE_SIZE - GamePanel.HEIGHT)/2;
		this.rooms = new HashMap<String, Room>(); // create rooms map
		createBg();
		loadImage();
	}
	
	// Generate background title
	@SuppressWarnings("resource")
	private void createBg() {
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
					int roomX = Integer.parseInt(tokens[1]) * TILE_SIZE;
					int roomY = Integer.parseInt(tokens[1]) * TILE_SIZE;
					int roomW = Integer.parseInt(tokens[3]) * TILE_SIZE;
					int roomH = Integer.parseInt(tokens[4]) * TILE_SIZE;

					this.rooms.put(roomName, new Room(roomName,roomX,roomY,roomW,roomH) );
					
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
			BufferedImage background = ImageIO.read(new File("Assets/Sprites/background.jpg"));
			BufferedImage wall = ImageIO.read(new File("Assets/Sprites/wall.png"));
			BufferedImage tile = ImageIO.read(new File("Assets/Sprites/tile.jpg"));
			BufferedImage water = ImageIO.read(new File("Assets/Sprites/water.gif"));
			BufferedImage flashlight = ImageIO.read(new File("Assets/Sprites/flashlight.jpg"));
			
			//map start from 0
			this.tileImages.put(0, background);
			this.tileImages.put(1, wall);
			this.tileImages.put(2, tile);
			this.tileImages.put(3, water);
			
			//stuff start from 10
			this.tileImages.put(10, flashlight);
			
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
	 
	public int[][] getMapTiles() {
		return mapTiles;
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
		
	
}
