package model;

import java.util.ArrayList;

/**
 * Interface with methods representing generic gravitational functionality.
 * @author Victor
 * @version 2016-03-04
 *
 */
public interface IGravity {
	
	/**
	 * Return the gravity value.
	 * @return gravity The gravity value.
	 */
	public double getGravity();
	
	
	/**
	 * Adds effect of gravitationalforce from other objects to this object over given time.
	 * 
	 * @param data The list of WorldObjects.
	 * @param dT The time under which force accelerates this object.
	 * 
	 */
	public void gravityPull(ArrayList<WorldObject> data, double dT);
}
