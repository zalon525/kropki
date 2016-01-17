package com.galas.filip.kropki.entity;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.galas.filip.kropki.GameEvent;
import com.galas.filip.kropki.ParsingUtil;

/* 
 * This is a class, which implements simple and inaccurate collision
 */

public class SimpleCollider extends Collider {

	private Map<Entity, Point> prevNonCollidingPositions = new HashMap<Entity, Point>();

	public SimpleCollider() {
		super();
	}

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

	// this setup does not set the collidableEntities list; it is done by the
	// XMLSceneLoader itself though
	public void setupFromXMLElement(Element element) {
		String str = null;
		NodeList nodeList = null;

		nodeList = element.getElementsByTagName("position");
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				str = nodeList.item(i).getTextContent();
			}
		}
		str = str.trim();
		this.setPosition(ParsingUtil.parsePoint(str));

		nodeList = element.getElementsByTagName("width");
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				str = nodeList.item(i).getTextContent();
			}
		}
		str = str.trim();
		this.setWidth(Integer.parseInt(str));

		nodeList = element.getElementsByTagName("height");
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				str = nodeList.item(i).getTextContent();
			}
		}
		str = str.trim();
		this.setHeight(Integer.parseInt(str));
	}

}
