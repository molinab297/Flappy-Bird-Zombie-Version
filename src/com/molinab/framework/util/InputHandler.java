package com.jamescho.framework.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.jamescho.game.main.GameMain;
import com.jamescho.game.state.State;

/**
 * @author James
 *	InputHandler now has two new methods mousePressed and mouseReleased.
 *	
 *	This example project uses a fixed game resolution and scales it to a higher screen 
 *	resolution. As such, if the player clicks the game relying on the screen
 *	resolution, the X and Y values of each click will need to be scaled down
 *	to match the game resolution.
 *	
 *	The mousePressed and mouseReleased methods will receive MouseEvent objects
 *	from the user input, scale that down to match the game resolution, and send the
 *	x and y values to the currentState for handling.
 */
public class InputHandler implements KeyListener, MouseListener {

	private State currentState;

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		currentState.onClick(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Do Nothing
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Do Nothing
	}

	@Override
	public void mousePressed(MouseEvent e) {
		currentState.onMousePress((int) (e.getX() / (float) GameMain.GAME_SCALE), (int) (e.getY() / (float) GameMain.GAME_SCALE));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		currentState.onMouseRelease((int) (e.getX() / (float) GameMain.GAME_SCALE), (int) (e.getY() / (float) GameMain.GAME_SCALE));
	}

	@Override
	public void keyPressed(KeyEvent e) {
		currentState.onKeyPress(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		currentState.onKeyRelease(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Do Nothing
	}

}
