package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.SwingUtilities;
import javax.swing.Timer;


import model.Floater;
import model.WorldObject;
import model.objects.BlackHole;
import model.objects.Edible;
import model.objects.SnakeHead;
import model.objects.SnakeTail;

import util.Config;
import util.Parser;
import view.figures.Figure;
import view.figures.MapFigure;

/**
 * This is a class that just contains the game itself
 * 
 * Is listener to itself in order to zoom and other stuff. Some events gets sent to others...
 * is observer at the model on updates there (new objects?)
 * 
 * @author Gustav
 * @version 2016-03-04
 */


@SuppressWarnings("serial")
public class MapView 
extends WorldView
implements MouseListener, ActionListener
{

	// Determines the zoom level
	protected float mapSize = 1;
	private int margin = 5;
	
	//All figures in the map
	private ArrayList<MapFigure> theList = new ArrayList<MapFigure>();
	
	//Update itself every now and then
	Timer t;
	
	//Colors. These are defaults, overridden by config in the constructor.
	Color bg_color1 = new Color(255,255,255,255);
	Color bg_color2 = new Color(255,255,255,200);
	Color bg_color3 = new Color(255,255,255,255);
	Color border_color = new Color(0,0,0,200);
	
	
	/**
	 * Constructor that generates the view.
	 */
	public MapView ()
	{	
		this.build();
		this.addMouseListener(this);
		
		t = new Timer(500,this); //Only needed if nothing is repainted in the gameview.
		t.setActionCommand("Repaint");
        t.start();
	}
	
	
	/**
	 * The actual constructor of the view. Only called from the constructors.
	 */
	private void build()
	{
		//Use coordinates for positioning
		this.setLayout(null);
		mapSize = Integer.parseInt(Config.get("Map_size"));
		bg_color1 = Parser.ColorFromString(Config.get("Map_bg_color1"));
		bg_color2 = Parser.ColorFromString(Config.get("Map_bg_color2"));
		bg_color3 = Parser.ColorFromString(Config.get("Map_bg_color3"));
		border_color = Parser.ColorFromString(Config.get("Map_border_color"));
	}

	
	/**
	 * This draws the map.
	 * @param g		The graphics object that is used
	 */
	@Override
    public void paintComponent(final Graphics g) {
		
		Graphics2D g2 =(Graphics2D)g;
        super.paintComponent(g2);
        
        //Set center to (0,0)
        g2.translate(this.getWidth()-mapSize/2-margin,this.getHeight()-mapSize/2-margin); 
        // Set zoom
        g2.scale(mapSize/worldSize, mapSize/worldSize);
        
        // Draw the world
        g2.setRenderingHint(
    	        RenderingHints.KEY_ANTIALIASING,
    	        RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(2*worldSize/mapSize));
        
        Point2D center = new Point2D.Double(0, 0);
        Point2D focus = center;
        float[] dist = {0.0f, 0.9f, 1.0f};
        Color[] colors = {bg_color1, bg_color2, bg_color3};
        RadialGradientPaint rgrad = new RadialGradientPaint(center, (float) worldSize/2, focus, dist, colors, CycleMethod.NO_CYCLE);
        g2.setPaint(rgrad);
    	g2.setColor(border_color);
        g2.drawOval(-worldSize/2-1, -worldSize/2-1, worldSize+2, worldSize+2);
        g2.drawLine(0, 0, 0, 0);
        g2.setPaint(rgrad);
        g2.fillOval(-worldSize/2, -worldSize/2, worldSize, worldSize);  
        
        //Draw all items:
        g2.setStroke(new BasicStroke(4*worldSize/mapSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        for (MapFigure figure : theList)
        {
        	g2.setColor(figure.getColor());
        	g2.drawLine((int)figure.positionX(), (int)figure.positionY(), (int)figure.positionX(), (int)figure.positionY());
        	
        }
    }
	
	
	///////////////////////////////////
	// Do nothing on any MouseEvents //
	///////////////////////////////////
	/** Do nothing */
	@Override public void mouseClicked(MouseEvent arg0) {}
	/** Do nothing */
	@Override public void mouseEntered(MouseEvent arg0) {}
	/** Do nothing */
	@Override public void mouseExited(MouseEvent arg0) {}
	/** Do nothing */
	@Override public void mousePressed(MouseEvent arg0) {}
	/** Do nothing */
	@Override public void mouseReleased(MouseEvent arg0) {}
	
	
	/**
	 * Needs to override for the rounded corners. Checks if som x and y coordinates are within it
	 * @param x		The x-coordinate (pixels)
	 * @param y		The y-coordinate (pixels)
	 */
	@Override
	public boolean contains (int x, int y) {
		double x1 = (this.getWidth()-margin-mapSize/2)-x;
		double y1 = (this.getHeight()-margin-mapSize/2)-y;
		return mapSize*mapSize/4 > x1*x1+y1*y1;
	}
	
	
	/**
	 * Removes everything from the view. Equal to restart the view.
	 */
	public void clear() {
		theList = new ArrayList<MapFigure>();
	}
	
	
	/**
	 * Adds some item to the world
	 * @param what	The item to add
	 */
	@Override
	protected void addItem (WorldObject what) {
		final MapFigure figure;
		if (what instanceof Floater) {
			figure = new MapFigure(this, what.getPosition(), what.getRadius()*2, new Color(0,0,155));
		} else if (what instanceof BlackHole) {
			figure = new MapFigure(this, what.getPosition(), what.getRadius()*2, new Color(0,0,0));
		} else if (what instanceof Edible) {
			figure = new MapFigure(this, what.getPosition(), what.getRadius()*2, new Color(0,255,0));
		} else if (what instanceof SnakeHead) {
			figure = new MapFigure(this, what.getPosition(), what.getRadius()*2, new Color(125,125,0));
		} else if (what instanceof SnakeTail) {
			figure = new MapFigure(this, what.getPosition(), what.getRadius()*2, new Color(155,155,0));
		} else {
			figure = new MapFigure(this, what.getPosition(), what.getRadius()*2, new Color(255,0,0));
		}
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() { theList.add(figure); }	    
		});
		what.addObserver(figure);
	}
	
	
	/**
	 * Remove some item from the world. Called from the items themselves.
	 * @param who	The item to remove
	 */
	@Override
	public void removeMe(Figure who) {
		theList.remove((MapFigure)who);
	}


	/**
	 * For commands sent from the game view menu
	 * @param e		the ActionEvent that happened
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		//If it was a timer event
		if (e.getActionCommand().equals("Repaint"))
			repaint();
	}
}
