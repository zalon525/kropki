package com.galas.filip.kropki;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import com.galas.filip.kropki.entity.ColloredArea;
import com.galas.filip.kropki.entity.ColloredDot;
import com.galas.filip.kropki.entity.Enemy;
import com.galas.filip.kropki.entity.Entity;
import com.galas.filip.kropki.entity.SimpleCollider;
import com.galas.filip.kropki.entity.Text;
import com.galas.filip.kropki.entity.ai.PatrolEnemyAIStrategy;
import com.galas.filip.kropki.entity.ai.SimpleEnemyAIStrategy;
import com.galas.filip.kropki.loading.SceneLoader;

public class TestSceneLoader implements SceneLoader {

	public Scene getScene() {

		// example scene
		SortedMap<Integer, List<Entity>> layers = new TreeMap<Integer, List<Entity>>();
		List<Entity> collidableEntities = new ArrayList<Entity>();

		Point start = new Point(250, 250);
		
		List<Entity> entitiesBeforePlayer = new ArrayList<Entity>();
		List<Entity> entitiesAfterPlayer = new ArrayList<Entity>();
		List<Entity> colliders = new ArrayList<Entity>();
		
		layers.put(-1, entitiesBeforePlayer);
		layers.put(1, entitiesAfterPlayer);
		layers.put(Integer.MAX_VALUE, colliders);

		Enemy enemy = new Enemy(new Point(50, 50), 5,
				new SimpleEnemyAIStrategy(300, 150, 2));
		enemy.setRangeVisibility(true);
		collidableEntities.add(enemy);
		entitiesAfterPlayer.add(enemy);
		ColloredDot checkpoint1 = new ColloredDot(new Point(20, 250), 7,
				Color.MAGENTA);
		entitiesBeforePlayer.add(checkpoint1);
		ColloredDot checkpoint2 = new ColloredDot(new Point(480, 250), 7,
				Color.MAGENTA);
		entitiesBeforePlayer.add(checkpoint2);
		List<Point> rout = new ArrayList<Point>();
		rout.add(checkpoint1.getPosition());
		rout.add(checkpoint2.getPosition());
		Enemy patrollingEnemy = new Enemy(new Point(30, 30), 5,
				new PatrolEnemyAIStrategy(rout, 50, 1, 3));
		patrollingEnemy.setRangeVisibility(true);
		collidableEntities.add(patrollingEnemy);
		entitiesAfterPlayer.add(patrollingEnemy);
		// colliders
		colliders.add(new SimpleCollider(new Point(150, 50), 50, 50,
				collidableEntities));
		colliders.add(new ColloredArea(new Point(150, 50), 50, 50, Color.GRAY));
		colliders.add(new SimpleCollider(new Point(400, 400), 30, 60,
				collidableEntities));
		colliders.add(new ColloredArea(new Point(400, 400), 30, 60, Color.GRAY));
		// text
		Text text = new Text(new Point(400, 50), "Test level", 20, Color.BLACK);
		entitiesBeforePlayer.add(text);

		return new Scene(layers, start, null);
	}

}
