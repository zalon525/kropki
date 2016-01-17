package com.galas.filip.kropki.entity;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.galas.filip.kropki.GameEvent;
import com.galas.filip.kropki.ParsingUtil;

public class AnimatedColloredDot extends ColloredDot {

	private List<Point> rout;
	private int speed;
	private int routPoint = 0;

	public AnimatedColloredDot() {
		super();
	}

	public AnimatedColloredDot(Point position, int dotRadius, Color color,
			List<Point> rout, int speed) {
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

	public void setupFromXMLElement(Element element) {
		String str = null;
		NodeList nodeList = null;

		List<Point> rout = new ArrayList<Point>();
		nodeList = element.getElementsByTagName("rout");
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) nodeList.item(i);
				NodeList points = e.getElementsByTagName("point");
				for (int j = 0; j < points.getLength(); j++) {
					if (points.item(j).getNodeType() == Node.ELEMENT_NODE) {
						str = points.item(j).getTextContent();
						Point p = ParsingUtil.parsePoint(str);
						rout.add(p);
					}
				}
			}
		}
		this.setRout(rout);

		nodeList = element.getElementsByTagName("speed");
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				str = nodeList.item(i).getTextContent();
			}
		}
		str = str.trim();
		this.setSpeed(Integer.parseInt(str));

		super.setupFromXMLElement(element);
	}

}
