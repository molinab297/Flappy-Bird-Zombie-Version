package com.jamescho.game.state;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.jamescho.game.main.Resources;

public class LoadState extends State {

	@Override
	public void init() {
		Resources.load();
	}

	@Override
	public void update(float delta) {
		// Goes directly to GameState
		setCurrentState(new GameState());
	}

	@Override
	public void render(Graphics g) {
	}

	@Override
	public void onClick(MouseEvent e) {
	}

	@Override
	public void onKeyPress(KeyEvent e) {
	}

	@Override
	public void onKeyRelease(KeyEvent e) {
	}

	@Override
	public void onMousePress(int scaledX, int scaledY) {
		
	}

	@Override
	public void onMouseRelease(int scaledX, int scaledY) {
		
	}

}
