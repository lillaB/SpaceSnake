package view;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import view.menu.GameOverMenu;
import view.menu.GameViewMenu;
import view.menu.IngameMenu;
import view.menu.StartupMenu;

/**
 * This is the view class that handles every graphical interaction
 * 
 * @author Gustav
 * @version 2016-02-12
 */


public class View
{
	
	private IngameMenu ingameMenu;
	private GameView gameView;
	private GameViewMenu gameViewMenu;
	private MapView mapView;
	private StartupMenu startupMenu;
	private GameOverMenu gameOverMenu;
	private MainWindow mainWindow;

	
	/**
	 * Constructor that creates the different parts but only show the (empty) main window.
	 */
	public View()
	{
		mainWindow = new MainWindow();
		gameView = new GameView();
		gameViewMenu = new GameViewMenu();
		mapView = new MapView();
		startupMenu = new StartupMenu();
		ingameMenu = new IngameMenu();
		gameOverMenu = new GameOverMenu();
	}
	
	
	/**
	 * Add an ActionListener to the view
	 * @param AL ActionListener
	 */
	public void addActionListener(ActionListener AL) {
		mainWindow.addActionListener(AL);
	}
	
	
	/**
	 * Adds a keyListener to the window that sends events to the ActionListeners
	 * @param key		The keyCode
	 * @param command	The command that gets sent to the ActionListeners
	 */
	public void addKeyListener (int key, String command) {
		mainWindow.addKeyListener (key, command);
	}
	

	/**
	 * Shows the startup menu
	 * @param AL		The ActionListener that handles the generated events..
	 */
	public void showStartupMenu(ActionListener AL)
	{
		startupMenu.addActionListener(AL);
		mainWindow.addGameComponent(startupMenu,MainWindow.MENULAYER);
	}
	
	
	/**
	 * removes the startup menu from the main window.
	 */
	public void hideStartupMenu()
	{
		mainWindow.remove(startupMenu);
	}
	
	
	/**
	 * Shows the ingame menu.
	 * 
	 * @param AL		The ActionListener that handles the generated events..
	 */
	public void showIngameMenu(ActionListener AL)
	{
		ingameMenu.addActionListener(AL);
		mainWindow.addGameComponent(ingameMenu,MainWindow.GAMEMENULAYER);
	}

	
	/**
	 * removes the ingame menu from the main window.
	 */
	public void hideIngameMenu()
	{
		mainWindow.remove(ingameMenu);
	}
	
	/**
	 * Shows the gameover menu.
	 * 
	 * @param AL		The ActionListener that handles the generated events..
	 */
	public void showGameOverMenu(ActionListener AL)
	{
		gameOverMenu.addActionListener(AL);
		mainWindow.addGameComponent(gameOverMenu,MainWindow.GAMEMENULAYER);
	}

	
	/**
	 * removes the gameover menu from the main window.
	 */
	public void hideGameOverMenu()
	{
		mainWindow.remove(gameOverMenu);
	}
	
	
	/**
	 * Shows a Game in which everything happens. Can send actions to an {@link ActionListener} (the controller)
	 * the actions are: accelerate in different directions with different forces and to stop accelerating.
	 * maybe also on click a one time acceleration "pulse".
	 * 
	 * @param AL	The ActionListener to which the game sends its actions
	 */
	public GameObserver showGame(ActionListener AL)
	{
		gameView.addActionListener(AL);
		gameViewMenu.addActionListener(gameView);
		
		mainWindow.addGameComponent(gameView, MainWindow.GAMELAYER);
		mainWindow.addGameComponent(gameViewMenu, MainWindow.GAMECONTROLLAYER);
		
		return gameView;
	}
	
	/**
	 * Shows a cleared Game
	 * @param AL	an ActionListener that listenes to gameactions
	 * @return	the GameView
	 */
	public GameObserver showNewGame(ActionListener AL)
	{
		gameView.clear();
		return showGame(AL);
	}
	
	/**
	 * Shows a Map in which everything happens.
	 * @return the MapView
	 */
	public GameObserver showMap()
	{
		mainWindow.addGameComponent(mapView, MainWindow.MAPLAYER);
		return mapView;
	}
	
	/**
	 * Shows a cleared map.
	 * @return the MapView
	 */
	public GameObserver showNewMap()
	{
		mapView.clear();
		return showMap();
	}
	
	/**
	 * Hides the game.
	 */
	public void hideGame()
	{
		mainWindow.remove(gameView);
		mainWindow.remove(gameViewMenu);
		mainWindow.remove(mapView);
	}
	
	
	/**
	 * Opens a file dialog where the user chooses a file where the game gets saved.
	 * @return The file and its path as a string.
	 */
	public String saveGameFileChooser() {
		JFileChooser filechooser = new JFileChooser();
		int returnVal = filechooser.showSaveDialog(mainWindow);
	    if(returnVal == JFileChooser.APPROVE_OPTION)
	    	return filechooser.getSelectedFile().toString();
	    else return null;
	}
	
	
	/**
	 * Opens a file dialog where the user chooses a file where the game gets loaded.
	 * @return The file and its path as a string.
	 */
	public String loadGameFileChooser() {
		JFileChooser filechooser = new JFileChooser();
		int returnVal = filechooser.showOpenDialog(mainWindow);
	    if(returnVal == JFileChooser.APPROVE_OPTION)
	    	return filechooser.getSelectedFile().toString();
	    else return null;
	}
	
	
	/**
	 * Shows a popup dialog for error messages.
	 * @param message	The message that is in the dialogue
	 */
	public void messageDialog(String message) {
		//custom title, error icon
		JOptionPane.showMessageDialog(mainWindow, message, "Message", JOptionPane.ERROR_MESSAGE);
	}
	
}


