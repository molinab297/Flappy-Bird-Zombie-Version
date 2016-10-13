package com.jamescho.game.main;

import javax.swing.JFrame;

/**
 * @author James
 * Now creates a JFrame whose resolution is not identical to the game width and height!
 */
public class GameMain {
	
	public static final String GAME_TITLE = "Zombie Bird";
	public static final int GAME_WIDTH = 136;
	public static final int GAME_HEIGHT = 200;
	public static Game sGame;
	// Value by which width and height will be scaled.
	public static final int GAME_SCALE = 3;
	
	public static void main(String[] args) {
		JFrame frame = new JFrame(GAME_TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false); 
		sGame = new Game(GAME_WIDTH, GAME_HEIGHT);
		// Scale Screen size to game size * game scale
		// This means that the screen resolution will NOT equal game resolution.
		frame.setSize(GAME_WIDTH * GAME_SCALE, GAME_HEIGHT  * GAME_SCALE);
		frame.add(sGame);
		frame.setVisible(true);
		frame.setIconImage(Resources.iconimage);
	}

}