package objects;

import util.Vector2D;
import model.Moveable;

/**
 * An moveable object...
 * @author Gustav
 *
 */

public class SnakePart
extends Moveable 
{
	private SnakePart nextPart;
	private double stiffness = 1;
	private double linkLength = 100;

	
	/**
	 * Constructor with doubles
	 */
	public SnakePart(double xSpeed, double ySpeed, double xPos, double yPos,
			double mass, double radius) {
		super(xSpeed, ySpeed, xPos, yPos, mass, radius);
	}
	
	
	/**
	 * Constructor with vectors
	 */
	public SnakePart(Vector2D velocity, Vector2D position, double mass, double radius) {
		super(velocity, position, mass, radius);
	}
	
	
	/**
	 * Calculates an accelerates this and the next part depending on a spring constant
	 */
	public void pullAtNext () {
		if (nextPart != null) {
			this.accelerate( this.position.sub( nextPart.getPosition() ).scale( (this.position.sub( nextPart.getPosition() ).length() - linkLength) * stiffness );
			nextPart.accelerate( nextPart.getPosition().sub( this.position ).scale( (this.position.sub( nextPart.getPosition() ).length() - linkLength) * stiffness );
		}
	}
	
	
	/**
	 * Adds a new part at the end of this.
	 * @param tail	The part that should get added
	 * @return	true if the tail was added, false if it already has a tail
	 */
	public boolean addTail (SnakePart tail) {
		if (nextPart == null) return false;
		nextPart = tail;
		return true;
	}
	
	
	/**
	 * Checks if this part has a tail.
	 * @return true if it has a tail, false otherwise
	 */
	public boolean checkTail () {
		if (nextPart == null) return false;
		return true;
	}

}
