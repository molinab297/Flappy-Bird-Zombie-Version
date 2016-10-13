package com.jamescho.framework.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

/**
 *  @author James
 *  This class performs identical behavior as the UIButton from the Android Game Development
 * 	Framework in the book. Note that Android-specific classes such as Rect and Bitmap 
 * 	are replaced by Java-specific classes such as Rectangle and Image.
 */

public class UIButton {
	private Rectangle buttonRect;
	private boolean buttonDown = false;
	private Image buttonImage, buttonDownImage;

	public UIButton(int x, int y, int width, int height, Image buttonImage, Image buttonPressedImage) {
		buttonRect = new Rectangle(x, y, width, height);
		this.buttonImage = buttonImage;
		this.buttonDownImage = buttonPressedImage;
	}
	
	public void render(Graphics g) {
		Image currentButtonImage = buttonDown ? buttonDownImage : buttonImage;
		g.drawImage(currentButtonImage, buttonRect.x, buttonRect.y,
				buttonRect.width, buttonRect.height, null);
	}

	public void onPress(int clickX, int clickY) {
		if (buttonRect.contains(clickX, clickY)) {
			buttonDown = true;
		} else {
			buttonDown = false;
		}
	}

	public void cancel() {
		buttonDown = false;
	}

	public boolean isPressed(int touchX, int touchY) {
		return buttonDown && buttonRect.contains(touchX, touchY);
	}
}