package model;
import java.io.Serializable;
import java.util.Observable;

import util.*;

/**
 * WorldObject class represents any object contained within the WorldCollection class.
 * 
 * @author Victor, Joakim
 * @version 2016-03-04
 */
public abstract class WorldObject 
extends Observable 
implements Serializable
{
	
	/**
	 * Change this value if any change is made to any fields.
	 */
	private static final long serialVersionUID = 1L;
	protected Vector2D position;
	protected double radius;
	protected double mass;
	protected WorldCollection theWorld;
	
	/**
	 * Constructor for WorldObject Class.
	 * 
	 * @param world	The WorldCollection of objects.
	 * @param position	The position vector.
	 * @param mass		The mass value.
	 * @param radius	The radius value.
	 */
	public WorldObject(WorldCollection world, Vector2D position, double mass, double radius) {
		this.position = position;
		this.radius = radius;
		this.mass = mass;
		theWorld = world;
	}
	
	
	/**
	 * Return this objects mass.
	 * 
	 * @return mass - The mass value.
	 */
	public double getMass(){
		return mass;
	}
	
	
	/**
	 * Return the position vector of this object.
	 * 
	 * @return position - The position vector.
	 */
	public Vector2D getPosition(){
		return position;
	}
	
	
	/**
	 * Return this objects radius.
	 * 
	 * @return radius - The radius value.
	 */
	public double getRadius(){
		return radius;
	}
	
	
	/**
	 * Return this objects diameter.
	 * 
	 * @return diameter - The diameter value. 
	 */
	public double getDiameter(){
		return radius*2;
	}
	
	
	/**
	 * Sets this object as changed and notify it's observers with the new position.
	 * 
	 */
	public void update(){
		setChanged();
		notifyObservers(position);
	}
	
	
	/**
	 * Sets this object as changed and notify it's observers with the dead message.
	 * 
	 */
	public void kill(){
		theWorld.delete(this);
		setChanged();
		notifyObservers("Died");
	}
}
