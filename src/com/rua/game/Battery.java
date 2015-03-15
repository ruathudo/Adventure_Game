package com.rua.game;

import java.util.Timer;
import java.util.TimerTask;


public class Battery extends Thing{
	private int energy;
	private boolean isConsume;
	
	public Battery() {
		super("Battery");
		energy = 100;
		consume();
	}

	public void consume() {
		Timer t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {
		    @Override
		    public void run() {
		    	if( isConsume ) 
		    		energy -= 2;
		    }
		}, 10, 3000);//Set the amount of time between each execution (in milliseconds)
	}
	
	public int getLevel() { return energy; }
	
	public void charge(int c) { energy += c; }

	public boolean checkConsume() { return isConsume; }
	
	public void setConsume(boolean b) { isConsume = b; }
}
