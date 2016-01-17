package com.galas.filip.kropki.entity.ai;

import java.awt.Color;
import java.awt.Graphics2D;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.galas.filip.kropki.entity.Enemy;
import com.galas.filip.kropki.entity.Player;

public class SimpleEnemyAIStrategy implements EnemyAIStrategy {

	private int watchDist;
	private int attackDist;
	private int speed;

	private static enum State {
		NORMAL, WATCHING, ATTACKING
	}

	private State state = State.NORMAL;

	private boolean rangeVisibility;

	public SimpleEnemyAIStrategy() {

	}

	public SimpleEnemyAIStrategy(int watchDist, int attackDist, int speed) {
		this.watchDist = watchDist;
		this.attackDist = attackDist;
		this.speed = speed;
	}

	public void update(Enemy e) {
		Player player = Player.getInstance();

		// set state
		int distance = (int) Math.round(player.getPosition().distance(
				e.getPosition()));
		if (distance > watchDist) {
			state = State.NORMAL;
		} else if (distance <= watchDist && distance > attackDist) {
			state = State.WATCHING;
		} else {
			state = State.ATTACKING;
		}

		// active behaviour: attack
		if (state == State.ATTACKING) {
			e.simpleMoveToPoint(player.getPosition(), speed);
		}

	}

	public void draw(Enemy e, Graphics2D g) {

		// set color
		if (state == State.NORMAL) {
			g.setColor(Color.GREEN);
		}
		if (state == State.WATCHING) {
			g.setColor(Color.YELLOW);
		}
		if (state == State.ATTACKING) {
			g.setColor(Color.RED);
		}

		// draw
		int x = e.getX() - e.getDotRadius();
		int y = e.getY() - e.getDotRadius();
		g.fillOval(x, y, 2 * e.getDotRadius(), 2 * e.getDotRadius());

		// if ranges are visible, draw range circles
		if (rangeVisibility) {
			x = e.getX() - watchDist;
			y = e.getY() - watchDist;
			g.setColor(Color.YELLOW);
			g.drawOval(x, y, 2 * watchDist, 2 * watchDist);

			x = e.getX() - attackDist;
			y = e.getY() - attackDist;
			g.setColor(Color.RED);
			g.drawOval(x, y, 2 * attackDist, 2 * attackDist);
		}
	}

	public void setRangeVisibility(boolean visible) {

		rangeVisibility = visible;
	}

	public int getWatchDist() {
		return watchDist;
	}

	public void setWatchDist(int watchDist) {
		this.watchDist = watchDist;
	}

	public int getAttackDist() {
		return attackDist;
	}

	public void setAttackDist(int attackDist) {
		this.attackDist = attackDist;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public boolean isRangeVisible() {
		return rangeVisibility;
	}

	public void setupFromXMLElement(Element element) {
		String str = null;
		NodeList nodeList = null;
		
		nodeList = element.getElementsByTagName("watchdist");
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				str = nodeList.item(i).getTextContent();
			}
		}
		str = str.trim();
		this.setWatchDist(Integer.parseInt(str));
		
		nodeList = element.getElementsByTagName("attackdist");
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				str = nodeList.item(i).getTextContent();
			}
		}
		str = str.trim();
		this.setAttackDist(Integer.parseInt(str));
		
		nodeList = element.getElementsByTagName("speed");
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				str = nodeList.item(i).getTextContent();
			}
		}
		str = str.trim();
		this.setSpeed(Integer.parseInt(str));
	}

}
