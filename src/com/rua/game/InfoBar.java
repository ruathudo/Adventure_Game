package com.rua.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class InfoBar {
	private int stuffNum;
	private int battery;
	private int key;
	private int time;
	
	public InfoBar() {
		stuffNum = 0;
		battery = 100;
		key = 0;
		time = 0;
	}
	
	public void draw(Graphics g) {
		int fontSize = 14;
	    g.setFont(new Font("Serif", Font.BOLD, fontSize));
	    g.setColor(Color.WHITE);
	    g.drawString("Collected: "+ stuffNum + "/20", 5, 318);
	    g.drawString("Battery: "+ battery + "%", 220, 318);
	    g.drawString("Time: "+ time + "s", 240, 12);
	    g.drawString("Keys: "+ key + "/5", 5, 12);
	}
	
	public void addKey() { key++; }
	public void setBattery(int n) { battery = n;}
	public void addStuff() { stuffNum++; }
	public void setTime(int n) { time += n; }
}
