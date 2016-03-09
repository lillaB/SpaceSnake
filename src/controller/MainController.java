package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Timer;
import java.util.TimerTask;

import model.WorldCollection;

import controller.menucontroller.GameOverMenuController;
import controller.menucontroller.IngameMenuController;
import controller.menucontroller.StartupMenuController;
import util.GameEvent;
import util.Vector2D;
import view.View;

/**
 * Creates view and subcontrollers, handles events from the keyboard and implements actions from subcontrollers
 * 
 * @author Ingrid, Micaela
 * @version 2016-02-28
 */

public class MainController implements ActionListener {
	private View view;
	private String state;
	
	private static final String STARTUP_MENU = "Startup Menu";
	private static final String INGAME_MENU = "Ingame Menu";
	private static final String GAME_VIEW = "Game View";
	private static final String GAME_OVER = "Game Over";
	
	private StartupMenuController startupMenuController;
	private IngameMenuController ingameMenuController;
	private GameController gameController;
	private GameOverMenuController gameOverMenuController;
	
	/**
	 * Constructor that adds a reference to the view, configures the view and adds
	 * subcontrollers and starts the startup menu
	 * 
	 * @param view	Reference to the view
	 */
	public MainController(View view){
		this.view = view;
		state = STARTUP_MENU;
		
		configView();
		
		startupMenuController = new StartupMenuController(this);
		ingameMenuController = new IngameMenuController(this);
		gameController = new GameController(this);
		gameOverMenuController = new GameOverMenuController(this);
		
		view.showStartupMenu(startupMenuController);
	}
	
	/**
	 * Adds an ActionListener and a KeyListener to the view
	 */
	private void configView () {
		view.addActionListener(this);
		view.addKeyListener(KeyEvent.VK_ESCAPE, GameEvent.ESC_PRESSED);
		view.addKeyListener(KeyEvent.VK_UP, GameEvent.UP_PRESSED);
		view.addKeyListener(KeyEvent.VK_DOWN, GameEvent.DOWN_PRESSED);
		view.addKeyListener(KeyEvent.VK_RIGHT, GameEvent.RIGHT_PRESSED);
		view.addKeyListener(KeyEvent.VK_LEFT, GameEvent.LEFT_PRESSED);
	}
	
	/**
	 * Starts a new game
	 */
	public void startNewGame() {
		gameController.newGame();
		view.hideStartupMenu();			
		gameController.addObserver(view.showNewGame(gameController));
		gameController.addObserver(view.showNewMap());
		
		final Timer timer = new Timer();
	    timer.schedule(new TimerTask() {
	        public void run() {
	        	gameController.runPhysics();
	            timer.cancel();
	        }}, 100);
		
		state = GAME_VIEW;
	}
		
	/**
	 * Loads a game
	 */
	public void loadGame() {
		//Get filename from gui
		String filename = view.loadGameFileChooser();
		if (filename != null)
		{
			FileInputStream fis = null;
			ObjectInputStream in = null;
			WorldCollection theWorld = null;
			//Load the game
			try {
				fis = new FileInputStream(filename);
				in = new ObjectInputStream(fis);
				theWorld = (WorldCollection) in.readObject();
				in.close();
				
				//Start the game
				gameController.loadGame(theWorld);
				view.hideStartupMenu();			
				gameController.addObserver(view.showNewGame(gameController));
				gameController.addObserver(view.showNewMap());
				
				final Timer timer = new Timer();
			    timer.schedule(new TimerTask() {
			        public void run() {
			        	gameController.runPhysics();
			            timer.cancel();
			        }}, 100);
				
				state = GAME_VIEW;
			} catch (Exception ex) {
				view.messageDialog("Error while loading file.");
			}
		}
	}
	
	/**
	 * Exits the game
	 */
	public void exit() {
		System.exit(0);
	}
	
	/**
	 * Resumes the game
	 */
	public void resumeGame() {
		view.hideIngameMenu();
		view.showMap();
		gameController.resumePhysics();
		state = GAME_VIEW;
	}
	
	/**
	 * Saves the game
	 */
	public void saveGame() {
		//get filename from gui
		String filename = view.saveGameFileChooser();
		if (filename != null)
		{
			System.out.println(filename);
			//get worldcollection
			WorldCollection theWorld = gameController.getWorldCollection();
			//Save the game
			FileOutputStream fos = null;
	    	ObjectOutputStream out = null;
	    	try {
	    		fos = new FileOutputStream(filename);
	    		out = new ObjectOutputStream(fos);
	    		out.writeObject(theWorld);
	    		out.close();
	    	} catch (Exception ex) {
	    		view.messageDialog("Error while saving file.");
	    	}
		}
	}
	
	/**
	 * Exits the game from the ingame menu or gameover menu and shows the startup menu
	 */
	public void exitMenu() {
		gameController.stopPhysics();
		switch (state) {
		case INGAME_MENU: view.hideIngameMenu();
			break;
		case GAME_OVER: view.hideGameOverMenu();
			break;
		}
		view.hideGame();
		view.showStartupMenu(startupMenuController);
		state = STARTUP_MENU;
	}
	
	/**
	 * Pauses a running game and shows the in game menu
	 */
	public void pauseGame() {
		gameController.pausePhysics();
		view.showIngameMenu(ingameMenuController);
		state = INGAME_MENU;
	}
	
	/**
	 * Sets Game Over state and shows the Game Over menu
	 */
	public void setGameOver() {
		view.showGameOverMenu(gameOverMenuController);
		state = GAME_OVER;
	}
	
	/**
	 * Handles events coming from the ESC button on the keyboard, and from when the main window is closed
	 * @param e		The ActionEvent sent from a button press or by closing the main window
	 */
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand().equals(GameEvent.ESC_PRESSED)) {
			switch (state) {
			case STARTUP_MENU: 
				break;
			case INGAME_MENU: resumeGame();
				break;
			case GAME_VIEW: pauseGame();
				break;
			case GAME_OVER:
				break;
			}
		}
		if (e.getActionCommand().equals(GameEvent.UP_PRESSED)) {
			gameController.accelerate(new Vector2D(0,-1));
		}
		if (e.getActionCommand().equals(GameEvent.DOWN_PRESSED)) {
			gameController.accelerate(new Vector2D(0,1));
		}
		if (e.getActionCommand().equals(GameEvent.RIGHT_PRESSED)) {
			gameController.accelerate(new Vector2D(1,0));
		}
		if (e.getActionCommand().equals(GameEvent.LEFT_PRESSED)) {
			gameController.accelerate(new Vector2D(-1,0));
		}
		if (e.getActionCommand() == GameEvent.WINDOW_CLOSED) {
			exit();
		}
	}
}
