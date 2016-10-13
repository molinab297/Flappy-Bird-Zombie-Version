package com.jamescho.game.main;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.jamescho.framework.animation.Animation;
import com.jamescho.framework.animation.Frame;

public class Resources {

	public static BufferedImage iconimage, bg, bar, zbLogo,
			grass, birdImage, skullUp, skullDown, playButtonUp, playButtonDown,
			ready, gameOver, highScore, scoreboard, star, noStar, retry;
	public static AudioClip dead, fall, flap, coin;
	public static Animation birdAnim;

	/**
	 * Notice that the game only uses 1 image (texture.png) for all of the images.
	 * We can easily split this image into many images by using
	 * the getSubimage() method and providing the x, y, width and height
	 * of a smaller image.
	 */
	public static void load() {
		iconimage = loadImage("iconimage.png");
		BufferedImage texture = loadImage("texture.png");
		bg = texture.getSubimage(0, 0, 136, 43);
		bar = texture.getSubimage(136, 16, 22, 3);
		skullDown = texture.getSubimage(192, 0, 24, 14);
		skullUp = getVerticallyFlippedImage(skullDown);
		ready = texture.getSubimage(59, 83, 34, 7);
		grass = texture.getSubimage(0, 43, 143, 11);
		birdImage = texture.getSubimage(153, 0, 17, 12);
		zbLogo = texture.getSubimage(0, 55, 135, 24);
		playButtonUp = texture.getSubimage(0, 83, 29, 16);
		playButtonDown = texture.getSubimage(29, 83, 29, 16);
		scoreboard = texture.getSubimage(111, 83, 97, 37);
		star = texture.getSubimage(152, 70, 10, 10);
		noStar = texture.getSubimage(165, 70, 10, 10);
		retry = texture.getSubimage(59, 110, 33, 7);
		gameOver = texture.getSubimage(59, 92, 46, 7);
		highScore = texture.getSubimage(0, 55, 135, 24);
		
		Frame birdDown = new Frame(texture.getSubimage(136, 0, 17, 12), 0.06f);
		Frame bird = new Frame(birdImage, 0.06f);
		Frame birdUp = new Frame(texture.getSubimage(170, 0, 17, 12), 0.06f);
		birdAnim = new Animation(birdDown, bird, birdUp, bird);

		dead = loadSound("dead.wav");
		fall = loadSound("fall.wav");
		flap = loadSound("flap.wav");
		coin = loadSound("coin.wav");
	}

	private static BufferedImage getVerticallyFlippedImage(BufferedImage source) {
		BufferedImage flipped = new BufferedImage(source.getWidth(),
				source.getHeight(), source.getType());
		Graphics g = flipped.getGraphics();
		g.drawImage(source, 0, 0 + source.getHeight(), source.getWidth(),
				-source.getHeight(), null);
		g.dispose();
		return flipped;
	}

	private static AudioClip loadSound(String filename) {
		URL fileURL = Resources.class.getResource("/resources/" + filename);
		return Applet.newAudioClip(fileURL);
	}

	private static BufferedImage loadImage(String filename) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(Resources.class
					.getResourceAsStream("/resources/" + filename));
		} catch (IOException e) {
			System.out.println("Error while reading: " + filename);
			e.printStackTrace();
		}
		return img;
	}
}