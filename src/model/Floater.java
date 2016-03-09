package model;
import java.io.Serializable;
import java.util.ArrayList;

import util.*;
/**
 * Floater class represents a generic floating object in space
 * having both gravity an velocity.
 * 
 * @author Victor, Joakim 
 * @version 2016-03-04
 */
public class Floater extends Moveable implements IGravity, Serializable{
	
	/**
	 * Change this value if any change is made to any fields.
	 */
	private static final long serialVersionUID = 1L;
	private double gravity=12;
	

	/**
	 * 
	 * @param world 	The WorldCollection of objects.
	 * @param velocity	The velocity vector.
	 * @param position	The position vector.
	 * @param mass		The mass value.
	 * @param radius	The radius value.
	 */
	public Floater(WorldCollection world, Vector2D velocity, Vector2D position, double mass, double radius){
		super(world, velocity, position, mass, radius);
		gravity=Double.parseDouble(Config.get("Gravity_constant"));
	}
	
	
	/**
	 * Adds effect of gravitationalforce from other objects to this object over given time.
	 * 
	 * @param data The list of WorldObjects.
	 * @param dT The time under which force accelerates this object.
	 */
	public void gravityPull(ArrayList<WorldObject> data, double dT) {
		for(WorldObject obj : data){
			if(!obj.equals(this) && obj instanceof Moveable)
				((Moveable)obj).accelerate(this.calcuateGravity(obj), dT);
		}
	}
	
	
	/**
	 * Calculates the scaled gravitational vector between this WorldObjects and the specified WorldObject
	 * 
	 * @param arg The other WorldObject
	 * @return temp The calcuated gravitational force vector between the two objects.
	 */
	public Vector2D calcuateGravity(WorldObject arg) {
		Vector2D temp = Vector2D.diff(this.getPosition(), arg.getPosition());
		double r = radius + arg.getRadius();
		if (r*r>temp.lengthsquared())
			return new Vector2D(0,0);
		temp = temp.normalize();
		temp = temp.scale(mass*gravity*arg.getMass());
		temp = temp.div(this.getPosition().distancesquared(arg.getPosition()));
		return temp;
	}
	
	
	/**
	 * Returns this objects Gravity.
	 * 
	 * @return gravity The gravity value.
	 */
	public double getGravity() {
		return gravity;
	}
	
	
	/**
	 * Return this objects velocity vector.
	 * 
	 * @return velocity The velocity vector.
	 */
	public Vector2D getVelocity(){
		return velocity;
	}
}