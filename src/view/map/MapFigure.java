package view.map;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;
import javax.swing.SwingUtilities;

import util.Vector2D;

/**
 * The main class for figures in the game view. Should be subclassed for all different kinds of figures.
 * Is an observer for events like a new figure added.
 * Is an ActionListener to be able to listen to itself.
 *  
 * @author Gustav
 * @version 2016-02-05
 */

public class MapFigure 
implements Observer
{

	protected double size;
	protected Vector2D position;

	protected Color color = new Color(255,0,0);;
	
	//Extra buffer when determining bounds
	int extra=1;
	
	//Needed to repaint the containing panel and to delete itself
	MapView parent;
	
	
	/**
	 * Constructor that takes a position (x,y) and a size and generates a GameFigure.
	 *
	 * @param parent	The containing GameView
	 * @param position	The position vector.
	 * @param size		The size of the GameFigure.S
	 * @param color		The color.
	 */
    public MapFigure(MapView parent, Vector2D position, double size, Color color)
    {
    	this.parent = parent;
        this.position = position;
        this.size=size;
        this.color = color;
    }
	
    
    /**
     * Set a new size for the figure
     * @param new_size	The new size
     */
    private void resize (double new_size) {
    	size = new_size;
	}
    
    /**
     * Returns the color of itself to be used in the map.
     */
    public Color getColor() {
    	return color;
    }
    public void setColor(Color c) {
    	color = c;
    }
    
    /**
     * Paints itself.
     * @param g
     */
    public void paintComponent(Graphics g) {
    	g.setColor(color);
    	g.drawLine((int)position.getX(), (int)position.getY(), (int)position.getX(), (int)position.getY());
    }
    
    
    /**
     * Used when "the model" sends notifyObservers(arg1).
     */
	@Override //Movement (or something)
	public void update(Observable who, Object what) {
		
		// if (moved) move(new_x, new_y);
		if (what instanceof Vector2D) {
			position = (Vector2D) what;
		}
		
		// if (died) parent.removeItem(this);
		if (what instanceof String) {
			if (((String)what).equals("Died"))
			{
				final MapFigure reference = this;
				SwingUtilities.invokeLater(new Runnable() {
				    public void run() { parent.removeMe(reference); }	    
				});
			}
		}
		
		// if (resized) resize(new_size);
	}
	
	
	/**
	 * Moves the figure to a new position
	 * @param position
	 */
	private void setPosition (Vector2D position) {
		this.position = position;
	}
	public double positionX() {
		return position.getX();
	}
	public double positionY() {
		return position.getY();
	}
}
