package com.galas.filip.kropki;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class GameFrame extends Frame {

	private static final long serialVersionUID = -331736319788124026L;

	private BufferedImage gameScreen;

	public GameFrame(String title, int width, int height) {
		super(title);
		setSize(width, height);
		gameScreen = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}

	public BufferedImage getGameScreen() {
		return gameScreen;
	}

	public void paint(Graphics g) {
		synchronized (gameScreen) {
			g.drawImage(gameScreen, 0, 0, null);
		}
	}

	public void update(Graphics g) {
		paint(g);
	}
}
