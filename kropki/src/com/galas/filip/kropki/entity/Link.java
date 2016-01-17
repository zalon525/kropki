package com.galas.filip.kropki.entity;

import java.awt.Graphics2D;
import java.awt.Point;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.galas.filip.kropki.GameEvent;
import com.galas.filip.kropki.XMLLoadable;
import com.galas.filip.kropki.ParsingUtil;

public class Link extends Entity implements XMLLoadable {

	private String sceneURL;
	private Runnable action;
	private int activationOffset;

	public Link() {
		super();
	}

	public Link(Point position, String sceneURL, Runnable action, int activationOffset) {
		super(position);
		this.sceneURL = sceneURL;
		this.action = action;
		this.activationOffset = activationOffset;
	}

	public String getSceneURL() {
		return sceneURL;
	}

	public Runnable getAction() {
		return action;
	}

	public void setSceneURL(String sceneURL) {
		this.sceneURL = sceneURL;
	}

	public void setAction(Runnable action) {
		this.action = action;
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
			return GameEvent.newLinkGameEvent(sceneURL);
		} else {
			return null;
		}
	}

	public void draw(Graphics2D g) {

	}

	// setting the link action from an xml file is not yet implemented
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

		nodeList = element.getElementsByTagName("sceneurl");
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				str = nodeList.item(i).getTextContent();
			}
		}
		str = str.trim();
		this.setSceneURL(str);

		nodeList = element.getElementsByTagName("offset");
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				str = nodeList.item(i).getTextContent();
			}
		}
		str = str.trim();
		this.setActivationOffset(Integer.parseInt(str));

		if (element.hasAttribute("exit") && element.getAttribute("exit").equals("true")) {
			this.setSceneURL(null);
		}
	}

}
