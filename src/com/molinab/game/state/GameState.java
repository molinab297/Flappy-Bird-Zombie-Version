package com.jamescho.game.state;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.jamescho.game.main.GameMain;
import com.jamescho.game.world.GameRenderer;
import com.jamescho.game.world.GameWorld;

/**
 * @author James
 * GameState is now a multi-purpose state that serves as the menu, game, and gameover state.
 * Rather than doing all of the object updating and rendering, it delegates two helper classes
 * GameWorld and GameRenderer to do the job.
 */
public class GameState extends State {

	private GameWorld world;
	private GameRenderer renderer;

	// This is the constructor, not the class declaration
	public GameState() {
		float screenWidth = GameMain.GAME_WIDTH;
		float screenHeight = GameMain.GAME_HEIGHT;
		float gameWidth = 136;
		float gameHeight = screenHeight / (screenWidth / gameWidth);
		int midPointY = (int) (gameHeight / 2);
		world = new GameWorld(midPointY);
		renderer = new GameRenderer(world, (int) gameHeight, midPointY);
	}

	@Override
	public void init() {
	}

	@Override
	public void update(float delta) {
		// Delegate GameWorld world to update.
		world.update(delta);
	}

	@Override
	public void render(Graphics g) {
		// Delegate GameRenderer renderer to update.
		renderer.render(g);
	}

	@Override
	public void onClick(MouseEvent e) {
		world.onClick(e);
	}

	@Override
	public void onKeyPress(KeyEvent e) {
	}

	@Override
	public void onKeyRelease(KeyEvent e) {
	}

	@Override
	public void onMousePress(int scaledX, int scaledY) {
		world.onMousePress(scaledX, scaledY);
	}

	@Override
	public void onMouseRelease(int scaledX, int scaledY) {
		world.onMouseRelease(scaledX, scaledY);		
	}

}
