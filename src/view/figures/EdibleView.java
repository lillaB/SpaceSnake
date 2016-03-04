package view.figures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import util.Vector2D;
import view.GameView;

@SuppressWarnings("serial")
public class EdibleView 
extends GameFigure {

	public EdibleView(double x, double y, double size, GameView parent){
		super(x, y, size, parent);
	}
	public EdibleView(Vector2D position, double size, GameView parent){
		super(position.getX(), position.getY(), size, parent);
	}

    /**
     * Paints itself.
     * @param g_in
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
