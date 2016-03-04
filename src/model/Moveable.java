package model;
import java.io.Serializable;
import java.util.ArrayList;

import util.*;

/**
 * Moveable class represents a generic floating object in space with mass which 
 * also is collidable with other Moveable objets.
 * 
 * @author Victor
 * @version 2016-03-04
 */

public class Moveable 
extends WorldObject 
implements Serializable
{
	
	/**
	 * Change this value if any change is made to any fields.
	 */
	private static final long serialVersionUID = 1L;
	
	protected Vector2D velocity;
	protected Vector2D velocity_diff = new Vector2D(0,0);
	private double collision_damping = 0.99;
	private double max_speed_sqr = 2500;
	
	/**
	 * Constructor for Moveable Class
	 * 
	 * @param world The WorldCollection of objects.
	 * @param velocity	The velocity vector.
	 * @param position	The position vector.
	 * @param mass		The mass value.
	 * @param radius	The radius value.
	 */
	public Moveable (WorldCollection world, Vector2D velocity, Vector2D position, double mass, double radius) {
		super(world, position, mass, radius);
		this.velocity = velocity;
		collision_damping = Double.parseDouble(Config.get("Collision_damping"));
		double max_speed = Double.parseDouble(Config.get("Max_speed"));
		max_speed_sqr = max_speed * max_speed;
	}
	
	
	/**
	 * Moves this object under given time and notify observers
	 * 
	 * @param dT - The time under which the object is moved
	 * @return void
	 */
	public void move(double dT){
		velocity = velocity.add(velocity_diff);	// V1 = V1 + A*dT
		if (velocity.lengthsquared() > max_speed_sqr)
			velocity = velocity.scale(0.9);
		velocity_diff = new Vector2D(0,0);
		position = position.add(velocity.scale(dT));		//P1=P1 + V * dT
		update();
	}
	
	
	/**
	 * Accelerates this object under given time, affected by given force.
	 * 
	 * @param force - The force which accerlates this object.
	 * @param dT - The time under which the object is accelerated.
	 */
	public void accelerate(Vector2D force, double dT) {
		velocity_diff = velocity_diff.add(force.div(mass).scale(dT));
	}
	
	
	/**
	 * Return this objects velocity vector.
	 * 
	 * @return velocity The velocity vector.
	 */
	public Vector2D getVelocity(){
		return velocity;
	}

	
	/**
	 * Help method that checks for collision between this Moveable object and the list of all Worldobjects.
	 * 
	 * @param data - The list of WorldObjects
	 * @return void
	 */
	public void collisions(ArrayList<WorldObject> data){
		for(WorldObject obj : data){
			if(!this.equals(obj) && collides(obj)){
				collision(obj);
			}
		}
	}
	
	
	/**
	 * Checks if this object collides with another WorldObject
	 * 
	 * @param other The other Worldobject
	 * @return boolean - Returns true/flase depending if collision happens or not.
	 */
	protected boolean collides(WorldObject other) {
		double lengthsqr = this.position.sub(other.getPosition()).lengthsquared();
		double radlength = this.getRadius() + other.getRadius();
		return (lengthsqr < radlength*radlength);
	}
	
	
	/**
	 * Calculates the collisionvector as an elastic collision between this object
	 * and the object it collides with and adds it to this objects velocity_diff.
	 * 
	 * @param obj - The object this object collides with. 
	 * @return void
	 */
	protected void collision(WorldObject obj){
		if (obj instanceof Moveable) 
		{	
			Vector2D v1 = this.getVelocity();
			Vector2D v2 = ((Moveable)obj).getVelocity();
			Vector2D p1 = this.getPosition();
			Vector2D p2 = ((Moveable)obj).getPosition();
			double m1 = this.getMass();
			double m2 = ((Moveable)obj).getMass();
			double massScalar = (2*m2)/(m1+m2);
			Vector2D p1minusp2 = p1.sub(p2);
			Vector2D v1minusv2 = v1.sub(v2);
			double dstsqr = p1minusp2.lengthsquared();
			double dot = -Math.abs(Vector2D.dot(v1minusv2, p1minusp2));
			double constant = (massScalar * dot)/dstsqr;
			Vector2D newV1 = p1minusp2.scale(constant*collision_damping);
			velocity_diff = velocity_diff.sub( newV1 );
			
		} else {
			double dot = velocity.dot(position.sub(obj.getPosition()));
			Vector2D dv = position.sub(obj.getPosition());
			dv = dv.scale(  dot/position.sub(obj.getPosition()).lengthsquared());
			velocity_diff = velocity_diff.sub( dv.scale(collision_damping*2) );
		}	
	}
	
	
	/**
	 * Calcuates the collisionvector with the bounderies of the world and adds it to this objects
	 * velocity_diff.
	 * 
	 * @return void
	 */
	public void wallCollide(){
		double r = theWorld.getWorldSize()/2-this.getRadius();
		if(position.lengthsquared() > r*r){
			Vector2D p_norm = position.normalize();
			double projection = velocity.dot(p_norm);
			if (projection > 0) //Only if we are heading outwards
			{
				p_norm = p_norm.scale(2*collision_damping*projection);
				velocity_diff = velocity_diff.sub(p_norm);
			}
		}
	}
	
}
