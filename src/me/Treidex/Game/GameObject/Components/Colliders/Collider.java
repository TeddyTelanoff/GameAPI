package me.Treidex.Game.GameObject.Components.Colliders;

import me.Treidex.Game.GameObject.Components.Component;
import me.Treidex.Game.Math.Mathf;
import me.Treidex.Game.Math.Vector2;

/**
 * Colliders!
 * Without them,
 * you'd go right through
 * your best friend.
 * 
 * @author Treidex
 *
 */
public abstract class Collider extends Component {
	
	/**
	 * List of all Colliders.
	 */
	public static Collider[] colliders = new Collider[0];
	
	/**
	 * Boolean to see if the
	 * Collider is a Trigger.
	 */
	public boolean isTrigger;
	
	/**
	 * Checking Margin;
	 * used to save computing
	 * Power.
	 */
	public float margin;
	
	/**
	 * (!UN-USED!)
	 * Used to determine
	 * when to trigger
	 * onCollisionEnter (UN-USED) and
	 * onCollisionExit (UN-USED).
	 */
	private boolean pcollided;
	
	/**
	 * Function structure.
	 * All Colliders must have
	 * this.
	 * 
	 * @return Collision Map (Tells Physics Component how to move).
	 */
	public abstract float[] checkCollision();
	
	/**
	 * Function structure.
	 * Used for checking individual
	 * spots of Collider.
	 * 
	 * @param checkPos The Position in which to Check.
	 * @return Collision Map.
	 * @see me.Treidex.Game.GameObject.Components.Colliders.Collider#checkCollision() Check Collision().
	 */
	public abstract float[] checkCollision(Vector2 checkPos);
	
	protected ColliderEvent[] colliderEvents;
	
	/**
	 * Initialize the Collider.
	 * 
	 * @param isTrigger Determines whether the Collider is a Trigger.
	 * @param margin The Margin in which to check Collision.
	 */
	public Collider(boolean isTrigger, float margin, ColliderEvent[] colliderEvents) {
		this.isTrigger = isTrigger;
		this.margin = margin;
		this.colliderEvents = colliderEvents;
		
		colliders = Mathf.<Collider> addToArray(Collider.class, colliders, this);
		
		for (Collider collider : colliders)
		System.out.println(collider);
		System.out.println(colliders.length);
	}
	
	public void destroy(int index) {
		colliders = Mathf.<Collider> removeFromArray(Collider.class, colliders, index);
	}
	
	/**
	 * Getter for pcollided.
	 * @see me.Treidex.Game.GameObject.Components.Colliders.Collider#pcollided P Collided.
	 * 
	 * @return pcollided The Value for pcollided.
	 */
	public boolean pcollided() {
		return pcollided;
	}
	
	/**
	 * Setter for pcollided.
	 * @see me.Treidex.Game.GameObject.Components.Colliders.Collider#pcollided P Collided.
	 * 
	 * @param pcollided Whether Collider Collided before.
	 */
	public void pcollided(boolean pcollided) {
		this.pcollided = pcollided;
	}
}