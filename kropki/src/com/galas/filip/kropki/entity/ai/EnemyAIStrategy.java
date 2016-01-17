package com.galas.filip.kropki.entity.ai;

import java.awt.Graphics2D;

import com.galas.filip.kropki.XMLLoadable;
import com.galas.filip.kropki.entity.Enemy;

public interface EnemyAIStrategy extends XMLLoadable {

	void update(Enemy e);

	void draw(Enemy e, Graphics2D g);

	void setRangeVisibility(boolean visible);
}
