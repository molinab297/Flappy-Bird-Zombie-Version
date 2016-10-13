package com.jamescho.game.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.jamescho.framework.util.InputHandler;
import com.jamescho.game.state.LoadState;
import com.jamescho.game.state.State;

/**
 * @author James
 * The Game class has minor changes. The size of the window (screen resolution)
 * will be larger than the game resolution, as it is equal to game resolution
 * scaled by GameMain.GAME_SCALE.
 * 
 * Because of double buffering, we have an easy solution. We simply render the game image,
 * onto which we render everything using the game resolution, as a larger size in
 * the renderGameImage() method, stretching it to fit the window.
 */
@SuppressWarnings("serial")
public class Game extends JPanel implements Runnable {
	private int gameWidth;
	private int gameHeight;
	private BufferedImage gameImage;

	private Thread gameThread;
	private volatile boolean running;
	private volatile State currentState;

	private InputHandler inputHandler;

	public Game(int gameWidth, int gameHeight) {
		this.gameWidth = gameWidth;
		this.gameHeight = gameHeight;
		setPreferredSize(new Dimension(gameWidth, gameHeight));
		setBackground(Color.BLACK);
		setFocusable(true);
		requestFocus();
	}

	public void setCurrentState(State newState) {
		System.gc();
		newState.init();
		currentState = newState;
		inputHandler.setCurrentState(currentState);
	}

	@Override
	public void addNotify() {
		super.addNotify();
		initInput();
		setCurrentState(new LoadState());
		initGame();
	}

	private void initGame() {
		running = true;
		gameThread = new Thread(this, "Game Thread");
		gameThread.start();
	}

	@Override
	public void run() {
		long updateDurationMillis = 0; 
		long sleepDurationMillis = 0; 
		while (running) {
			long beforeUpdateRender = System.nanoTime();
			long deltaMillis = updateDurationMillis + sleepDurationMillis;

			updateAndRender(deltaMillis);

			updateDurationMillis = (System.nanoTime() - beforeUpdateRender) / 1000000L;
			sleepDurationMillis = Math.max(2, 17 - updateDurationMillis);

			try {
				Thread.sleep(sleepDurationMillis);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.exit(0);
	}

	private void updateAndRender(long deltaMillis) {
		currentState.update(deltaMillis / 1000f);
		prepareGameImage();
		currentState.render(gameImage.getGraphics());
		renderGameImage(getGraphics());
	}

	private void prepareGameImage() {
		if (gameImage == null) {
			gameImage = (BufferedImage) createImage(gameWidth, gameHeight);
		}
		Graphics g = gameImage.getGraphics();
		g.clearRect(0, 0, gameWidth, gameHeight);
	}

	public void exit() {
		running = false;
	}

	private void renderGameImage(Graphics g) {
		if (gameImage != null) {
			// Game image is smaller than the size of the window. We must stretch it to fit the window!
			g.drawImage(gameImage, 0, 0, GameMain.GAME_WIDTH * GameMain.GAME_SCALE, GameMain.GAME_HEIGHT  * GameMain.GAME_SCALE, null);
		}
		g.dispose();
	}

	private void initInput() {
		inputHandler = new InputHandler();
		addKeyListener(inputHandler);
		addMouseListener(inputHandler);
	}
}