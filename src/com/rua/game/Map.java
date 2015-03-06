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
	public static final int MAP_SIZEY  = 30;
	private HashMap<Integer,BufferedImage> tileImages;
	private int x, y;  // Map position
	
	public Map() {
		this.x = - (MAP_SIZEX * TILE_SIZE - GamePanel.WIDTH)/2;
		this.y = - (MAP_SIZEY * TILE_SIZE - GamePanel.HEIGHT)/2;
		createBg();
		loadImage();
	}
	
	// Generate background title
	@SuppressWarnings("resource")
	private void createBg() {
		this.mapTiles = new int[MAP_SIZEY][MAP_SIZEX];
		
		try {
			Scanner mapFile = new Scanner(new File("Assets/Maps/map.txt")); // read map file
			
			for(int row=0; row < MAP_SIZEY; row++) {
				String line = mapFile.nextLine();
				String[] tokens =  line.split(" ");
				for(int col=0; col < MAP_SIZEX; col++ ) {
					this.mapTiles[row][col] = Integer.parseInt(tokens[col]);
				}
				//System.out.println(line);
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
			
			this.tileImages.put(0, background);
			this.tileImages.put(1, wall);
			this.tileImages.put(2, tile);
			
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
	///////////////////////////////////////////////////
	
	public void draw(Graphics g) {
		for(int row=0; row < MAP_SIZEY; row++) {
			for(int col=0; col < MAP_SIZEX; col++ ) {
				BufferedImage tile = this.tileImages.get( mapTiles[row][col] );
				g.drawImage(tile, x + col*TILE_SIZE, y + row*TILE_SIZE , null);
			}
		}
	}
	
	public int[][] getMapTiles() {
		return mapTiles;
	}
	
	
}
