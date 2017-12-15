package com.galas.filip.kropki.entity;

import java.awt.Point;

public abstract class Area extends Entity {

	private int width;
	private int height;

	public Area() {
		super();
	}

	public Area(Point position, int width, int height) {
		super(position);
		this.width = width;
		this.height = height;
	}

	public boolean contains(Point p) {
		if ((p.x >= this.getX() && p.x <= this.getX() + width)
				&& (p.y >= this.getY() && p.y <= this.getY() + height)) {
			return true;
		} else {
			return false;
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
