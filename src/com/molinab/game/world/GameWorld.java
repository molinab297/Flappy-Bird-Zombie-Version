package com.jamescho.game.world;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import com.jamescho.framework.util.UIButton;
import com.jamescho.game.main.Resources;
import com.jamescho.game.model.Bird;
import com.jamescho.game.model.ScrollHandler;

public class GameWorld {

	private Bird bird;
	private ScrollHandler scroller;
	private Rectangle ground;
	private int score = 0;
	private float runTime = 0;
	private int midPointY;
	private GameMode currentMode;
	public UIButton playButton;

	/**
	 * These are the various modes that the game passes through.
	 * MENU is used for the main menu.
	 * READY is used in the ready screen.
	 * RUNNING is used during gameplay.
	 * GAMEOVER is shown upon the bird's death.
	 */
	public enum GameMode {
		MENU, READY, RUNNING, GAMEOVER
	}

	public GameWorld(int midPointY) {
		currentMode = GameMode.MENU;
		this.midPointY = midPointY;
		bird = new Bird(33, midPointY - 5, 17, 12);
		// The grass should start 66 pixels below the midPointY
		scroller = new ScrollHandler(this, midPointY + 66);
		ground = new Rectangle(0, midPointY + 66, 137, 11);
		playButton = new UIButton(
				136 / 2 - (Resources.playButtonUp.getWidth() / 2),
				midPointY + 30, 29, 16, Resources.playButtonUp,
				Resources.playButtonDown);
	}

	public void update(float delta) {
		runTime += delta;
		Resources.birdAnim.update(delta);

		switch (currentMode) {
		case READY:
		case MENU:
			updateReady(delta);
			break;

		case RUNNING:
			updateRunning(delta);
			break;
		default:
			break;
		}

	}

	private void updateReady(float delta) {
		bird.updateReady(runTime);
		scroller.updateReady(delta);
	}

	public void updateRunning(float delta) {
		if (delta > .15f) {
			delta = .15f;
		}

		bird.update(delta);
		scroller.update(delta);

		if (scroller.collides(bird) && bird.isAlive()) {
			scroller.stop();
			bird.die();
			Resources.dead.play();
			Resources.fall.play();
		}

		if (bird.getBoundingCircle().intersects(ground)) {
			if (bird.isAlive()) {
				Resources.dead.play();
				bird.die();
			}

			scroller.stop();
			bird.decelerate();
			currentMode = GameMode.GAMEOVER;
		}
	}

	public Bird getBird() {
		return bird;

	}

	public int getMidPointY() {
		return midPointY;
	}

	public ScrollHandler getScroller() {
		return scroller;
	}

	public int getScore() {
		return score;
	}

	public void addScore(int increment) {
		score += increment;
		System.out.println(score);
	}

	public void start() {
		currentMode = GameMode.RUNNING;
	}

	public void ready() {
		currentMode = GameMode.READY;
	}

	public void restart() {
		score = 0;
		bird.onRestart(midPointY - 5);
		scroller.onRestart();
		ready();
	}

	public boolean isReady() {
		return currentMode == GameMode.READY;
	}

	public boolean isGameOver() {
		return currentMode == GameMode.GAMEOVER;
	}

	public boolean isMenu() {
		return currentMode == GameMode.MENU;
	}

	public boolean isRunning() {
		return currentMode == GameMode.RUNNING;
	}

	public void onClick(MouseEvent e) {
		// Do nothing
	}

	// Handles a mouse down event only.
	public void onMousePress(int scaledX, int scaledY) {
		if (isMenu()) {
			playButton.onPress(scaledX, scaledY);
		} else if (isRunning()) {
			bird.onClick();
		} else if (isReady()) {
			start();
			bird.onClick();
		}
	}

	// Handles a mouse up event only.
	public void onMouseRelease(int scaledX, int scaledY) {
		if (isMenu()) {
			if (playButton.isPressed(scaledX, scaledY)) {
				playButton.cancel();
				currentMode = GameMode.READY;
			} else {
				playButton.cancel();
			}
		} else if (isGameOver()) {
			restart();
		}
	}

}
