package com.galas.filip.kropki;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import com.galas.filip.kropki.entity.Entity;
import com.galas.filip.kropki.entity.Player;

public class UpdateThread extends Thread {

	private List<Entity> entities;
	private GameFrame frame;
	private Properties config;

	private Collection<GameEventListener> gameListeners = new ArrayList<GameEventListener>();

	private boolean running;

	private int scrollX = 0;
	private int scrollY = 0;
	private int dScrollX = 0;
	private int dScrollY = 0;

	public UpdateThread(List<Entity> entities, GameFrame frame, Properties config) {
		super("UpdateThread");
		this.entities = entities;
		this.frame = frame;
		this.config = config;
	}

	public void addGameListener(GameEventListener gl) {
		gameListeners.add(gl);
	}

	public void removeGameListener(GameEventListener gl) {
		gameListeners.remove(gl);
	}

	public void kill() {
		running = false;
	}

	private int getScrollX() {
		return scrollX;
	}

	private void addToScrollX(int delta) {
		dScrollX = delta;
		this.scrollX = scrollX + delta;
	}

	private int getScrollY() {
		return scrollY;
	}

	private void addToScrollY(int delta) {
		dScrollY = delta;
		this.scrollY = scrollY + delta;
	}

	private int getdScrollX() {
		return dScrollX;
	}

	private int getdScrollY() {
		return dScrollY;
	}

	public void run() {
		BufferedImage gameScreen = frame.getGameScreen();
		Graphics2D g = gameScreen.createGraphics();
		Player player = Player.getInstance();
		Rectangle nonScrollRect = new Rectangle();

		// center the view on the player
		this.addToScrollX(player.getX() - (frame.getWidth() / 2));
		this.addToScrollY(player.getY() - (frame.getHeight() / 2));

		running = true;

		while (running) {

			// update
			for (Entity e : entities) {
				GameEvent ge = e.update();
				if (ge != null) {
					for (GameEventListener gl : gameListeners) {
						gl.onGameEvent(ge);
					}
				}
			}

			Color backgroundColor = ParsingUtil
					.parseColor(config.getProperty(ConfigurationModel.DEFAULT_BACKROUND_COLOR));
			// draw to the game screen
			synchronized (gameScreen) {
				g.translate(-getdScrollX(), -getdScrollY());
				g.setBackground(backgroundColor);
				g.clearRect(getScrollX(), getScrollY(), gameScreen.getWidth(), gameScreen.getHeight());
				for (Entity e : entities) {
					e.draw(g);
				}
			}

			// tell the frame to draw the game screen onto the window
			frame.repaint();

			int nonScrollingAreaWidth = Integer
					.valueOf(config.getProperty(ConfigurationModel.NON_SCROLLING_AREA_WIDTH));
			int nonScrollingAreaHeight = Integer
					.valueOf(config.getProperty(ConfigurationModel.NON_SCROLLING_AREA_HEIGHT));
			// calculate the scroll
			int x = (frame.getWidth() - nonScrollingAreaWidth) / 2 + getScrollX();
			int y = (frame.getHeight() - nonScrollingAreaHeight) / 2 + getScrollY();
			nonScrollRect = new Rectangle(x, y, nonScrollingAreaWidth, nonScrollingAreaHeight);
			if (player.getX() > (int) (nonScrollRect.getMaxX())) {
				addToScrollX(player.getX() - (nonScrollRect.x + nonScrollRect.width));
			} else if (player.getX() < (int) (nonScrollRect.getMinX())) {
				addToScrollX(player.getX() - nonScrollRect.x);
			} else {
				addToScrollX(0);
			}
			if (player.getY() > (int) (nonScrollRect.getMaxY())) {
				addToScrollY(player.getY() - (nonScrollRect.y + nonScrollRect.height));
			} else if (player.getY() < (int) (nonScrollRect.getMinY())) {
				addToScrollY(player.getY() - nonScrollRect.y);
			} else {
				addToScrollY(0);
			}

			// sleep... zzzzz
			try {
				sleep(Integer.valueOf(config.getProperty(ConfigurationModel.UPDATE_DELAY)));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
