package com.galas.filip.kropki;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;

import com.galas.filip.kropki.entity.Collider;
import com.galas.filip.kropki.entity.Entity;

public class Scene {

	private SortedMap<Integer, List<Entity>> layers;
	private Point startPosition;
	private Color backgroundColor; // optional, can be set to null

	public Scene(SortedMap<Integer, List<Entity>> layers, Point startPosition, Color backgroundColor) {
		this.layers = layers;
		this.startPosition = startPosition;
		this.backgroundColor = backgroundColor;
	}

	public List<Entity> getEntities() {
		List<Entity> entities = new ArrayList<Entity>();

		for (Entry<Integer, List<Entity>> e : layers.entrySet()) {
			entities.addAll(e.getValue());
		}

		return entities;
	}

	public Point getStartPosition() {
		return (Point) (startPosition.clone());
	}

	public void setStartPosition(Point position) {
		startPosition = (Point) position.clone();
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public void addEntityAsFirstInLayer(Entity e, boolean collidable, int layerNum) {
		List<Entity> layer;
		if (layers.get(layerNum) == null) {
			layer = new ArrayList<Entity>();
			layers.put(layerNum, layer);
		} else {
			layer = layers.get(layerNum);
		}
		layer.add(0, e);

		if (collidable) {
			for (Entity en : getEntities()) {
				if (Collider.class.isInstance(en)) {
					Collider c = (Collider) en;
					c.addCollidableEntity(e);
				}
			}
		}
	}

	public void addEntityAsLastInLayer(Entity e, boolean collidable, int layerNum) {
		List<Entity> layer;
		if (layers.get(layerNum) == null) {
			layer = new ArrayList<Entity>();
			layers.put(layerNum, layer);
		} else {
			layer = layers.get(layerNum);
		}
		layer.add(e);

		if (collidable) {
			for (Entity en : getEntities()) {
				if (Collider.class.isInstance(en)) {
					Collider c = (Collider) en;
					c.addCollidableEntity(e);
				}
			}
		}
	}

}
