package com.galas.filip.kropki;

import com.galas.filip.kropki.exception.SceneLoadingException;

public interface SceneLoader {

	Scene getScene() throws SceneLoadingException;

}
