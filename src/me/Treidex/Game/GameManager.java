package me.Treidex.Game;

import me.Treidex.Game.SuperClasses.Scene;

/**
 * Imagine the world
 * if everyone just did
 * whatever, with no organization.
 * Crazy, right?
 * That is why Managers exist.
 * This is the Manager for your game.
 * 
 * @author Treidex
 *
 */
public final class GameManager {
	
	/**
	 * The Active Scene.
	 */
	public Scene scene;
	
	/**
	 * The Canvas the Game is ran on.
	 */
	@SuppressWarnings("unused")
	private Game runner;

	/**
	 * Initialize the Game Manage
	 * 
	 * @param scenes All the Scenes to be loaded.
	 */
	public GameManager(Scene scene) {
		changeScene(scene);
	}
	
	public void changeScene(Scene newScene) {
		scene = newScene;
		
		scene.init();
	}
	
	/**
	 * Set the Runner of the Game Manager.
	 * 
	 * @param runner The Canvas the Game is ran on.
	 */
	public void setRunner(Game runner) {
		this.runner = runner;
	}
}