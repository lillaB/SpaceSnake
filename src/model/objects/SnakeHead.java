package model.objects;

import java.io.Serializable;

import model.WorldCollection;
import model.WorldObject;
import util.Config;
import util.Vector2D;

/**
 * An moveable object...
 * @author Gustav
 *
 */

public class SnakeHead 
extends SnakePart
implements Serializable 
{

	/**
	 * Change this value if any change is made to any fields.
	 */
	private static final long serialVersionUID = 1L;
	
	private double new_Tail_radius;
	private double new_Tail_mass;
	
	public SnakeHead(WorldCollection world, Vector2D velocity, Vector2D position, double mass, double radius) {
		super(world, velocity, position, mass, radius);
		this.theWorld = world;
		new_Tail_radius = Double.parseDouble(Config.get("new_Tail_radius"));
		new_Tail_mass = Double.parseDouble(Config.get("new_Tail_mass"));
	}
	
	
	/**
	 * Overrides function in SnakePart to make fun stuff happen
	 */
	@Override
	public void collision (WorldObject obj) {
		if (obj instanceof Edible) {
			eat((Edible)obj);
		} else if (obj instanceof BlackHole){
			 die();
		} else {
			super.collision(obj);
		}
	}
	
	private void die() {
		theWorld.delete(this);
	}
	
	private void eat(Edible what)
	{
		//Find the last and second last part of the snake
		SnakePart last = this;
		SnakePart second_last = this;
		while (last.getTail() != null)
		{
			second_last = last;
			last = last.getTail();
		}
		
		//Build a tailpart
		SnakeTail tail;
		if (last.equals(this))
			tail = new SnakeTail(theWorld, this.getVelocity(), 
					this.getPosition().sub(this.getVelocity().normalize().scale(this.getRadius()+new_Tail_radius)),
					new_Tail_mass,new_Tail_radius);
		else
			tail = new SnakeTail(theWorld, 
					last.getVelocity(), 
					last.getPosition().add(last.getPosition().sub(second_last.getPosition()).normalize().scale(last.getRadius()+new_Tail_radius)),  
					new_Tail_mass,new_Tail_radius);
		
		// Add the tailpart
		last.addTail(tail);
		theWorld.add(tail);
		
		//kill the edible object
		what.kill();
		
		//Get points?
		//other actions?
	}

	
}
