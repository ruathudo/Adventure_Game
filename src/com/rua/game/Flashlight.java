package com.rua.game;

public class Flashlight extends Thing {
	private int battery;
	private boolean onState;  // on = true, off = false

	public Flashlight(String n) {
		super(n);
		battery = 100;
		onState = false;
	}
	
	public void turnOn() { onState = true; }
	public void turnOff() { onState = false; }
	public boolean isOn() { return onState; }
	
	public void charge(int c) {
		this.battery += c;
	}
	
	public int checkBattery() {
		return this.battery;
	}
	
	public void consume() {
		this.battery--;
	}
}
