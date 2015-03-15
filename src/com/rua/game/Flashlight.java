package com.rua.game;

public class Flashlight extends Thing {
	private boolean onState;  // on = true, off = false

	public Flashlight(String n) {
		super(n);
		onState = false;
	}
	
	public void turnOn() { onState = true; }
	public void turnOff() { onState = false; }
	public boolean isOn() { return onState; }

}
