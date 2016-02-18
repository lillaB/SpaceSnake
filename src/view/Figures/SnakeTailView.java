package view.Figures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class SnakeTailView 
extends GameFigure {

	public SnakeTailView(double x, double y, double size, JComponent parent){
		super(x, y, size, parent);
	}

    /**
     * Paints itself.
     * @param g
     */
    @Override
    public void paintComponent(Graphics g_in) {
    	double centerx = size/2;
    	double centery = size/2;
    	double radius  = size/2;
    	
    	Graphics2D g = (Graphics2D)g_in;
    	g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, 
    						RenderingHints.VALUE_ANTIALIAS_ON);
    	
       	Point2D center = new Point2D.Double(centerx, centery);
        Point2D focus = center;//new Point2D.Float(40, 40);
        float[] dist = {0.0f, 0.8f, 1.0f};
        Color[] colors = {new Color(255,255,200), new Color(235,235,0,255), new Color(235,235,0,255)};
        RadialGradientPaint rgrad = new RadialGradientPaint(center, (float) radius, focus, dist, colors, CycleMethod.NO_CYCLE);
        g.setPaint(rgrad);
        g.fillOval(0, 0, (int)size, (int)size);
        
    }

}
