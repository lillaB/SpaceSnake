package view.figures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import util.Vector2D;
import view.GameView;

/**
 * The main class for figures in the game view. Should be subclassed for all different kinds of figures.
 * Is an observer for events like a new figure added.
 * Is an ActionListener to be able to listen to itself.
 *  
 * @author Gustav
 * @version 2016-02-05
 */

@SuppressWarnings("serial")
public class GameFigure 
extends Figure
{

	//Extra buffer when determining bounds
	int extra=1;
	
	
	/**
	 * Constructor that takes a position (x,y) and a size and generates a GameFigure.
	 * 
	 * @param position	The initial position
	 * @param size		The size of the GameFigure
	 * @param parent	The containing GameView
	 */
    public GameFigure(Vector2D position, double size, GameView parent)
    {
    	super(parent, position, size);
        this.setBounds((int)(position.x-size/2-2*extra), (int)(position.y-size/2-2*extra), (int)size+2*extra, (int)size+2*extra);
    }
	
    
    
    /**
     * Paints itself. Default function that each subclass should override.
     * @param g_in		The graphics that is used
     */
    @Override
    public void paintComponent(Graphics g_in) {
		Graphics2D g = (Graphics2D)g_in;
    	g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, 
    						RenderingHints.VALUE_ANTIALIAS_ON);
    	
    	g.setColor(Color.WHITE);
        g.fillOval(0, 0, (int)size, (int)size);
        g.setColor(Color.BLACK);
        g.drawOval(0, 0, (int)size, (int)size);
        
        g.setColor(Color.RED);
        String text="?";
        g.setFont(g.getFont().deriveFont((float)(size*0.75)));
        Rectangle2D r = g.getFont().getStringBounds(text, g.getFontRenderContext());
        g.drawString(text, (int)(size/2-r.getCenterX()),(int)(size/2-r.getCenterY()));    
    }
	
	
	/**
	 * Moves the figure to a new position
	 * @param position	the new position
	 */
	public void setPosition (Vector2D position) {
		super.setPosition(position);
		this.setBounds((int)(position.x-size/2-2*extra), (int)(position.y-size/2-2*extra), (int)size+2*extra, (int)size+2*extra);
		parent.repaint();
	}
	
	
	/**
     * Set a new size for the figure
     * @param new_size	The new size
     */
    public void resize (double new_size) {
    	super.resize(new_size);
    	this.setBounds((int)(position.x-size/2-2*extra), (int)(position.y-size/2-2*extra), (int)size+extra, (int)size+extra);
    	parent.repaint();
	}
    
}
