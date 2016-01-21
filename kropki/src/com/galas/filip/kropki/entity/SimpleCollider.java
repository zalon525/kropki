package com.galas.filip.kropki.entity;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.galas.filip.kropki.GameEvent;
import com.galas.filip.kropki.loading.EntityParameter;
import com.galas.filip.kropki.loading.EntityParameterMap;
import com.galas.filip.kropki.loading.IntegerEntityParameter;
import com.galas.filip.kropki.loading.PointEntityParameter;

/* 
 * This is a class, which implements simple and inaccurate collision
 */

public class SimpleCollider extends Collider {

	private Map<Entity, Point> prevNonCollidingPositions = new HashMap<Entity, Point>();

	public SimpleCollider() {
		super();
	}

	private static final EntityParameter<?>[] PARAMETERS = { new PointEntityParameter("position", new Point()),
			new IntegerEntityParameter("width", 0), new IntegerEntityParameter("height", 0) };

	/*
	 * Warning! Assuming that when this constructor is called all collidable
	 * entities' positions are non-colliding
	 */

	public SimpleCollider(Point position, int width, int height, Collection<Entity> collidableEntities) {
		super(position, width, height, collidableEntities);

		for (Entity e : getCollidableEntities()) {
			prevNonCollidingPositions.put(e, e.getPosition());
		}
	}

	public GameEvent update() {

		for (Entity e : getCollidableEntities()) {

			if (this.contains(e.getPosition())) {
				Point slidedOnXAxisPoint = new Point(prevNonCollidingPositions.get(e).x, e.getY());
				Point slidedOnYAxisPoint = new Point(e.getX(), prevNonCollidingPositions.get(e).y);

				if (!this.contains(slidedOnXAxisPoint)) {
					e.setPosition(slidedOnXAxisPoint);
				} else if (!this.contains(slidedOnYAxisPoint)) {
					e.setPosition(slidedOnYAxisPoint);
				} else {
					e.setPosition(prevNonCollidingPositions.get(e));
				}
			} else {
				prevNonCollidingPositions.put(e, e.getPosition());
			}
		}

		return null;
	}

	public void draw(Graphics2D g) {

	}

	@Override
	public void setupFromParameters(EntityParameterMap parameters) {
		setPosition(parameters.getParameterValue("position"));
		setWidth(parameters.getParameterValue("width"));
		setHeight(parameters.getParameterValue("height"));
	}

	@Override
	public List<EntityParameter<?>> getEntityParameters() {
		return Arrays.asList(PARAMETERS);
	}

}
