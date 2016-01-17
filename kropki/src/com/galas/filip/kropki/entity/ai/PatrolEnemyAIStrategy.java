package com.galas.filip.kropki.entity.ai;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.galas.filip.kropki.ParsingUtil;
import com.galas.filip.kropki.entity.Enemy;
import com.galas.filip.kropki.entity.Player;

public class PatrolEnemyAIStrategy implements EnemyAIStrategy {

	private List<Point> rout;
	private int attackDist;
	private int patrolSpeed;
	private int attackSpeed;

	private static enum State {
		PATROLLING, ATTACKING
	}

	private State state = State.PATROLLING;
	private int routPoint = 0;

	private boolean rangeVisibility;

	public PatrolEnemyAIStrategy() {

	}

	public PatrolEnemyAIStrategy(List<Point> rout, int attackDist,
			int patrolSpeed, int attackSpeed) {
		this.rout = rout;
		this.attackDist = attackDist;
		this.patrolSpeed = patrolSpeed;
		this.attackSpeed = attackSpeed;
	}

	public void update(Enemy e) {

		Player player = Player.getInstance();

		// set state
		int distance = (int) Math.round(player.getPosition().distance(
				e.getPosition()));
		if (distance <= attackDist) {
			state = State.ATTACKING;
		} else {
			state = State.PATROLLING;
		}

		// active behaviour: attack
		if (state == State.ATTACKING) {
			e.simpleMoveToPoint(player.getPosition(), attackSpeed);
		}

		// active behaviour: patrol
		if (state == State.PATROLLING) {
			e.simpleMoveToPoint(rout.get(routPoint), patrolSpeed);
			if (e.getPosition().equals(rout.get(routPoint))) {
				routPoint = (routPoint + 1) % rout.size();
			}
		}

	}

	public void draw(Enemy e, Graphics2D g) {

		// set color
		if (state == State.PATROLLING) {
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
			x = e.getX() - attackDist;
			y = e.getY() - attackDist;
			g.setColor(Color.RED);
			g.drawOval(x, y, 2 * attackDist, 2 * attackDist);
		}
	}

	public void setRangeVisibility(boolean visible) {

		rangeVisibility = visible;
	}

	public List<Point> getRout() {
		return rout;
	}

	public void setRout(List<Point> rout) {
		this.rout = rout;
	}

	public int getAttackDist() {
		return attackDist;
	}

	public void setAttackDist(int attackDist) {
		this.attackDist = attackDist;
	}

	public int getPatrolSpeed() {
		return patrolSpeed;
	}

	public void setPatrolSpeed(int patrolSpeed) {
		this.patrolSpeed = patrolSpeed;
	}

	public int getAttackSpeed() {
		return attackSpeed;
	}

	public void setAttackSpeed(int attackSpeed) {
		this.attackSpeed = attackSpeed;
	}

	public boolean isRangeVisible() {
		return rangeVisibility;
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

		nodeList = element.getElementsByTagName("attackdist");
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				str = nodeList.item(i).getTextContent();
			}
		}
		str = str.trim();
		this.setAttackDist(Integer.parseInt(str));

		nodeList = element.getElementsByTagName("patrolspeed");
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				str = nodeList.item(i).getTextContent();
			}
		}
		str = str.trim();
		this.setPatrolSpeed(Integer.parseInt(str));

		nodeList = element.getElementsByTagName("attackspeed");
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				str = nodeList.item(i).getTextContent();
			}
		}
		str = str.trim();
		this.setAttackSpeed(Integer.parseInt(str));
	}

}
