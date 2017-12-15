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

public class ColloredDot extends Dot implements Loadable {

	private Color color;

	public ColloredDot() {
		super();
	}

	private static final EntityParameter<?>[] PARAMETERS = { new PointEntityParameter("position", new Point()),
			new IntegerEntityParameter("size", 0), new ColorEntityParameter("color", new Color(100, 100, 100)) };

	public ColloredDot(Point position, int dotRadius, Color color) {
		super(position, dotRadius);
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

		int x = getX() - getDotRadius();
		int y = getY() - getDotRadius();
		g.fillOval(x, y, 2 * getDotRadius(), 2 * getDotRadius());
	}

	@Override
	public void setupFromParameters(EntityParameterMap parameters) {
		setPosition(parameters.getParameterValue("position"));
		setDotRadius(parameters.getParameterValue("size"));
		setColor(parameters.getParameterValue("color"));
	}

	@Override
	public List<EntityParameter<?>> getEntityParameters() {
		return Arrays.asList(PARAMETERS);
	}

}
