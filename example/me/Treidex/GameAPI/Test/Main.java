package me.Treidex.GameAPI.Test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.Treidex.GameAPI.GameManager;
import me.Treidex.GameAPI.Program;
import me.Treidex.GameAPI.GameObject.GameObject;
import me.Treidex.GameAPI.GameObject.Components.Component;
import me.Treidex.GameAPI.GameObject.Components.Follow;
import me.Treidex.GameAPI.GameObject.Components.Physics;
import me.Treidex.GameAPI.GameObject.Components.PlayerController;
import me.Treidex.GameAPI.GameObject.Components.SpriteRenderer;
import me.Treidex.GameAPI.GameObject.Components.Transform;
import me.Treidex.GameAPI.GameObject.Components.Colliders.ColliderType;
import me.Treidex.GameAPI.GameObject.Components.Colliders.RectangleCollider;
import me.Treidex.GameAPI.GameObject.Components.UI.Text;
import me.Treidex.GameAPI.Scene.Scene;
import me.Treidex.GameAPI.Util.Vector2;

public final class Main {
	private static final int width = 1000;
	private static final int height = 800;
	
	private static final String PlayerName = "Treidex";
	
	private static Scene scene;
	private static GameManager gameManager;
	private static Program program;
	
	private static GameObject player;
	
	public static void main(String[] args) {
		scene = new Scene() {
			public void init() {
				player = new GameObject(
					"Player",
					new Transform(
						new Vector2(0, 0),
						new Vector2(50, 100),
						0
					),
					new Component[] {
						new Follow(
							0,
							Vector2.zero,
							width,
							height
						),
						new SpriteRenderer(
							"gui/icon.png"
						),
						new RectangleCollider(
							false,
							2
						),
						new Physics(
							ColliderType.Rectangle,
							new Vector2(0, 520),
							0f,
							0f
						),
						new PlayerController(
							1000,
							69,
							false
						)
					},
					new GameObject[] {
						new GameObject(
							"Text",
							new Transform(
								new Vector2(0, 0),
								new Vector2((float) (PlayerName.length()) * 6, 20),
								0
							),
							new Text(
								PlayerName,
								Color.CYAN,
								new Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 20)
							)
						)
					}
				);
				
				name = "Scene 1";
				
				noTranslateObjects = new GameObject[] {};
				staticObjects = new GameObject[] {
					new GameObject(
						"Ground 1",
						new Transform(
							new Vector2(500, 700),
							new Vector2(1000, 50),
							0
						),
						new SpriteRenderer(
							"gui/Menu.png"
						),
						new RectangleCollider(
							false,
							2
						)
					),
					new GameObject(
						"Ground 2",
						new Transform(
							new Vector2(1500, 600),
							new Vector2(1000, 50),
							0
						),
						new SpriteRenderer(
							"gui/Menu.png"
						),
						new RectangleCollider(
							false,
							2
						)
					),
					new GameObject(
						"Ground 3",
						new Transform(
							new Vector2(-500, 900),
							new Vector2(1000, 50),
							0
						),
						new SpriteRenderer(
							"gui/Menu.png"
						),
						new RectangleCollider(
							false,
							2
						)
					),
					new GameObject(
						"Ground 1",
						new Transform(
							new Vector2(500, 700),
							new Vector2(1000, 50),
							0
						),
						new SpriteRenderer(
							"gui/Menu.png"
						),
						new RectangleCollider(
							false,
							2
						)
					),
					
					new GameObject(
						"Marker",
						new Transform(
							new Vector2(900, 600),
							new Vector2(1, 1),
							0
						),
						new SpriteRenderer(
							"gui/KIU.png"
						)
					)
				};
				gameObjects = new GameObject[] {
					player
				};
				
				super.init();
			}
			
			public void draw(Graphics g) {
				g.setColor(Color.DARK_GRAY);
				g.fillRect(0, 0, width, height);
				
				super.draw(g);
			}
			
			public void fixedUpdate() {
				if (player.transform.position().y >= 1000) {
					gameManager.changeScene(scene);
					System.out.println(player.transform.position().y);
				}
				
				super.fixedUpdate();
			}
		};
		
		gameManager = new GameManager(scene);
		program = new Program(gameManager, "Testing the Game Engine - Treidex", 420, 60, width, height);
		program.start();
	}

}
