package model.objects;

import java.io.Serializable;

import model.Moveable;
import model.WorldCollection;
import model.WorldObject;
import util.Vector2D;

/**
 * An moveable object...
 * @author Gustav
 *
 */

public class Edible 
extends Moveable
implements Serializable
{

	/**
	 * Change this value if any change is made to any fields.
	 */
	private static final long serialVersionUID = 1L;
	
	public Edible(WorldCollection world, Vector2D velocity, Vector2D position, double mass, double radius) {
		super(world, velocity, position, mass, radius);
	}
	
	/**
	 * Overrides function in Moveable to make it die when it hits a black hole
	 */
	@Override
	public void collision (WorldObject obj) {
		if (obj instanceof BlackHole){
			 kill();
		} else {
			super.collision(obj);
		}
	}
}
