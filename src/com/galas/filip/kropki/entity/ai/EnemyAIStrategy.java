package com.galas.filip.kropki.entity.ai;

import java.awt.Graphics2D;

import com.galas.filip.kropki.entity.Enemy;
import com.galas.filip.kropki.loading.Loadable;

public interface EnemyAIStrategy extends Loadable {

	void update(Enemy e);

	void draw(Enemy e, Graphics2D g);

	void setRangeVisibility(boolean visible);
}
