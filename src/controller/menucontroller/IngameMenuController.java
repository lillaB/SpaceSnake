
package controller.menucontroller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.MainController;
import util.GameEvent;

/**
 * Handles events from the IngameMenu
 */
public class IngameMenuController implements ActionListener {
	private MainController parent;
	
	/**
	 * Constructor that adds a reference to the parent controller
	 * @param parent	The parent controller
	 */
	public IngameMenuController(MainController parent){
		this.parent = parent;
	}
	
	/**
	 * Handles events coming from buttons on the ingame menu
	 * @param e		The ActionEvent sent from a button press
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == GameEvent.RETURN_TO_GAME) {
			parent.resumeGame();
		}
		else if (e.getActionCommand() == GameEvent.SAVE_GAME) {
			parent.saveGame();
		}
		else if (e.getActionCommand() == GameEvent.EXIT_GAME) {
			parent.exitMenu();
		}
	}
}
