package com.rua.game;

import java.io.Serializable;

public class Flashlight extends Thing implements Serializable{
	private static final long serialVersionUID = 1L;
	private boolean onState;  // on = true, off = false

	public Flashlight() {
		super("Flashlight");
		onState = false;
	}
	
	public void turnOn() { onState = true; }
	public void turnOff() { onState = false; }
	public boolean isOn() { return onState; }

}
