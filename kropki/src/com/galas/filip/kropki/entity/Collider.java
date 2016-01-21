package com.galas.filip.kropki.entity;

import java.awt.Point;
import java.util.Collection;

import com.galas.filip.kropki.loading.Loadable;

public abstract class Collider extends Area implements Loadable {

	private Collection<Entity> collidableEntities;

	public Collider() {
		super();
	}

	public Collider(Point position, int width, int height, Collection<Entity> collidableEntities) {
		super(position, width, height);
		this.collidableEntities = collidableEntities;
	}

	public void addCollidableEntity(Entity e) {
		collidableEntities.add(e);
	}

	public void removeCollidableEntity(Entity e) {
		collidableEntities.remove(e);
	}

	public Collection<Entity> getCollidableEntities() {
		return collidableEntities;
	}

	public void setCollidableEntities(Collection<Entity> collidableEntities) {
		this.collidableEntities = collidableEntities;
	}
}
