package com.galas.filip.kropki.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Arrays;
import java.util.List;

import com.galas.filip.kropki.GameEvent;
import com.galas.filip.kropki.loading.ColorEntityParameter;
import com.galas.filip.kropki.loading.EntityParameter;
import com.galas.filip.kropki.loading.EntityParameterMap;
import com.galas.filip.kropki.loading.IntegerEntityParameter;
import com.galas.filip.kropki.loading.Loadable;
import com.galas.filip.kropki.loading.PointEntityParameter;

public class ColloredArea extends Area implements Loadable {

	private Color color;

	public ColloredArea() {
		super();
	}

	private static final EntityParameter<?>[] PARAMETERS = { new PointEntityParameter("position", new Point()),
			new IntegerEntityParameter("width", 0), new IntegerEntityParameter("height", 0),
			new ColorEntityParameter("color", new Color(100, 100, 100)) };

	public ColloredArea(Point position, int width, int height, Color color) {
		super(position, width, height);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public GameEvent update() {
		return null;
	}

	public void draw(Graphics2D g) {
		g.setColor(color);
		g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}

	@Override
	public void setupFromParameters(EntityParameterMap parameters) {
		setPosition(parameters.getParameterValue("position"));
		setWidth(parameters.getParameterValue("width"));
		setHeight(parameters.getParameterValue("height"));
		setColor(parameters.getParameterValue("color"));
	}

	@Override
	public List<EntityParameter<?>> getEntityParameters() {
		return Arrays.asList(PARAMETERS);
	}
}
