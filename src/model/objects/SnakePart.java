package model.objects;

import java.io.Serializable;

import util.Config;
import util.Vector2D;
import model.Moveable;
import model.WorldCollection;
import model.WorldObject;

/**
 * An moveable object...
 * @author Gustav
 *
 */

public class SnakePart
extends Moveable 
implements Serializable 
{
	
	/**
	 * Change this value if any change is made to any fields.
	 */
	private static final long serialVersionUID = 1L;
	
	protected SnakePart nextPart;
	private double stiffness = 10;
	private double linkLength = 40;
	private double damping = 0.9;	
	
	/**
	 * Constructor with vectors
	 */
	public SnakePart(WorldCollection world, Vector2D velocity, Vector2D position, double mass, double radius) {
		super(world, velocity, position, mass, radius);
		stiffness = Double.parseDouble(Config.get("Snake_link_stiffness"));
		damping = Double.parseDouble(Config.get("Snake_link_damping"));
	}
	
	
	/**
	 * Calculates an accelerates this and the next part depending on a spring constant
	 */
	public void pullAtNext (double dT) {
		if (nextPart != null) {
			
			//Elastic pull/push
			Vector2D distance = this.position.sub( nextPart.getPosition() );
			double x = distance.length() - linkLength;
			this.accelerate( distance.normalize().scale(-x*stiffness), dT );
			nextPart.accelerate( distance.normalize().scale(x*stiffness), dT );
			
			//Damping
			Vector2D v = this.velocity.sub(nextPart.getVelocity());
			this.accelerate(v.scale(-damping), dT);
			nextPart.accelerate(v.scale(damping), dT);
			
		}
	}
	
	
	/**
	 * Adds a new part at the end of this.
	 * @param tail	The part that should get added
	 * @return	true if the tail was added, false if it already has a tail
	 */
	public boolean addTail (SnakePart tail) {
		if (nextPart != null) return false;
		nextPart = tail;
		linkLength = this.getRadius() + tail.getRadius();
		return true;
	}
	
	
	/**
	 * Removes the tail (in case it died)
	 */
	public void removeTail () {
		nextPart = null;
	}
	
	
	/**
	 * Returns the tail
	 * @return the tail
	 */
	public SnakePart getTail () {
		return nextPart;
	}


	/**
	 * Overrides function in Moveable to only make collisions happen if it is not the next neighbor.
	 */
	@Override
	public void collision (WorldObject obj) {
		if (!isNeighbor(obj)) 
			super.collision(obj);
	}
	
	
	/**
	 * Checks if a obj is the neighbor of this one.
	 * @param obj the other part that gets checked
	 * @return	true if it is just in front of or just behind
	 */
	protected boolean isNeighbor (WorldObject obj) {
		if (obj instanceof SnakePart)
		{
			if (((SnakePart) obj).nextPart != null && ((SnakePart) obj).nextPart.equals(this))
				return true;
			if (nextPart !=null && obj.equals(nextPart))
				return true;
		}
		return false;
	}
}
