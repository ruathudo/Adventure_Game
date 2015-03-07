package com.rua.game;

public class Room {
	private String name;
	private int x;
	private int y;
	private int width;
	private int height;
	
	
	public Room(String s, int x, int y, int w, int h) {
		this.name = s;
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
	}
	
	/////////////////////////////////////////////////
	
	public int getX(){ return this.x; }
	public int getY(){ return this.y; }
	public int getWidth(){ return this.width; }
	public int getHeight(){ return this.height; }
	public String getName(){ return this.name; }
	
	/////////////////////////////////////////////////
	
}
