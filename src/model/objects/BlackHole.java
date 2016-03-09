package model.objects;

import java.io.Serializable;
import java.util.ArrayList;

import model.IGravity;
import model.Moveable;
import model.WorldCollection;
import model.WorldObject;
import util.Config;
import util.Vector2D;

/**
 * An non movable object with gravity
 * @author Gustav
 * @version 2016-03-04
 */

public class BlackHole
extends WorldObject
implements IGravity, Serializable
{

	/**
	 * Change this value if any change is made to any fields.
	 */
	private static final long serialVersionUID = 1L;
	
	private double gravity = 18; //the value gets overriden by config
	
	/**
	 * Constructor that generates the Black Hole.
	 * @param world		The worldCollection in which it resides
	 * @param position	Its initial position
	 * @param mass		Its mass
	 * @param radius	Its radius
	 */
	public BlackHole(WorldCollection world, Vector2D position, double mass, double radius) {
		super(world, position, mass, radius);
		gravity=Double.parseDouble(Config.get("Gravity_constant"));
	}

	/**
	 * Returns the gravity constant for the object.
	 * @return	The gravity constant
	 */
	@Override
	public double getGravity() {
		return gravity;
	}

	/**
	 * Calculate the gravity related acceleration and applies it to all Moveablaobjects in the list of objects.
	 * @param data	An ArrayList of WorldObjects that gets accelerated (if they are Moveable)
	 * @param dT	the length of time during which the acceleration should last
	 */
	@Override
	public void gravityPull(ArrayList<WorldObject> data, double dT) {
		for(WorldObject obj : data){
			if(obj instanceof Moveable && !obj.equals(this))
			{
				Vector2D distance = position.sub(obj.getPosition());
				double r = radius + obj.getRadius();
				if (r*r<distance.lengthsquared())
					((Moveable)obj).accelerate(distance.normalize().scale(gravity*mass*obj.getMass()/distance.lengthsquared()), dT);
			}
		}
	}


}
