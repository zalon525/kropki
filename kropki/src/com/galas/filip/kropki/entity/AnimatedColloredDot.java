package com.galas.filip.kropki.entity;

import java.awt.Color;
import java.awt.Point;
import java.util.Arrays;
import java.util.List;

import com.galas.filip.kropki.GameEvent;
import com.galas.filip.kropki.loading.ColorEntityParameter;
import com.galas.filip.kropki.loading.EntityParameter;
import com.galas.filip.kropki.loading.EntityParameterMap;
import com.galas.filip.kropki.loading.IntegerEntityParameter;
import com.galas.filip.kropki.loading.PointEntityParameter;
import com.galas.filip.kropki.loading.PointListEntityParameter;

public class AnimatedColloredDot extends ColloredDot {

	private List<Point> rout;
	private int speed;
	private int routPoint = 0;

	public AnimatedColloredDot() {
		super();
	}

	private static final EntityParameter<?>[] PARAMETERS = { new PointEntityParameter("position", new Point()),
			new IntegerEntityParameter("size", 0), new ColorEntityParameter("color", new Color(100, 100, 100)),
			new PointListEntityParameter("rout"), new IntegerEntityParameter("speed", 1) };

	public AnimatedColloredDot(Point position, int dotRadius, Color color, List<Point> rout, int speed) {
		super(position, dotRadius, color);
		this.rout = rout;
		this.speed = speed;
	}

	public List<Point> getRout() {
		return rout;
	}

	public void setRout(List<Point> rout) {
		this.rout = rout;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public GameEvent update() {
		this.simpleMoveToPoint(rout.get(routPoint), speed);
		if (getPosition().equals(rout.get(routPoint))) {
			routPoint = (routPoint + 1) % rout.size();
		}

		return null;
	}

	@Override
	public void setupFromParameters(EntityParameterMap parameters) {
		setPosition(parameters.getParameterValue("position"));
		setDotRadius(parameters.getParameterValue("size"));
		setColor(parameters.getParameterValue("color"));
		setRout(parameters.getParameterValue("rout"));
		setSpeed(parameters.getParameterValue("speed"));
	}

	@Override
	public List<EntityParameter<?>> getEntityParameters() {
		return Arrays.asList(PARAMETERS);
	}

}
