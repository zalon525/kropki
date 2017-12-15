package com.galas.filip.kropki.entity;

import java.awt.Color;
import java.awt.Font;
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
import com.galas.filip.kropki.loading.StringEntityParameter;

public class Text extends Entity implements Loadable {

	private String text;
	private int size;
	private Color color;

	public Text() {
		super();
	}

	private static final EntityParameter<?>[] PARAMETERS = { new PointEntityParameter("position", new Point()),
			new StringEntityParameter("text", ""), new IntegerEntityParameter("size", 0),
			new ColorEntityParameter("color", new Color(100, 100, 100)) };

	public Text(Point position, String text, int size, Color color) {
		super(position);
		this.text = text;
		this.size = size;
		this.color = color;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
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
		g.setFont(new Font(Font.DIALOG, Font.PLAIN, size));
		g.drawString(text, this.getX(), this.getY());
	}

	@Override
	public void setupFromParameters(EntityParameterMap parameters) {
		setPosition(parameters.getParameterValue("position"));
		setText(parameters.getParameterValue("text"));
		setSize(parameters.getParameterValue("size"));
		setColor(parameters.getParameterValue("color"));
	}

	@Override
	public List<EntityParameter<?>> getEntityParameters() {
		return Arrays.asList(PARAMETERS);
	}
}
