package view.figures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

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
extends JComponent
implements Observer
{

	protected double size;
	protected double x,y;

	//Extra buffer when determining bounds
	int extra=1;
	
	//Needed to repaint the containing panel and to delete itself
	GameView parent;
	
	
	/**
	 * Constructor that takes a position (x,y) and a size and generates a GameFigure.
	 * 
	 * @param x		The x-position
	 * @param y		The y-position
	 * @param size	The size of the GameFigure
	 * @param parent	The containing GameView
	 */
    public GameFigure(double x, double y, double size, GameView parent)
    {
        this.x=x;
        this.y=y;
        this.size=size;
        this.parent=parent;
        this.setBounds((int)(x-size/2-2*extra), (int)(y-size/2-2*extra), (int)size+2*extra, (int)size+2*extra);
    }
	
    
    
    /**
     * Paints itself.
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
     * Used when "the model" sends notifyObservers(arg1).
     */
	@Override //Movement (or something)
	public void update(Observable who, Object what) {
		
		//If it was a vector: move there.
		if (what instanceof Vector2D) {
			final Vector2D position = new Vector2D((Vector2D) what);
			SwingUtilities.invokeLater(new Runnable() {
			    public void run() { setPosition(position); }	    
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
	private void setPosition (Vector2D position) {
		this.x = position.getX();
		this.y = position.getY();
    	this.setBounds((int)(x-size/2-2*extra), (int)(y-size/2-2*extra), (int)size+2*extra, (int)size+2*extra);
		parent.repaint();
	}
	
	
	/**
     * Set a new size for the figure
     * @param new_size	The new size
     */
    private void resize (double new_size) {
    	size = new_size;
    	this.setBounds((int)(x-size/2-2*extra), (int)(y-size/2-2*extra), (int)size+extra, (int)size+extra);
    	parent.repaint();
	}
    
}
