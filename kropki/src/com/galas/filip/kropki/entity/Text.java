package com.galas.filip.kropki.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.galas.filip.kropki.GameEvent;
import com.galas.filip.kropki.XMLLoadable;
import com.galas.filip.kropki.ParsingUtil;

public class Text extends Entity implements XMLLoadable {

	private String text;
	private int size;
	private Color color;

	public Text() {
		super();
	}

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

		nodeList = element.getElementsByTagName("text");
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				str = nodeList.item(i).getTextContent();
			}
		}
		str = str.trim();
		this.setText(str);

		nodeList = element.getElementsByTagName("size");
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				str = nodeList.item(i).getTextContent();
			}
		}
		str = str.trim();
		this.setSize(Integer.parseInt(str));

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
