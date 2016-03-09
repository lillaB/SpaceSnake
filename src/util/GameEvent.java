package util;

import java.awt.event.ActionEvent;


/**
 * Internal class making commands sent through events more readable and makes sure that they are unique.
 * @author Gustav
 * @version 2016-03-05
 */


@SuppressWarnings("serial") //Never saved anyway.
public class GameEvent
extends ActionEvent
{


	/**
	 * Strings to be used as action command strings. Any string can be used,
	 * these are only saved for convenience so the spelling is consistent.
	 */
	public static String
	ESC_PRESSED = "ESC pressed",
	UP_PRESSED = "Up pressed",
	DOWN_PRESSED = "Down pressed",
	RIGHT_PRESSED = "Right pressed",
	LEFT_PRESSED = "Left pressed",
	WINDOW_CLOSED = "Window closed",
	RETURN_TO_GAME = "Return to game",
	SAVE_GAME = "Save game",
	EXIT_GAME = "Exit game",
	START_NEW_GAME = "Start new game",
	LOAD_GAME = "Load game",
	EXIT = "Exit",
	BACK_TO_MENU = "Back to menu",
	MOUSE_PRESSED = "Mouse pressed",
	MOUSE_RELEASED = "Mouse released",
	MOUSE_DRAGGED = "Mouse dragged",
	ZOOM_IN = "Zoom in",
	ZOOM_OUT = "Zoom out";

	
	/**
	 * A vector that is used for sending position with a mouse click.
	 */
	private Vector2D vector;


	/**
	 * Constructor that generates a normal ActionEvent
	 * @param who		The object triggering the event
	 * @param action	The action sent to the listeners
	 */
	public GameEvent(Object who, String action) {
		super(who, ActionEvent.ACTION_PERFORMED, action, System.currentTimeMillis(), 0);
	}


	/**
	 * Constructor that extends the ActionEvent with a vector
	 * @param who		The object triggering the event
	 * @param action	The action sent to the listeners
	 * @param vector	The vector that get sent along
	 */
	public GameEvent(Object who, String action, Vector2D vector) {
		super(who, ActionEvent.ACTION_PERFORMED, action, System.currentTimeMillis(), 0);
		this.vector = vector;
	}


	/**
	 * Returns the vector that was sent along. Returns null if no vector was specified.
	 * @return	The vector
	 */
	public Vector2D getVector() {
		return vector;
	}

}