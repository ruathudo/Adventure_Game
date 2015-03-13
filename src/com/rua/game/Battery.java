package com.rua.game;

import java.util.Timer;
import java.util.TimerTask;


public class Battery {
	private int energy;
	private Timer t = new Timer();
	public Battery() {
		energy = 100;
	}

	public void consume(boolean isConsume) {
		if(isConsume) {
			t.scheduleAtFixedRate(new TimerTask() {
			    @Override
			    public void run() {
			        energy--;
			    }
			}, 0, 2000);//Set the amount of time between each execution (in milliseconds)
		} else {
			t.cancel(); // cancel the consuming 
		}

	}
	
	public int getLevel() {
		return energy;
	}
	
	public void charge(int c) {
		energy += c;
	}
}
