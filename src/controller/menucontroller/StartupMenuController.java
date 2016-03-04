package controller.menucontroller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.MainController;
import util.GameEvent;

/**
 * Handles events from the StartupMenu
 */
public class StartupMenuController implements ActionListener {
	private MainController parent;
	
	/**
	 * Constructor that adds a reference to the parent controller
	 * @param parent	The parent controller
	 */
	public StartupMenuController(MainController parent){
		this.parent = parent;
	}
	
	/**
	 * Handles events coming from buttons on the startup menu
	 * @param e		The ActionEvent sent from a button press
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == GameEvent.START_NEW_GAME) {
			parent.startNewGame();
		}
		else if (e.getActionCommand() == GameEvent.LOAD_GAME) {
			parent.loadGame();
		}
		else if (e.getActionCommand() == GameEvent.EXIT) {
			parent.exit();
		}
	}
}
