package view;

import java.util.Observable;

import view.figures.Figure;

import model.WorldCollection;
import model.WorldObject;

/**
 * This is a class that contains a view of the world
 * is observer at the model on updates there (new objects?)
 * 
 * @author Gustav
 * @version 2016-03-05
 */

@SuppressWarnings("serial")
public class WorldView
extends GameComponent
implements GameObserver
{

	//The world size
	protected int worldSize=800;
	
	
	/**
	 * Constructor that does nothing.
	 */
	public WorldView () {}

	
	/**
	 * Update function run by the observable (through notifyobservers).
	 * @param who	the observable that was updated
	 * @param what	what was updated. If it is an Double the world gets resized
	 */
	@Override
	public void update(Observable who, Object what) {
		//If it was a Double: resize.
		if (what instanceof Double) {
			worldSize = (int)what;
		}
		//If it was a worldObject: add it.
		if (what instanceof WorldObject) {
			addItem((WorldObject) what);
		}
	
	}


	/**
	 * Adds a worldCollection to the View. Only sets the size.
	 * @param world The WorldCollection from which the size is fetched
	 */
	@Override
	public void addWorld(WorldCollection world) {
		for (WorldObject thing : world.getCollection()) {
			addItem(thing);
		}
		worldSize=(int)world.getWorldSize();
	}
	
	/**
	 * Adds some item to the world
	 * @param what	The item to add
	 */
	protected void addItem (WorldObject what) {}

		
	/**
	 * Remove some item from the world. Called from the items themselves.
	 * @param who	The item to remove
	 */
	public void removeMe(Figure who) {}
	

}
