package com.galas.filip.kropki;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Properties;

import com.galas.filip.kropki.entity.Player;
import com.galas.filip.kropki.exception.SceneLoadingException;

public class GameController implements GameEventListener {

	private GameFrame frame;
	private Scene currentScene;
	private UpdateThread updateThread;

	private Properties config;

	public GameController(Properties config) {
		this.config = config;
		setupGame();
	}

	private void setupGame() {

		createGameFrame();
		centerGameFrameOnScreen();
		setGameFrameBehaviour();

		createPlayer();

		try {
			setStartSceneAsCurrentScene();
		} catch (SceneLoadingException e) {
			throw new RuntimeException("cannot load the start scene", e);
		}

		frame.addKeyListener(new PlayerInputListener());

		frame.setVisible(true);
	}

	private void createGameFrame() {
		frame = new GameFrame("Kropki (beta)", Integer.valueOf(config.getProperty(ConfigurationModel.FRAME_WIDTH)),
				Integer.valueOf(config.getProperty(ConfigurationModel.FRAME_HEIGHT)));
	}

	private void centerGameFrameOnScreen() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point frameLocation = new Point((screenSize.width - frame.getWidth()) / 2,
				(screenSize.height - frame.getHeight()) / 2);
		frame.setLocation(frameLocation);
	}

	private void setGameFrameBehaviour() {
		frame.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

			public void windowDeiconified(WindowEvent e) {
				resume();
			}

			public void windowIconified(WindowEvent e) {
				pause();
			}

		});
		frame.setResizable(false);
	}

	private void createPlayer() {
		Player.createInstance(new Point(), Integer.valueOf(config.getProperty(ConfigurationModel.PLAYER_DOT_SIZE)),
				Integer.valueOf(config.getProperty(ConfigurationModel.PLAYER_SPEED)),
				ParsingUtil.parseColor(config.getProperty(ConfigurationModel.PLAYER_COLOR)));
	}

	private void setStartSceneAsCurrentScene() throws SceneLoadingException {
		String startSceneName = config.getProperty(ConfigurationModel.START_SCENE_NAME);
		Color defaultBackgroundColor = ParsingUtil
				.parseColor(config.getProperty(ConfigurationModel.DEFAULT_BACKROUND_COLOR));

		Scene startScene = loadScene(startSceneName, defaultBackgroundColor);
		setSceneAsCurrent(startScene);
	}

	private Scene loadScene(String sceneName, Color defaultBackgroundColor) throws SceneLoadingException {
		SceneLoader sceneLoader = SceneLoaderFactory.getSceneLoader(sceneName);
		Scene scene = sceneLoader.getScene();
		if (scene.getBackgroundColor() == null) {
			scene.setBackgroundColor(defaultBackgroundColor);
		}

		return scene;
	}

	private void setSceneAsCurrent(Scene scene) {
		Player player = Player.getInstance();
		player.setPosition(scene.getStartPosition());
		scene.addEntityAsFirstInLayer(player, true, 0);

		currentScene = scene;
	}

	public void pause() {
		if (updateThread != null) {
			updateThread.setRunning(false);
		}
		updateThread = null;
	}

	public void resume() {
		if (updateThread == null) {
			updateThread = new UpdateThread(currentScene.getEntities(), frame, config);
			updateThread.addGameEventListener(this);
			updateThread.start();
		}
	}

	public void onGameEvent(GameEvent e) {
		try {
			Color defaultBackgroundColor = ParsingUtil
					.parseColor(config.getProperty(ConfigurationModel.DEFAULT_BACKROUND_COLOR));

			if (e.getEventType() == GameEvent.EventType.LINK) {
				if (e.getTargetSceneName() != null) {
					setSceneAsCurrent(loadScene(e.getTargetSceneName(), defaultBackgroundColor));
					pause();
					resume();
				} else {
					// a null link means quit the game
					System.exit(0);
				}
			} else if (e.getEventType() == GameEvent.EventType.LOST) {
				String gameOverSceneName = config.getProperty(ConfigurationModel.GAME_OVER_SCENE_NAME);
				setSceneAsCurrent(loadScene(gameOverSceneName, defaultBackgroundColor));
				pause();
				resume();
			}
		} catch (SceneLoadingException ex) {
			throw new RuntimeException(ex);
		}
	}
}
