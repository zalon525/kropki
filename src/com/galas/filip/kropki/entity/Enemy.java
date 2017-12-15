package com.galas.filip.kropki.entity;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Arrays;
import java.util.List;

import com.galas.filip.kropki.GameEvent;
import com.galas.filip.kropki.entity.ai.EnemyAIStrategy;
import com.galas.filip.kropki.entity.ai.SimpleEnemyAIStrategy;
import com.galas.filip.kropki.loading.EntityEntityParameter;
import com.galas.filip.kropki.loading.EntityParameter;
import com.galas.filip.kropki.loading.EntityParameterMap;
import com.galas.filip.kropki.loading.IntegerEntityParameter;
import com.galas.filip.kropki.loading.Loadable;
import com.galas.filip.kropki.loading.PointEntityParameter;

public class Enemy extends Dot implements Loadable {

	private EnemyAIStrategy ai;

	public Enemy() {
		super();
	}

	private static final EntityParameter<?>[] PARAMETERS = { new PointEntityParameter("position", new Point()),
			new IntegerEntityParameter("size", 6), new EntityEntityParameter("ai", new SimpleEnemyAIStrategy()) };

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
		int dist = (int) Math.round(player.getPosition().distance(getPosition()));

		if (dist < getDotRadius()) {
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

	@Override
	public void setupFromParameters(EntityParameterMap parameters) {
		setPosition(parameters.getParameterValue("position"));
		setDotRadius(parameters.getParameterValue("size"));
		setAi(parameters.getParameterValue("ai"));
	}

	@Override
	public List<EntityParameter<?>> getEntityParameters() {
		return Arrays.asList(PARAMETERS);
	}
}
