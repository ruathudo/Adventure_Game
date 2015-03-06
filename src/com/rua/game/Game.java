package com.rua.game;

import javax.swing.JFrame;

public class Game {

	public static void main(String[] args) {
		JFrame window = new JFrame("The Adventure");
		window.add(new GamePanel());
		window.pack();
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
}
