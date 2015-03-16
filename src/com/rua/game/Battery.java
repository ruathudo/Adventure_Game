package com.rua.game;

import java.io.Serializable;

public class Battery extends Thing implements Serializable{
	private static final long serialVersionUID = 1L;
	private int energy;
	private boolean isConsume;
	
	public Battery() {
		super("Battery");
		energy = 100;
	}

	public int getLevel() { return energy; }
	public void consume(int n) { energy -= n; }
	public void charge(int c) { energy += c; }
	public void setConsume(boolean b) { isConsume = b; }
	public boolean checkConsume() { return isConsume; }
}
