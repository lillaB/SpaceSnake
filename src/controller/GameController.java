
package controller;

import java.lang.Math;
import java.util.Observable;
import java.util.Observer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.*;
import model.objects.Edible;
import view.*;
import util.Config;
import util.GameEvent;
import util.Vector2D;

/**
 * Class that handles events from the GameView and methods for the PhysicsEngine
 * 
 * @author Ingrid, Micaela
 * @version 2016-02-28
 */

public class GameController 
implements ActionListener, Observer 
{
	private WorldCollection worldCollection;
	private PhysicsEngine physicsEngine;
	
	@SuppressWarnings("unused")
	private WorldFactory worldFactory;
	
	private MainController parent;

	private static final long gameSpeed = 50;
	private double mousePower = 20;
	private double keyPower = 20;
	
	/**
	 * Constructor that adds a reference to the parent controller
	 * @param parent	The parent controller
	 */
	public GameController(MainController parent){
		this.parent = parent;
		mousePower = Double.parseDouble(Config.get("Mouse_acc_power"));
		keyPower = Double.parseDouble(Config.get("Key_acc_power"));
	}
	
	/**
	 * Creates a new game
	 */
	public void newGame () {
		worldCollection = new WorldCollection();
		worldCollection.addObserver(this);
		worldCollection.setWorldSize(WorldFactory.randomWorldSize());
		physicsEngine = new PhysicsEngine(worldCollection, 1, gameSpeed);
		worldFactory = new WorldFactory(this, worldCollection);
	}
	
	/**
	 * Loads a game
	 */
	public void loadGame (WorldCollection theWorld) {
		worldCollection = theWorld;
		worldCollection.addObserver(this);
		physicsEngine = new PhysicsEngine(worldCollection, 1, gameSpeed);
	}
	
	/**
	 * Returns the world. Used in order to save a game.
	 * @return worldCollection	the world of the game
	 */
	public WorldCollection getWorldCollection() {
		return worldCollection;
	}
	
	/**
	 * Starts the thread where the physics run
	 */
	public void runPhysics () {
		physicsEngine.start();
	}
	
	/**
	 * Stops the thread where the physics run
	 */
	public void stopPhysics () {
		physicsEngine.interrupt();
	}
	
	/**
	 * Pauses the thread where the physics run
	 */
	public void pausePhysics() {
		physicsEngine.setPaused();
	}
	
	/**
	 * Resumes the thread where the physics run
	 */
	public void resumePhysics() {
		synchronized(physicsEngine) {
			physicsEngine.setResumed();
			physicsEngine.notifyAll();
		}
	}
	
	/**
	 * Adds an observer to the world
	 * @param gameObserver	The observer that will be added
	 */
	public void addObserver(GameObserver gameObserver) {
		worldCollection.addObserver(gameObserver);
		gameObserver.addWorld(worldCollection);
	}
	
	/**
	 * Handles mouse events in the game
	 * @param e_in		The ActionEvent sent from a mouse press
	 */
	public void actionPerformed(ActionEvent e_in) {
		GameEvent e = (GameEvent) e_in;
		if (e.getActionCommand() == GameEvent.MOUSE_PRESSED) {
			physicsEngine.SnakePull(e.getVector().div(200).scale(mousePower));
			//Maybe should set something in the physicsengine that released unsets?
		}
		else if (e.getActionCommand() == GameEvent.MOUSE_RELEASED) {
			physicsEngine.SnakePull(new Vector2D (0,0));
		}
		else if (e.getActionCommand() == GameEvent.MOUSE_DRAGGED) {
			physicsEngine.SnakePull(e.getVector().div(200).scale(mousePower));
		}
	}
	
	public void accelerate(Vector2D	acc) {
		physicsEngine.SnakePull(acc.scale(keyPower));
	}

	/**
	 * Override Observer function.
	 */
	@Override
	public void update(Observable arg0, Object arg1) { //TODO: Ändra variabelnamn...
		if(arg1 instanceof String)
		{	
			if (((String)arg1).equals("GAMEOVER")) {
				parent.setGameOver();
				pausePhysics(); 
			} else if (((String)arg1).equals("NEWEDIBLE")) {
				newEdible();
			}
			
		}
	}
	
	/**
	 * Creates anew edible somewhere.
	 */
	private void newEdible() {
		Vector2D pos = new Vector2D(Math.random() * worldCollection.getWorldSize() - worldCollection.getWorldSize()/2, Math.random() * worldCollection.getWorldSize() - worldCollection.getWorldSize()/2);
		while ( pos.lengthsquared()*4 > worldCollection.getWorldSize()*worldCollection.getWorldSize() )
			pos = new Vector2D(Math.random() * worldCollection.getWorldSize() - worldCollection.getWorldSize()/2, Math.random() * worldCollection.getWorldSize() - worldCollection.getWorldSize()/2);
		double speedMultiplier = 30;
		Vector2D speed = new Vector2D((Math.random() * speedMultiplier) - speedMultiplier/2, (Math.random() * speedMultiplier) - speedMultiplier/2);
		double mass = Double.parseDouble(Config.get("edible_mass"));
		double size = Double.parseDouble(Config.get("edible_size"));
		worldCollection.add(new Edible(worldCollection,speed,pos,mass,size));
	}
}
