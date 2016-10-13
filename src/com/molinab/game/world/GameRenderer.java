package com.jamescho.game.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import com.jamescho.game.main.Resources;
import com.jamescho.game.model.Bird;
import com.jamescho.game.model.Grass;
import com.jamescho.game.model.Pipe;
import com.jamescho.game.model.ScrollHandler;

public class GameRenderer {

	private GameWorld myWorld;
	private int midPointY;

	// Game Objects
	private Bird bird;
	private ScrollHandler scroller;
	private Grass frontGrass, backGrass;
	private Pipe pipe1, pipe2, pipe3;
	private AffineTransform transform;

	public GameRenderer(GameWorld world, int gameHeight, int midPointY) {
		myWorld = world;
		this.midPointY = midPointY;
		initGameObjects();
	}

	// Store all of the game objects from myWorld and scroller
	// as local variables for quicker use.
	private void initGameObjects() {
		bird = myWorld.getBird();
		scroller = myWorld.getScroller();
		frontGrass = scroller.getFrontGrass();
		backGrass = scroller.getBackGrass();
		pipe1 = scroller.getPipe1();
		pipe2 = scroller.getPipe2();
		pipe3 = scroller.getPipe3();
	}

	public void render(Graphics g) {
		g.setColor(Color.black);
		g.clearRect(0, 0, 136, 300);

		// Draw Upper Sky
		Color color = new Color(55, 80, 100);
		g.setColor(color);
		g.fillRect(0, 0, 136, midPointY + 66);

		// // Draw Dirt
		color = new Color(147, 80, 27);
		g.setColor(color);
		g.fillRect(0, midPointY + 77, 136, 52);

		g.drawImage(Resources.bg, 0, midPointY + 23, 136, 43, null);
		drawPipes(g);

		drawSkulls(g);

		if (myWorld.isRunning()) {
			drawBird(g);
		} else if (myWorld.isReady()) {
			drawBird(g);
			drawReady(g);
		} else if (myWorld.isMenu()) {
			drawBirdCentered(g);
			drawMenuUI(g);
		} else if (myWorld.isGameOver()) {
			drawScoreboard(g);
			drawBird(g);
			drawGameOver(g);
			drawRetry(g);
		}

		drawGrass(g);
	}

	private void drawGrass(Graphics g) {
		// Draw the grass
		g.drawImage(Resources.grass, (int) frontGrass.getX(),
				(int) frontGrass.getY(), frontGrass.getWidth(),
				frontGrass.getHeight(), null);
		g.drawImage(Resources.grass, (int) backGrass.getX(),
				(int) backGrass.getY(), backGrass.getWidth(),
				backGrass.getHeight(), null);
	}

	private void drawSkulls(Graphics g) {
		g.drawImage(Resources.skullUp, (int) pipe1.getX() - 1,
				(int) pipe1.getY() + pipe1.getHeight() - 14, 24, 14, null);
		g.drawImage(Resources.skullDown, (int) pipe1.getX() - 1,
				(int) pipe1.getY() + pipe1.getHeight() + 45, 24, 14, null);

		g.drawImage(Resources.skullUp, (int) pipe2.getX() - 1,
				(int) pipe2.getY() + pipe2.getHeight() - 14, 24, 14, null);
		g.drawImage(Resources.skullDown, (int) pipe2.getX() - 1,
				(int) pipe2.getY() + pipe2.getHeight() + 45, 24, 14, null);

		g.drawImage(Resources.skullUp, (int) pipe3.getX() - 1,
				(int) pipe3.getY() + pipe3.getHeight() - 14, 24, 14, null);
		g.drawImage(Resources.skullDown, (int) pipe3.getX() - 1,
				(int) pipe3.getY() + pipe3.getHeight() + 45, 24, 14, null);
	}

	private void drawPipes(Graphics g) {
		g.drawImage(Resources.bar, (int) pipe1.getX(), (int) pipe1.getY(),
				pipe1.getWidth(), pipe1.getHeight(), null);
		g.drawImage(Resources.bar, (int) pipe1.getX(), (int) pipe1.getY()
				+ pipe1.getHeight() + 45, pipe1.getWidth(), midPointY + 66
				- (pipe1.getHeight() + 45), null);

		g.drawImage(Resources.bar, (int) pipe2.getX(), (int) pipe2.getY(),
				pipe2.getWidth(), pipe2.getHeight(), null);
		g.drawImage(Resources.bar, (int) pipe2.getX(), (int) pipe2.getY()
				+ pipe2.getHeight() + 45, pipe2.getWidth(), midPointY + 66
				- (pipe2.getHeight() + 45), null);

		g.drawImage(Resources.bar, (int) pipe3.getX(), (int) pipe3.getY(),
				pipe3.getWidth(), pipe3.getHeight(), null);
		g.drawImage(Resources.bar, (int) pipe3.getX(), (int) pipe3.getY()
				+ pipe3.getHeight() + 45, pipe3.getWidth(), midPointY + 66
				- (pipe3.getHeight() + 45), null);
	}

	private void drawBirdCentered(Graphics g) {
		drawRotatedImage(g, Resources.birdAnim.getCurrentImage(), 59,
				bird.getY() - 15, bird.getWidth(), bird.getHeight(),
				bird.getRotation());
	}

	private void drawBird(Graphics g) {
		if (bird.shouldntFlap()) {
			drawRotatedImage(g, Resources.birdImage, bird.getX(), bird.getY(),
					bird.getWidth(), bird.getHeight(), bird.getRotation());
		} else {
			drawRotatedImage(g, Resources.birdAnim.getCurrentImage(),
					bird.getX(), bird.getY(), bird.getWidth(),
					bird.getHeight(), bird.getRotation());
		}
	}

	private void drawRotatedImage(Graphics g, Image image, float x, float y,
			float width, float height, float rotation) {
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform original = g2d.getTransform();
		if (transform == null) {
			transform = new AffineTransform();
		} else {
			transform.setToRotation(0);
		}
		transform.rotate(Math.toRadians(rotation), x + width / 2, y + height
				/ 2);
		g2d.transform(transform);
		g2d.drawImage(image, (int) x, (int) y, (int) width, (int) height, null);
		g2d.setTransform(original);
	}

	private void drawMenuUI(Graphics g) {
		g.drawImage(Resources.zbLogo, 136 / 2 - 56, (int) midPointY - 50,
				(int) (Resources.zbLogo.getWidth() / 1.2f),
				(int) (Resources.zbLogo.getHeight() / 1.2f), null);

		myWorld.playButton.render(g);
	}

	private void drawScoreboard(Graphics g) {
		g.drawImage(Resources.scoreboard, 22, midPointY - 30, 97, 37, null);

		g.drawImage(Resources.noStar, 25, midPointY - 15, 10, 10, null);
		g.drawImage(Resources.noStar, 37, midPointY - 15, 10, 10, null);
		g.drawImage(Resources.noStar, 49, midPointY - 15, 10, 10, null);
		g.drawImage(Resources.noStar, 61, midPointY - 15, 10, 10, null);
		g.drawImage(Resources.noStar, 73, midPointY - 15, 10, 10, null);

		if (myWorld.getScore() > 2) {

			g.drawImage(Resources.star, 73, midPointY - 15, 10, 10, null);
		}

		if (myWorld.getScore() > 17) {
			g.drawImage(Resources.star, 61, midPointY - 15, 10, 10, null);
		}

		if (myWorld.getScore() > 50) {
			g.drawImage(Resources.star, 49, midPointY - 15, 10, 10, null);
		}

		if (myWorld.getScore() > 80) {
			g.drawImage(Resources.star, 37, midPointY - 15, 10, 10, null);
		}

		if (myWorld.getScore() > 120) {
			g.drawImage(Resources.star, 25, midPointY - 15, 10, 10, null);
		}
	}

	private void drawRetry(Graphics g) {
		g.drawImage(Resources.retry, 30, midPointY + 10, 66, 14, null);
	}

	private void drawReady(Graphics g) {
		g.drawImage(Resources.ready, 30, midPointY - 50, 78, 14, null);
	}

	private void drawGameOver(Graphics g) {
		g.drawImage(Resources.gameOver, 24, midPointY - 50, 92, 14, null);
	}

}
