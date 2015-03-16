package com.rua.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;

public class InfoBar {
	private int stuffNum;
	private int battery;
	private int key;
	private int time;
	private String message;
	public InfoBar() {
		stuffNum = 0;
		battery = 100;
		key = 1;
		time = 200;
		message = "dcmmm vkll";
	}
	
	public void draw(Graphics g) {
		int fontSize = 14;
	    g.setFont(new Font("Arial", Font.BOLD, fontSize));
	    g.setColor(Color.WHITE);
	    g.drawString("Collected: "+ stuffNum + "/" + GameEngine.COLLECTION, 5, 318);
	    g.drawString("Battery: "+ battery + "%", 220, 318);
	    g.drawString("Time: "+ time + "s", 240, 12);
	    g.drawString("Next Room: "+ key + "/5", 5, 12);
	    g.drawString(message, 30, 250);
	}
	
	public void addKey() {
		if(key < 5)
			key++; 
	}
	public void setBattery(int n) { battery = n;}
	public void addStuff() { stuffNum++; }
	public void setTime(int n) { time += n; }
	public int getTime() { return time; }
	
	public void message(String s) {
		message = s;
		Timer t = new Timer();
		t.schedule(new TimerTask() {
		    @Override
		    public void run() {
		    	message = "";
		    }
		}, 3000);//Set the amount of time between each execution (in milliseconds)
	}
}
