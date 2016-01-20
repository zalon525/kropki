package com.galas.filip.kropki.entity;

import java.awt.Graphics2D;
import java.awt.Point;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.galas.filip.kropki.GameEvent;
import com.galas.filip.kropki.ParsingUtil;
import com.galas.filip.kropki.XMLLoadable;

public class Link extends Entity implements XMLLoadable {

	private String targetSceneName;
	private int activationOffset;

	public Link() {
		super();
	}

	public Link(Point position, String targetSceneName, int activationOffset) {
		super(position);
		this.targetSceneName = targetSceneName;
		this.activationOffset = activationOffset;
	}

	public String getTargetSceneName() {
		return targetSceneName;
	}

	public void setTargetSceneName(String sceneName) {
		this.targetSceneName = sceneName;
	}

	public int getActivationOffset() {
		return activationOffset;
	}

	public void setActivationOffset(int offset) {
		this.activationOffset = offset;
	}

	public GameEvent update() {
		Player player = Player.getInstance();
		int dist = (int) Math.round(player.getPosition().distance(this.getPosition()));
		if (dist <= activationOffset) {
			return GameEvent.newLinkGameEvent(targetSceneName);
		} else {
			return null;
		}
	}

	public void draw(Graphics2D g) {

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

		nodeList = element.getElementsByTagName("scenename");
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				str = nodeList.item(i).getTextContent();
			}
		}
		str = str.trim();
		this.setTargetSceneName(str);

		nodeList = element.getElementsByTagName("activationoffset");
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				str = nodeList.item(i).getTextContent();
			}
		}
		str = str.trim();
		this.setActivationOffset(Integer.parseInt(str));

		if (element.hasAttribute("exit") && element.getAttribute("exit").equals("true")) {
			this.setTargetSceneName(null);
		}
	}

}
