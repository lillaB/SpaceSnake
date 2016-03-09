package view.figures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import util.Vector2D;
import view.GameView;

/**
 * Class responsible for painting the Edibles.
 * @author Gustav
 * @version 2016-03-04
 */

@SuppressWarnings("serial")
public class EdibleView 
extends GameFigure {


	/**
	 * Constructor that creates the figure.
	 * @param position	the initial position
	 * @param size		The initial size (the diameter)
	 * @param parent	The containing GameView in which it is located
	 */
	public EdibleView(Vector2D position, double size, GameView parent){
		super(position, size, parent);
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
    	g.setColor(new Color(0,255,0));
        g.fillOval(0, 0, (int)size, (int)size);
    }

}
