package com.galas.filip.kropki.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.galas.filip.kropki.GameEvent;
import com.galas.filip.kropki.XMLLoadable;
import com.galas.filip.kropki.ParsingUtil;

public class ColloredArea extends Area implements XMLLoadable {

	private Color color;

	public ColloredArea() {
		super();
	}

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

		nodeList = element.getElementsByTagName("color");
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				str = nodeList.item(i).getTextContent();
			}
		}
		str = str.trim();
		this.setColor(ParsingUtil.parseColor(str));
	}
}
