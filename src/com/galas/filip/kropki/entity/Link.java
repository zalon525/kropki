package com.galas.filip.kropki.entity;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Arrays;
import java.util.List;

import com.galas.filip.kropki.GameEvent;
import com.galas.filip.kropki.loading.EntityParameter;
import com.galas.filip.kropki.loading.EntityParameterMap;
import com.galas.filip.kropki.loading.IntegerEntityParameter;
import com.galas.filip.kropki.loading.Loadable;
import com.galas.filip.kropki.loading.PointEntityParameter;
import com.galas.filip.kropki.loading.StringEntityParameter;

public class Link extends Entity implements Loadable {

	private String targetSceneName;
	private int activationOffset;

	public Link() {
		super();
	}

	private static final EntityParameter<?>[] PARAMETERS = { new PointEntityParameter("position", new Point()),
			new StringEntityParameter("scenename", ""), new IntegerEntityParameter("activationoffset", 0) };

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

	@Override
	public void setupFromParameters(EntityParameterMap parameters) {
		setPosition(parameters.getParameterValue("position"));
		setTargetSceneName(parameters.getParameterValue("scenename"));
		setActivationOffset(parameters.getParameterValue("activationoffset"));
	}

	@Override
	public List<EntityParameter<?>> getEntityParameters() {
		return Arrays.asList(PARAMETERS);
	}
}
