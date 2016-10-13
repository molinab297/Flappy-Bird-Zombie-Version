package com.jamescho.game.model;

import com.jamescho.framework.math.Vector2D;

// This class should only be used as a superclass.
public abstract class Scrollable {
	
	// Protected is similar to private, but allows inheritance by subclasses.
	protected Vector2D position;
	protected Vector2D velocity;
	protected int width;
	protected int height;
	protected boolean isScrolledLeft;

	public Scrollable(float x, float y, int width, int height, float scrollSpeed) {
		position = new Vector2D(x, y);
		velocity = new Vector2D(scrollSpeed, 0);
		this.width = width;
		this.height = height;
		isScrolledLeft = false;
	}

	public void update(float delta) {
		position.add(velocity.getClone().scale(delta));

		// If the Scrollable object is no longer visible:
		if (position.x + width < 0) {
			isScrolledLeft = true;
		}
	}

	// Reset: Should Override in subclass for more specific behavior.
	public void reset(float newX) {
		position.x = newX;
		isScrolledLeft = false;
	}

	public void stop() {
		velocity.x = 0;
	}
	
	// Getters for instance variables
	public boolean isScrolledLeft() {
		return isScrolledLeft;
	}

	public float getTailX() {
		return position.x + width;
	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
