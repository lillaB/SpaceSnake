package view;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Observable;

import javax.swing.SwingUtilities;


import model.Floater;
import model.WorldCollection;
import model.WorldObject;
import model.objects.BlackHole;
import model.objects.Edible;
import model.objects.SnakeHead;
import model.objects.SnakeTail;

import util.GameEvent;
import util.Vector2D;
import view.figures.*;

/**
 * This is a class that just contains the game itself
 * 
 * Is listener to itself in order to zoom and other stuff. Some events gets sent to others...
 * is observer at the model on updates there (new objects?)
 * 
 * @author Gustav
 * @version 2016-02-05
 */


@SuppressWarnings("serial")
public class GameView 
extends GameComponent 
implements MouseWheelListener, MouseMotionListener, MouseListener, GameObserver, ActionListener
{

	//Temp variable until the proper world gets used
	int worldSize=800;
	
	// Determines the zoom level
	protected float zoom = 1;
	
	//How much should the zoom change on zoom in/out
	private double zoomstep = 1.01; 
	
	//The snake position
	private Vector2D snakePosition=new Vector2D(0,0);
	
	/**
	 * Constructor that generates the view.
	 */
	public GameView ()
	{	
		this.build();
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
	}
	
	
	/**
	 * Constructor that generates the game view and adds an ActionListener to it.
	 * @param AL		The ActionListener to which events get sent
	 */
	public GameView (ActionListener AL)
	{
		this();
		this.addActionListener(AL);
	}
	
	
	/**
	 * Removes everything from the view. Equal to restart the view.
	 */
	public void clear() {
		removeAll();
	}
	
	
	/**
	 * The actual constructor of the view. Only called from the constructors.
	 */
	private void build()
	{
		//Use coordinates for positioning
		this.setLayout(null);
	}

	
	/**
	 * This draws the world.
	 * @param g		The graphics object that is used
	 */
	@Override
    public void paintComponent(final Graphics g) {
		
		Graphics2D g2 =(Graphics2D)g;
        super.paintComponent(g2);
        
        //Set center to (0,0)
        g2.translate(this.getWidth()/2 - snakePosition.getX()*zoom, this.getHeight()/2 - snakePosition.getY()*zoom); 
        
        // Set zoom
        g2.scale(zoom, zoom);
       
        // Draw the world
        g2.setRenderingHint(
    	        RenderingHints.KEY_ANTIALIASING,
    	        RenderingHints.VALUE_ANTIALIAS_ON);
    	g2.setColor(Color.BLACK);
        g2.drawOval(-worldSize/2-1, -worldSize/2-1, worldSize+2, worldSize+2);
        g2.setColor(Color.BLACK);
        g2.fillOval(-worldSize/2, -worldSize/2, worldSize, worldSize);  
    }
	
	
	/**
	 * Zoom in or out
	 * @param amount	The amount to zoom. Logarithmic scale	
	 * @return The zoom level after the zoom action
	 */
	public float zoom(int amount){
		zoom *= Math.pow(zoomstep, amount);
		repaint();
		return zoom;
	}
	
	/** Zooms based on the scroll */
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		zoom(e.getUnitsToScroll());
	}
	
	/** Do nothing */
	@Override public void mouseClicked(MouseEvent arg0) {}
	/** Do nothing */
	@Override public void mouseEntered(MouseEvent arg0) {}
	/** Do nothing */
	@Override public void mouseExited(MouseEvent arg0) {}
	
	/**
	 * Starts accelerating the snake.
	 * @param arg0	The MouseEvent that happened, used to get coordinates
	 */
	@Override 
	public void mousePressed(MouseEvent arg0) {
		fireEvent(new GameEvent(this, GameEvent.MOUSE_PRESSED, relativePosition(arg0.getPoint())));
	}
	
	/**
	 * Stops accelerating the snake.
	 * @param arg0	The MouseEvent that happened, used to get coordinates
	 */
	@Override
	public void mouseReleased(MouseEvent arg0) {
		fireEvent(new GameEvent(this, GameEvent.MOUSE_RELEASED, relativePosition(arg0.getPoint())));
	}
	
	/**
	 * Changes the direction of where the snake accelerates towards
	 * @param arg0	The MouseEvent that happened, used to get coordinates
	 */
	@Override //Change acceleration direction
	public void mouseDragged(MouseEvent arg0) {
		fireEvent(new GameEvent(this, GameEvent.MOUSE_DRAGGED, relativePosition(arg0.getPoint())));
	}
	
	/** Do nothing. */
	@Override public void mouseMoved(MouseEvent arg0) {}
	
	/**
	 * Calculate a relative position on the screen compared to the snake (center of screen). Uses the current zoom.
	 * @param point The point that is compared to the center
	 */
	private Vector2D relativePosition(Point point) {
		return (new Vector2D(point)).sub(new Vector2D(this.getWidth()/2,this.getHeight()/2)).div(zoom);
	}
	
	/**
	 * Adds a new world to the view including all objects and all constants.
	 * @param world The WorldCollection from which all objects and constants is taken
	 */
	public void addWorld (WorldCollection world) {
		for (WorldObject thing : world.getCollection()) {
			addItem(thing);
		}
		worldSize=(int)world.getWorldSize();
	}
	
	
	/**
	 * Update function run by the observable (through notifyobservers).
	 * @param who	the observable that was updated
	 * @param what	what was updated. If it is an WorldObject that object is added to the view
	 */
	@Override //Something happened in the world!!!
	public void update(Observable who, Object what) {
		//If it was a worldObject: add it.
		if (what instanceof WorldObject) {
			addItem((WorldObject) what);
		}
	}
	
	
	/**
	 * Adds some item to the world
	 * @param what	The item to add
	 */
	private void addItem (WorldObject what) {
		final GameFigure figure;
		if (what instanceof Floater) {
			figure = new FloaterView(what.getPosition().getX(), what.getPosition().getY(), what.getRadius()*2 ,this);
		} else if (what instanceof Edible) {
			figure = new EdibleView(what.getPosition().getX(), what.getPosition().getY(), what.getRadius()*2 ,this);
		} else if (what instanceof BlackHole) {
			figure = new BlackHoleView(what.getPosition().getX(), what.getPosition().getY(), what.getRadius()*2 ,this);
		} else if (what instanceof SnakeHead) {
			figure = new SnakeHeadView(what.getPosition().getX(), what.getPosition().getY(), what.getRadius()*2 ,this);
		} else if (what instanceof SnakeTail) {
			figure = new SnakeTailView(what.getPosition().getX(), what.getPosition().getY(), what.getRadius()*2 ,this);
		} else {
			figure = new GameFigure(what.getPosition().getX(), what.getPosition().getY(), what.getRadius()*2 ,this);
		}
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() { addFigure(figure); }	    
		});
		what.addObserver(figure);		
	}
	
	
	/**
	 * Adds a GameFigure too this so it gets painted.
	 * @param figure the GameFigure that is added
	 */
	private void addFigure (GameFigure figure) {
		this.add(figure);
	}
	
	
	/**
	 * Remove some item from the world. Called from the items themselves.
	 * @param who	The item to remove
	 */
	public void removeMe(GameFigure who) {
		this.remove(who);
	}
	
	/**
	 * Used for the snake to update where it is.
	 * @param position	The snake position
	 */
	public void updateSnakePosition(Vector2D position) {
		this.snakePosition = position;
	}


	/**
	 * For commands sent from the game view menu
	 * @param e	The ActionEvent that happened
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(GameEvent.ZOOM_IN)) zoom(10);
		else if (e.getActionCommand().equals(GameEvent.ZOOM_OUT)) zoom(-10);
		
	}
}
