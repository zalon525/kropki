package com.galas.filip.kropki;

import com.galas.filip.kropki.exception.UnsupportedSceneFormatException;

public class SceneLoaderFactory {

	private SceneLoaderFactory() {

	}

	public static SceneLoader getSceneLoader(String sceneName) throws UnsupportedSceneFormatException {

		if (sceneName.endsWith(".xml")) {
			return new XMLSceneLoader(SceneLoaderFactory.class.getResourceAsStream(sceneName));
		} else {
			throw new UnsupportedSceneFormatException();
		}
	}
}
