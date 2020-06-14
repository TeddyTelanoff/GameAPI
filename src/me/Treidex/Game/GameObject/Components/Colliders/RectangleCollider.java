package me.Treidex.Game.GameObject.Components.Colliders;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import me.Treidex.Game.Math.Mathf;
import me.Treidex.Game.Math.Vector2;

/**
 * The numero uno Collider
 * in Video Games.
 * 
 * @author Treidex
 *
 */
public class RectangleCollider extends Collider {
	
	/**
	 * Initialize the Rectangle Collider.
	 * 
	 * @param isTrigger Determines whether is a Trigger or not.
	 * @param margin The Margin in which to calculate from.
	 */
	public RectangleCollider(boolean isTrigger, float margin, ColliderEvent... colliderEvents) {
		super(isTrigger, margin, colliderEvents);
	}
	
	/**
	 * Draw The Wireframe of the Collider.
	 */
	public void draw(Graphics g) {
		g.setColor(new Color(50, 255, 14));
		g.drawRect((int) transform.position.x, (int) transform.position.y, (int) transform.size.x, (int) transform.size.y);
		
		g.setColor(new Color(255, 88, 14));
		g.drawRect((int) (transform.position.x + margin), (int) (transform.position.y + margin), (int) (transform.size.x - margin*2), (int) (transform.size.y - margin*2));
	}
	
	public void onDestroy() {
		super.destroy(Mathf.<Collider> getIndexFromArray(this, Collider.colliders));
	}
	
	/**
	 * Checks if the Rectangle is colliding with another collider.
	 */
	public final Object[] checkCollision() {
		float[] collisionMap = new float[] {
			0,
			0,
			0
		};
		
		ArrayList<Collider> collidersArray = new ArrayList<Collider>();
		
		for (Collider collider: Collider.colliders) {
			
			if (collider == this)
				continue;
			
			for (int y = 0; y < transform.size.y; y++) {
				for (int x = 0; x < transform.size.x; x++) {
					Object[] collision = collider.checkCollision(Vector2.add(transform.position, new Vector2(x, y)));
					
					Collider tempCollider = (Collider) collision[1];
					float[] tempCollisionMap = (float[]) collision[0];
					
					if (tempCollisionMap[0] != 0) {
						collisionMap[0] = tempCollisionMap[0];
						collidersArray.add(tempCollider);
					}
					
					if (tempCollisionMap[1] != 0)
						collisionMap[1] += tempCollisionMap[1];
					if (tempCollisionMap[2] != 0)
						collisionMap[2] += tempCollisionMap[2];
				}
			}
		}
		
		Collider[] colliders = new Collider[collidersArray.size()];
		colliders = collidersArray.<Collider> toArray(colliders);
		
		for (int i = 0; i < collisionMap.length; i++) {
			collisionMap[i] = Mathf.constrain(collisionMap[i], -1, 1);
		}
		
		if (collisionMap[0] == 1) {
			if (!pcollided()) {
				for (ColliderEvent colliderEvent : colliderEvents) {
					colliderEvent.onCollisionEnter(collisionMap, colliders);
				}
			}
			pcollided(true);
		} else {
			if (pcollided()) {
				for (ColliderEvent colliderEvent : colliderEvents) {
					colliderEvent.onCollisionExit();
				}
			}
			
			pcollided(false);
		}
		
		return new Object[] { collisionMap, colliders };
	}

	/**
	 * Checks Collision for one spot.
	 */
	public final Object[] checkCollision(Vector2 checkPos) {
		float[] collisionMap = new float[] {
			0, // Has Collided?
			0, // Horizontal Map
			0  // Vertical Map
		};
		
		if (
				checkPos.x >= transform.position.x && checkPos.x <= transform.position.x + margin &&
				checkPos.y >= transform.position.y && checkPos.y <= transform.position.y + transform.size.y
			) {
			collisionMap[0] = 1;
			
			collisionMap[1] = -1;
		}
		if (
				checkPos.x >= transform.position.x + transform.size.x - margin && checkPos.x <= transform.position.x + transform.size.x &&
				checkPos.y >= transform.position.y && checkPos.y <= transform.position.y + transform.size.y
			) {
			collisionMap[0] = 1;
			
			collisionMap[1] = 1;
		}
		
		if (
				checkPos.x >= transform.position.x && checkPos.x <= transform.position.x + transform.size.x &&
				checkPos.y >= transform.position.y && checkPos.y <= transform.position.y + margin
			) {
			collisionMap[0] = 1;
			
			collisionMap[2] = -1;
		}
		if (
				checkPos.x >= transform.position.x && checkPos.x <= transform.position.x + transform.size.x &&
				checkPos.y >= transform.position.y + transform.size.y - margin && checkPos.y <= transform.position.y + transform.size.y
			) {
			collisionMap[0] = 1;
			
			collisionMap[2] = 1;
		}
		
		return new Object[] { collisionMap, this };
	}
}