package com.jamescho.game.state;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.jamescho.game.main.GameMain;

public abstract class State {

	public abstract void init();

	public abstract void update(float delta);

	public abstract void render(Graphics g);

	public abstract void onClick(MouseEvent e);

	public abstract void onKeyPress(KeyEvent e);

	public abstract void onKeyRelease(KeyEvent e);

	public void setCurrentState(State newState) {
		GameMain.sGame.setCurrentState(newState);
	}

	/*
	 * The onMousePress() and onMouseRelease() methods are new in this example.
	 * They are called by methods of the same name in InputHandler.
	 */
	public abstract void onMousePress(int scaledX, int scaledY);
	
	public abstract void onMouseRelease(int scaledX, int scaledY);

}
