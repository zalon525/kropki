package com.galas.filip.kropki.entity;

import java.awt.Graphics2D;
import java.awt.Point;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.galas.filip.kropki.GameEvent;
import com.galas.filip.kropki.XMLLoadable;
import com.galas.filip.kropki.ParsingUtil;
import com.galas.filip.kropki.entity.ai.EnemyAIStrategy;

public class Enemy extends Dot implements XMLLoadable {

	private EnemyAIStrategy ai;

	public Enemy() {
		super();
	}

	public Enemy(Point position, int dotSize, EnemyAIStrategy ai) {
		super(position, dotSize);
		this.ai = ai;
	}

	public EnemyAIStrategy getAi() {
		return ai;
	}

	public void setAi(EnemyAIStrategy ai) {
		this.ai = ai;
	}

	public GameEvent update() {
		ai.update(this);
		Player player = Player.getInstance();
		if (player.getPosition().equals(this.getPosition())) {
			return GameEvent.newLostGameEvent();
		} else {
			return null;
		}
	}

	public void draw(Graphics2D g) {
		ai.draw(this, g);
	}

	public void setRangeVisibility(boolean visible) {
		ai.setRangeVisibility(visible);
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

		nodeList = element.getElementsByTagName("dotradius");
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				str = nodeList.item(i).getTextContent();
			}
		}
		str = str.trim();
		this.setDotRadius(Integer.parseInt(str));

		EnemyAIStrategy ai = null;
		nodeList = element.getElementsByTagName("ai");
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) (nodeList.item(i));
				String className = e.getAttribute("class");
				try {
					// TODO: this probably doesn't work now as the package
					// structure has changed
					ai = (EnemyAIStrategy) (Class.forName("com.galas.filip.kropki." + className).newInstance());
				} catch (InstantiationException ex) {
					ex.printStackTrace();
				} catch (IllegalAccessException ex) {
					ex.printStackTrace();
				} catch (ClassNotFoundException ex) {
					ex.printStackTrace();
				}
				ai.setupFromXMLElement(e);
			}
		}
		this.setAi(ai);

		setRangeVisibility(false);
		if (element.getAttribute("showrange") != null && element.getAttribute("showrange").equals("true")) {
			setRangeVisibility(true);
		}
	}

}
