package com.galas.filip.kropki.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.EnumSet;

import com.galas.filip.kropki.GameEvent;

public class Player extends Dot {

	private static Player sole_instance; // SINGLETON

	private Color color;
	private int speed;

	private static enum Direction {
		UP, DOWN, RIGHT, LEFT;
	}

	private EnumSet<Direction> moveDirection = EnumSet.noneOf(Direction.class);

	private Player(Point position, int dotSize, int speed, Color color) {
		super(position, dotSize);
		this.speed = speed;
		this.color = color;
	}

	public static Player createInstance(Point position, int speed, int dotSize, Color color) {
		if (sole_instance == null) {
			sole_instance = new Player(position, speed, dotSize, color);
		}
		return sole_instance;
	}

	public static Player getInstance() {
		return sole_instance;
	}

	public void setMoveUp() {
		moveDirection.add(Direction.UP);
		moveDirection.remove(Direction.DOWN);
	}

	public void setMoveDown() {
		moveDirection.add(Direction.DOWN);
		moveDirection.remove(Direction.UP);
	}

	public void setMoveRight() {
		moveDirection.add(Direction.RIGHT);
		moveDirection.remove(Direction.LEFT);
	}

	public void setMoveLeft() {
		moveDirection.add(Direction.LEFT);
		moveDirection.remove(Direction.RIGHT);
	}

	public void unsetMoveUp() {
		moveDirection.remove(Direction.UP);
	}

	public void unsetMoveDown() {
		moveDirection.remove(Direction.DOWN);
	}

	public void unsetMoveRight() {
		moveDirection.remove(Direction.RIGHT);
	}

	public void unsetMoveLeft() {
		moveDirection.remove(Direction.LEFT);
	}

	// TODO: moving algorithm: players moves faster diagonally
	public GameEvent update() {
		if (moveDirection.contains(Direction.UP)) {
			move(0, -speed);
		}
		if (moveDirection.contains(Direction.DOWN)) {
			move(0, speed);
		}
		if (moveDirection.contains(Direction.RIGHT)) {
			move(speed, 0);
		}
		if (moveDirection.contains(Direction.LEFT)) {
			move(-speed, 0);
		}

		return null;
	}

	public void draw(Graphics2D g) {
		g.setColor(color);
		int x = getX() - getDotRadius();
		int y = getY() - getDotRadius();
		g.fillOval(x, y, 2 * getDotRadius(), 2 * getDotRadius());
	}

}
