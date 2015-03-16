package com.rua.game;

import java.io.Serializable;

public class Room implements Serializable{

	private static final long serialVersionUID = 1L;
	private String name;
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	
	
	public Room(String s, int x1, int y1, int x2, int y2) {
		this.name = s;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	/////////////////////////////////////////////////
	
	public int getX1(){ return this.x1; }
	public int getY1(){ return this.y1; }
	public int getX2(){ return this.x2; }
	public int getY2(){ return this.y2; }
	public String getName(){ return this.name; }
	
	/////////////////////////////////////////////////
	
}
