package model.objects;

import java.io.Serializable;

import model.WorldCollection;
import model.WorldObject;
import util.Vector2D;

/**
 * An moveable object...
 * @author Gustav
 *
 */

public class SnakeTail 
extends SnakePart
implements Serializable
{

	/**
	 * Change this value if any change is made to any fields.
	 */
	private static final long serialVersionUID = 1L;
	
	public SnakeTail(WorldCollection world, Vector2D velocity, Vector2D position, double mass, double radius) {
		super(world, velocity, position, mass, radius);
	}
	
	/**
	 * Overrides function in SnakePart to only make collisions happen if the controller lets it
	 */
	@Override
	public void collision (WorldObject obj) {
		if (obj instanceof BlackHole){
			 die();
		} else {
			super.collision(obj);
		}
	}
	private void die() {
		SnakePart next = nextPart;
		while (next != null)
		{
			next.kill();
			theWorld.remove(next);
			next=next.getTail();
		}
		this.kill();
		theWorld.remove(this);
		for (WorldObject item : theWorld.getCollection()) {
			if (item instanceof SnakePart && ((SnakePart)item).getTail() != null && ((SnakePart)item).getTail().equals(this))
				((SnakePart)item).removeTail();
		}
		//set previous parts tail to null.
		
	}
}
