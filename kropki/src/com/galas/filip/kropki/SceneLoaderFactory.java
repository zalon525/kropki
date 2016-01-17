package com.galas.filip.kropki;

import java.io.File;

import com.galas.filip.kropki.exception.UnsupportedSceneFormatException;

public class SceneLoaderFactory {

	private SceneLoaderFactory() {

	}

	public static SceneLoader getSceneLoader(String sceneURL) throws UnsupportedSceneFormatException {

		if (sceneURL.endsWith(".xml")) {
			return new XMLSceneLoader(new File(sceneURL));
		} else {
			throw new UnsupportedSceneFormatException();
		}
	}
}
