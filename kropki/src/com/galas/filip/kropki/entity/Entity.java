package com.galas.filip.kropki.entity;

import java.awt.Point;

import com.galas.filip.kropki.GameEvent;

import java.awt.Graphics2D;

public abstract class Entity {

	private Point position;

	public Entity() {

	}

	public Entity(Point position) {
		this.position = position;
	}

	public Point getPosition() {
		return (Point) (position.clone());
	}

	public int getX() {
		return position.x;
	}
	
	public int getY() {
		return position.y;
	}
	
	public void setPosition(Point position) {
		this.position = (Point) (position.clone());
	}

	public void move(int delta_x, int delta_y) {
		Point p = new Point(position);
		p.translate(delta_x, delta_y);
		this.position = p;
	}

	public void simpleMoveToPoint(Point p, int speed) {
		if (p.x > this.getX()) {
			if (p.x - this.getX() < speed) {
				this.setPosition(new Point(p.x, this.getY()));
			} else {
				this.move(speed, 0);
			}
		} else if (p.x < this.getX()) {
			if (this.getX() - p.x < speed) {
				this.setPosition(new Point(p.x, this.getY()));
			} else {
				this.move(-speed, 0);
			}
		}
		if (p.y > this.getY()) {
			if (p.y - this.getY() < speed) {
				this.setPosition(new Point(this.getX(), p.y));
			} else {
				this.move(0, speed);
			}
		} else if (p.y < this.getY()) {
			if (this.getY() - p.y < speed) {
				this.setPosition(new Point(this.getX(), p.y));
			} else {
				this.move(0, -speed);
			}
		}
	}

	public abstract GameEvent update();

	public abstract void draw(Graphics2D g);
}
