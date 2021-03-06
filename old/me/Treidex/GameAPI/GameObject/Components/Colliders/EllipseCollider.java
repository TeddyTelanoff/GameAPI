package me.Treidex.GameAPI.GameObject.Components.Colliders;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import me.Treidex.GameAPI.Debug;
import me.Treidex.GameAPI.Anotations.Unfinished;
import me.Treidex.GameAPI.GameObject.Components.Component;
import me.Treidex.GameAPI.Util.Math.Mathf;
import me.Treidex.GameAPI.Util.Math.Vector2;

/**
 * Ellipse's are cool,
 * and I'm cool;
 * so Ellipse's now have
 * Colliders!
 * 
 * @author Treidex
 *
 */
@Unfinished
public class EllipseCollider extends Collider {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6377154936926758976L;
	
	protected JSONObject ecMap;
	
	/**
	 * Create the
	 * Ellipse Collider.
	 * 
	 * @param isTrigger Determines whether is a Trigger or not.
	 * @param margin The Margin in which to calculate from.
	 * @param colliderEvents The Collider Events.
	 */
	public EllipseCollider(boolean isTrigger, float margin, ColliderEvent... colliderEvents) {
		super(isTrigger, margin, colliderEvents);
		
		initID("Collider->Ellipse");
	}
	
	/**
	 * Draw The Wireframe
	 * of the Collider.
	 */
	public void draw(Graphics2D g) {
		if (Debug.statements.contains("debug")) {
			g.setColor(new Color(50, 255, 14));
			g.drawOval((int) transform.position().x, (int) transform.position().y, (int) transform.size.x, (int) transform.size.y);
			
			g.setColor(new Color(255, 88, 14));
			g.drawOval((int) (transform.position().x + margin), (int) (transform.position().y + margin), (int) (transform.size.x - margin*2), (int) (transform.size.y - margin*2));
		}
	}
	
	public void onDestroy() {
		destroyCollider(Mathf.<Collider> getIndexFromArray(this, colliders));
	}

	/**
	 * Checks if the Ellipse is colliding with
	 * another collider.
	 */
	public final CollisionMap checkCollision() {
		float[] collisionMap = new float[] {
			0,
			0,
			0
		};
		
		ArrayList<Collider> collidersArray = new ArrayList<Collider>();
		
		for (Collider collider : Collider.colliders) {
			
			if (collider == this)
				continue;
			
			for (float ang = 0; ang < 360; ang++) {
				float X = (float) Math.sin(ang);
				float Y = (float) Math.cos(ang);
				
				for (float multX = -transform.size.x; multX < transform.size.x/2; multX++) {
					float x = X * multX;
					for (float multY = -transform.size.y; multY < transform.size.y/2; multY++) {
						float y = Y * multY;
						
						CollisionMap tempCollision = collider.checkCollision(Vector2.add(transform.position(), new Vector2(x, y)));
						
						Collider tempCollider = tempCollision.collision;
						float[] tempCollisionMap = tempCollision.collisionMapf;
						
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
		
		return new CollisionMap(collisionMap, colliders);
	}

	/**
	 * Checks Collision for one spot.
	 */
	public final CollisionMap checkCollision(Vector2 checkPos) {
		float[] collisionMap = new float[] {
			0, // Has Collided?
			0, // Horizontal Map
			0  // Vertical Map
		};
		
		if (Math.pow((checkPos.x - transform.position().x), 2) / Math.pow(transform.size.x, 2) + Math.pow((checkPos.y - transform.position().y), 2) / Math.pow(transform.size.y, 2) <= 1) {
			collisionMap[0] = 1;
			
			if (
					checkPos.x >= transform.position().x && checkPos.x <= transform.position().x + margin &&
					checkPos.y >= transform.position().y && checkPos.y <= transform.position().y + transform.size.y
				) {
				collisionMap[1] = -1;
			}
			if (
					checkPos.x >= transform.position().x + transform.size.x - margin && checkPos.x <= transform.position().x + transform.size.x &&
					checkPos.y >= transform.position().y && checkPos.y <= transform.position().y + transform.size.y
				) {
				collisionMap[1] = 1;
			}
			
			if (
					checkPos.x >= transform.position().x && checkPos.x <= transform.position().x + transform.size.x &&
					checkPos.y >= transform.position().y && checkPos.y <= transform.position().y + margin
				) {
				collisionMap[2] = -1;
			}
			if (
					checkPos.x >= transform.position().x && checkPos.x <= transform.position().x + transform.size.x &&
					checkPos.y >= transform.position().y + transform.size.y - margin && checkPos.y <= transform.position().y + transform.size.y
				) {
				collisionMap[2] = 1;
			}
		}
		
		if (isTrigger)
			return new CollisionMap(new float[] { collisionMap[0], 0, 0 }, this);
		else
			return new CollisionMap(collisionMap, this);
	}

	@SuppressWarnings("unchecked")
	public JSONObject getMap() {
		ecMap = new JSONObject();
		ecMap.put("is-trigger", isTrigger);
		ecMap.put("margin", margin);
		
		return ecMap;
	}
	
	public static Component loadMap(final JSONObject map) {
		return new EllipseCollider((Boolean) map.get("is-trigger"), (Float) map.get("margin"));
	}
}
