package view;

import java.util.Observer;

import model.WorldCollection;

/**
 * This is an interface used by the controller to add an entire world to the view.
 * Used whenever the entire world is loaded or changed.
 * @author Gustav
 * @version 2016-03-04
 */


public interface GameObserver extends Observer
{

	/**
	 * Adds an entire world with its size and all content to the observer.
	 * @param worldCollection
	 */
	public void addWorld(WorldCollection worldCollection);
	
}
