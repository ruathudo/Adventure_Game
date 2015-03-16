package com.rua.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


public class Menu {
	
	public void draw(Graphics g) {
		g.setFont(new Font("Arial", Font.BOLD, 20));
	    g.setColor(Color.WHITE);
	    g.drawString("ADVENTURE GAME", 70, 80);
		g.drawString("Press:", 100, 150);
		g.drawString(" 1 To Play", 100, 180);
		g.drawString(" 2 To SAVE", 100, 210);
		g.drawString(" 3 To LOAD", 100, 240);
		g.drawString(" 4 To HELP", 100, 270);
		g.drawString(" ESC To BACK", 100, 300);
	}
}
