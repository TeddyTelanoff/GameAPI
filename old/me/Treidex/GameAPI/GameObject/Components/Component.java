package me.Treidex.GameAPI.GameObject.Components;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.Serializable;

import org.json.simple.JSONObject;

import me.Treidex.GameAPI.GameObject.GameObject;
import me.Treidex.GameAPI.Util.Math.Mathf;

/**
 * How do you make anything?
 * With Components!
 * 
 * This is the structure for every Component.
 * 
 * @author Treidex
 *
 */
public abstract class Component extends CMethods implements Serializable, CConstants {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Store the Transform of
	 * the Parent.
	 */
	protected Transform transform;
	
	private static String[] ids = new String[0];
	private static String ComponentID;
	
	/**
	 * Structure for Initialization of the Component.
	 */
	public void init() {}
	
	public void draw(Graphics2D g) {}
	
	/**
	 * Structure for Updating the Component.
	 */
	public void update() {}
	
	/**
	 * Structure for Fixed Updates of the Component.
	 */
	public void fixedUpdate() {}
	
	/**
	 * Structure for Late Updates of the Component.
	 */
	public void lateUpdate() {}
	
	/**
	 * Structure for Mouse Clicked Event
	 * 
	 * @param e Mouse Event
	 */
	public void mouseClicked(MouseEvent e) {}

	/**
	 * Structure for Mouse Press Event
	 * 
	 * @param e Mouse Event
	 */
	public void mousePressed(MouseEvent e) {}

	/**
	 * Structure for Mouse Release Event
	 * 
	 * @param e Mouse Event
	 */
	public void mouseReleased(MouseEvent e) {}

	/**
	 * Structure for Mouse Drag Event
	 * 
	 * @param e Mouse Event
	 */
	public void mouseDragged(MouseEvent e) {}

	/**
	 * Structure for Mouse Move Event
	 * 
	 * @param e Mouse Event
	 */
	public void mouseMoved(MouseEvent e) {}
	
	/**
	 * Structure for Key Pressing for the Component.
	 * 
	 * @param e Key Event.
	 */
	public void keyPressed(KeyEvent e) {}
	
	/**
	 * Structure for Key Releasing for the Component.
	 * 
	 * @param e Key Event.
	 */
	public void keyReleased(KeyEvent e) {}
	
	/**
	 * Function for Executing code when Component is Destroyed.
	 */
	public void onDestroy() {}
	
	protected final boolean hasComponent(Class<? extends Component> componentClass) { return parent.hasComponent(componentClass); }
	protected final <T extends Component> T getComponent(Class<T> componentClass) { return parent.<T> getComponent(componentClass); }
	
	/**
	 * Getter for the Parent Game Object.
	 * 
	 * @return The Parent GameObject.
	 */
	public final GameObject getParent() {
		return parent;
	}
	
	/**
	 * Used for the Game Object to set
	 * the Components Parent.
	 * 
	 * @param parent The Parent to be set.
	 */
	public void setParent(GameObject parent) {
		this.parent = parent;
		
		transform = parent.transform;
	}
	
	protected static final void initID(final String id) {
		ComponentID = id;
		if (!Mathf.<String> hasInArray(ids, ComponentID))
			ids = Mathf.<String> addToArray(String.class, ids, ComponentID);
	}
	
	public static final String getID() {
		if (ComponentID == null)
			throw new IllegalStateException("Component Has No Component ID!");
		
		return ComponentID;
	}
	
	public static final String[] getIDs() {
		return ids.clone();
	}
	
//	public abstract JSONObject getMap();
	
	public static Component loadMap(final JSONObject map) {
		throw new IllegalStateException("'loadMap()' must be define in Sub-Class: " + ComponentID);
	}
	
	public String toString() {
		return "Component";
	}
}
