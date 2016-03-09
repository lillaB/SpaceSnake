package view.menu;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import util.GameEvent;
import util.Config;
import util.Parser;
import view.GameComponent;


/**
 * This is a class that just contains the gameover menu
 * 
 * @author Micaela
 * @version 2016-02-25
 */

@SuppressWarnings("serial")
public class GameOverMenu 
extends GameComponent{		

	/**
	 * Constructor that generates the menu
	 */
	public GameOverMenu ()
	{		
		this.buildMenu();
	}
	
	
	/**
	 * Constructor that adds an ActionListener and creates the menu.
	 * @param AL		The ActionListener to which events get sent
	 */
	public GameOverMenu (ActionListener AL)
	{
		this();
		this.addActionListener(AL);
	}
	
	
	/**
	 * Where the menu actually gets created. Only called from the constructor.
	 */
	private void buildMenu()
	{
		MenuPanel menuPanel = new MenuPanel(this, Config.get("GameOver_menu_title"));
		menuPanel.setBackground(Parser.ColorFromString(Config.get("GameOver_bg_color")));
		menuPanel.setBorderColor(Parser.ColorFromString(Config.get("GameOver_border_color")));
		
		Color bg_color = Parser.ColorFromString(Config.get("GameOver_button_bg_color"));
		Color border_color = Parser.ColorFromString(Config.get("GameOver_button_border_color"));
		Color highlight_color = Parser.ColorFromString(Config.get("GameOver_button_highlight"));
		menuPanel.addButton(Config.get("Gameover_menu_back_to_menu"), GameEvent.BACK_TO_MENU, bg_color, border_color, highlight_color);

		this.add(menuPanel);
		this.setOpaque(false);
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.setVisible(true);
	}
	

}
