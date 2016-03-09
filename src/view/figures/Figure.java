package view.figures;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import util.Vector2D;
import view.WorldView;

/**
 * Main class for figures
 *  
 * @author Gustav
 * @version 2016-03-05
 */

@SuppressWarnings("serial")
public class Figure 
extends JComponent
implements Observer
{

	protected WorldView parent;
	protected Vector2D position;
	protected double size;
	
	/**
	 * Constructor that takes a position and a size and generates a Figure.
	 * @param position	The initial position
	 * @param size		The size of the Figure
	 * @param parent	The containing WorldView
	 */
    public Figure(WorldView parent, Vector2D position, Double size)
    {
    	this.parent = parent;
        this.position = position;
        this.size = size;
    }
    
    
    
    /**
     * Used when an update has happened. Can handle new positions, sizes and if the object died.
     * @param who		The observable that was updated
     * @param what		What that was updated, either a Vector2D if it is a new position, a Double if it is a new size
     * 					or a String containing "Died" if it died.
     */
	@Override
	public void update(Observable who, Object what) {
		
		//If it was a vector: move there.
		if (what instanceof Vector2D) {
			final Vector2D position = new Vector2D((Vector2D) what);
			SwingUtilities.invokeLater(new Runnable() {
			    public void run() { setPosition(position); }	    
			});
		}
		
		//If it was a double: use it as size
		if (what instanceof Double) {
			final double size = (double) what;
			SwingUtilities.invokeLater(new Runnable() {
			    public void run() { resize(size); }	    
			});
		}
		
		// if (died) parent.removeItem(this);
		if (what instanceof String) {
			if (((String)what).equals("Died"))
				//Die!
				parent.removeMe(this);
		}
	}
	
	
	/**
	 * Moves the figure to a new position
	 * @param position	the new position
	 */
	public void setPosition (Vector2D position) {
		this.position = position;
	}
	
	
	/**
     * Set a new size for the figure
     * @param new_size	The new size
     */
    public void resize (double new_size) {
    	size = new_size;
	}
    
}
