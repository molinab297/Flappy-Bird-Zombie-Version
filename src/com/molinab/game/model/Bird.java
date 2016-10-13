package com.jamescho.game.model;

import java.awt.geom.Ellipse2D;

import com.jamescho.framework.math.Vector2D;
import com.jamescho.game.main.Resources;


public class Bird {

	private Vector2D position;
	private Vector2D velocity;
	private Vector2D acceleration;

	private float rotation;
	private int width;
	private float height;

	private float originalY;

	private boolean isAlive;

	private Ellipse2D.Float boundingCircle;

	public Bird(float x, float y, int width, int height) {
		this.width = width;
		this.height = height;
		this.originalY = y;
		position = new Vector2D(x, y);
		velocity = new Vector2D(0, 0);
		acceleration = new Vector2D(0, 460);
		boundingCircle = new Ellipse2D.Float();
		isAlive = true;
	}

	/**
	 * @param delta
	 * update() is called on every frame during GameWorld.RUNNING mode. See GameWorld to see
	 * various modes of the game.
	 */
	public void update(float delta) {
		// Multiply acceleration by delta, add to velocity
		// This is done for framerate independent movement.
		// Note that we do not change the x and y components of acceleration
		// but instead use its clone, so that the true acceleration vector 
		// remains constant.
		velocity.add(acceleration.getClone().scale(delta));

		// Cap velocity at 200
		if (velocity.y > 200) {
			velocity.y = 200;
		}

		// CEILING CHECK
		if (position.y < -13) {
			position.y = -13;
			velocity.y = 0;
		}

		// Add scaled velocity to position. 
		position.add(velocity.getClone().scale(delta));

		// Set a bounding circle to be used as the hitbox for our bird.
		boundingCircle.setFrame(position.x + 3.5f, position.y - .5f, 13f, 13f);

		// Rotate counterclockwise
		if (velocity.y < 0) {
			rotation -= 600 * delta;
			if (rotation < -20) {
				rotation = -20;
			}
		}

		// Rotate clockwise
		if (isFalling() || !isAlive) {
			rotation += 480 * delta;
			if (rotation > 90) {
				rotation = 90;
			}

		}

	}

	public void updateReady(float runTime) {
		position.y = 2 * (float) Math.sin(7 * runTime) + originalY;
	}

	public boolean isFalling() {
		return velocity.y > 110;
	}

	public boolean shouldntFlap() {
		return velocity.y > 70 || !isAlive;
	}

	public void onClick() {
		if (isAlive) {
			Resources.flap.play();
			velocity.y = -140;
		}
	}

	public void die() {
		isAlive = false;
		velocity.y = 0;
	}

	public void decelerate() {
		acceleration.y = 0;
	}

	public void onRestart(int y) {
		rotation = 0;
		position.y = y;
		velocity.x = 0;
		velocity.y = 0;
		acceleration.x = 0;
		acceleration.y = 460;
		isAlive = true;
	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public float getRotation() {
		return rotation;
	}

	public Ellipse2D.Float getBoundingCircle() {
		return boundingCircle;
	}

	public boolean isAlive() {
		return isAlive;
	}

}
