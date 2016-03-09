package view.figures;

import java.awt.Color;
import java.awt.Graphics;

import util.Vector2D;
import view.MapView;

/**
 * The class for figures in the game view.
 * This is an observer for events like a new figure added.
 * Is also an ActionListener to be able to listen to itself.
 *  
 * @author Gustav
 * @version 2016-03-04
 */

@SuppressWarnings("serial")
public class MapFigure 
extends Figure
{

	protected Color color = new Color(255,0,0);
	
	//Extra buffer when determining bounds
	protected int extra=1;	
	
	/**
	 * Constructor that takes a position (x,y) and a size and generates a GameFigure.
	 * @param position	The initial position
	 * @param size		The size of the Figure
	 * @param parent	The containing MapView
	 * @param color		The color.
	 */
    public MapFigure(MapView parent, Vector2D position, double size, Color color)
    {
    	super(parent, position, size);
        this.color = color;
    }
	
    
    /**
     * Returns the color of itself to be used in the map.
     * @return		the current color
     */
    public Color getColor() {
    	return color;
    }
    
    /**
     * Sets the color of itself to be used in the map.
     * @param c		the new color
     */
    public void setColor(Color c) {
    	color = c;
    }
    
    /**
     * Paints itself.
     * @param g		the graphics object that is used
     */
    public void paintComponent(Graphics g) {
    	g.setColor(color);
    	g.drawLine((int)position.getX(), (int)position.getY(), (int)position.getX(), (int)position.getY());
    }
    
	
	/**
	 * Returns the x-coordinate
	 * @return the x-coordinate
	 */
	public double positionX() {
		return position.getX();
	}
	
	/**
	 * Returns the y-coordinate
	 * @return the y-coordinate
	 */
	public double positionY() {
		return position.getY();
	}
}
