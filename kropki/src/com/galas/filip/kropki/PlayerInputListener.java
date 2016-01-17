package com.galas.filip.kropki;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.galas.filip.kropki.entity.Player;

public class PlayerInputListener implements KeyListener {

	public void keyPressed(KeyEvent e) {
		Player player = Player.getInstance();

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			player.setMoveUp();
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.setMoveDown();
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.setMoveRight();
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.setMoveLeft();
		}
	}

	public void keyReleased(KeyEvent e) {
		Player player = Player.getInstance();

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			player.unsetMoveUp();
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.unsetMoveDown();
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.unsetMoveRight();
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.unsetMoveLeft();
		}
	}

	public void keyTyped(KeyEvent e) {

	}

}
