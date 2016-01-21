package com.galas.filip.kropki.loading;

import com.galas.filip.kropki.Scene;
import com.galas.filip.kropki.exception.SceneLoadingException;

public interface SceneLoader {

	Scene getScene() throws SceneLoadingException;

}
