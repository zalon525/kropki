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

	public void addGameEventListener(GameEventListener gl) {
		gameListeners.add(gl);
	}

	public void removeGameEventListener(GameEventListener gl) {
		gameListeners.remove(gl);
	}

	public boolean isRunning() {
		return running;
	}

	public synchronized void setRunning(boolean running) {
		this.running = running;
	}

	public void run() {
		BufferedImage gameScreen = frame.getGameScreen();
		Graphics2D g = gameScreen.createGraphics();
		Player player = Player.getInstance();

		Color backgroundColor = ParsingUtil.parseColor(config.getProperty(ConfigurationModel.DEFAULT_BACKROUND_COLOR));
		int nonScrollingAreaWidth = Integer.valueOf(config.getProperty(ConfigurationModel.NON_SCROLLING_AREA_WIDTH));
		int nonScrollingAreaHeight = Integer.valueOf(config.getProperty(ConfigurationModel.NON_SCROLLING_AREA_HEIGHT));

		centerViewOnPlayer(player);

		setRunning(true);

		long now = System.nanoTime();
		long lastUpdateTime = now;
		int updateDelayMili = Integer.valueOf(config.getProperty(ConfigurationModel.UPDATE_DELAY));
		int updateGranularityMili = Integer.valueOf(config.getProperty(ConfigurationModel.UPDATE_GRANULARITY));
		final int NANO_PER_MILI = 1_000_000;

		while (isRunning()) {

			now = System.nanoTime();

			if (now - lastUpdateTime >= updateDelayMili * NANO_PER_MILI) {
				lastUpdateTime = System.nanoTime();

				updateEntities();

				drawGameScreen(g, gameScreen, backgroundColor);

				frame.repaint();

				calculateScroll(player, nonScrollingAreaWidth, nonScrollingAreaHeight);
			}

			try {
				sleep(updateGranularityMili);
			} catch (InterruptedException e) {
				throw new RuntimeException("update sleep interrupted", e);
			}
		}
	}

	private int getScrollX() {
		return scrollX;
	}

	private void addToScrollX(int delta) {
		dScrollX = delta;
		scrollX = scrollX + delta;
	}

	private int getScrollY() {
		return scrollY;
	}

	private void addToScrollY(int delta) {
		dScrollY = delta;
		scrollY = scrollY + delta;
	}

	private int getdScrollX() {
		return dScrollX;
	}

	private int getdScrollY() {
		return dScrollY;
	}

	private void centerViewOnPlayer(Player player) {
		addToScrollX(player.getX() - (frame.getWidth() / 2));
		addToScrollY(player.getY() - (frame.getHeight() / 2));
	}

	private void updateEntities() {
		for (Entity e : entities) {
			GameEvent ge = e.update();
			if (ge != null) {
				for (GameEventListener gl : gameListeners) {
					gl.onGameEvent(ge);
				}
			}
		}
	}

	private void drawGameScreen(Graphics2D g, BufferedImage gameScreen, Color backgroundColor) {
		synchronized (gameScreen) {
			g.translate(-getdScrollX(), -getdScrollY());
			g.setBackground(backgroundColor);
			g.clearRect(getScrollX(), getScrollY(), gameScreen.getWidth(), gameScreen.getHeight());
			for (Entity e : entities) {
				e.draw(g);
			}
		}
	}

	private void calculateScroll(Player player, int nonScrollingAreaWidth, int nonScrollingAreaHeight) {
		int x = (frame.getWidth() - nonScrollingAreaWidth) / 2 + getScrollX();
		int y = (frame.getHeight() - nonScrollingAreaHeight) / 2 + getScrollY();
		Rectangle nonScrollRect = new Rectangle(x, y, nonScrollingAreaWidth, nonScrollingAreaHeight);

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
	}
}
