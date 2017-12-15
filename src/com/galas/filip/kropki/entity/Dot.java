package com.galas.filip.kropki.entity;

import java.awt.Point;

public abstract class Dot extends Entity {

	private int dotRadius;

	public Dot() {
		super();
	}

	public Dot(Point position, int dotRadius) {
		super(position);
		this.dotRadius = dotRadius;
	}

	public int getDotRadius() {
		return dotRadius;
	}

	public void setDotRadius(int dotRadius) {
		this.dotRadius = dotRadius;
	}

}
