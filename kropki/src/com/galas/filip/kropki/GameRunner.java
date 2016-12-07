package com.galas.filip.kropki;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GameRunner {

	private static final String DEFAULT_CONFIG_URL = "config.properties";
	private static final String PROPERTY__KROPKI_GAME_CONFIG_URL = "kropki.game.config.url";

	public static void main(String args[]) {

		String configURL = System.getProperty(PROPERTY__KROPKI_GAME_CONFIG_URL);
		if (configURL == null) {
			configURL = DEFAULT_CONFIG_URL;
		}

		Properties config = new Properties();
		try {
			config.load(new FileInputStream(configURL));
		} catch (IOException e) {
			throw new RuntimeException("an error occured during configuration loading", e);
		}

		GameController gameController = new GameController(config);
		gameController.resume();
	}
}
