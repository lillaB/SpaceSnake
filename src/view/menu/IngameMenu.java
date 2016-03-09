package view.menu;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import util.GameEvent;
import util.Config;
import util.Parser;
import view.GameComponent;


/**
 * This is a class that just contains the ingame menu with a zoom in and a zoom out button.
 * 
 * @author Gustav
 * @version 2016-02-05
 */

@SuppressWarnings("serial")
public class IngameMenu 
extends GameComponent{		

	/**
	 * Constructor that generates the menu
	 */
	public IngameMenu ()
	{	
		this.buildMenu();
	}
	
	
	/**
	 * Constructor that adds an ActionListener and creates the menu.
	 * @param AL		The ActionListener to which events get sent
	 */
	public IngameMenu (ActionListener AL)
	{
		this();
		this.addActionListener(AL);
	}
	
	
	/**
	 * Where the menu actually gets created. Only called from the constructor.
	 */
	private void buildMenu()
	{
		MenuPanel menuPanel = new MenuPanel(this, Config.get("Ingame_menu_title"));
		menuPanel.setBackground(Parser.ColorFromString(Config.get("Ingame_bg_color")));
		menuPanel.setBorderColor(Parser.ColorFromString(Config.get("Ingame_border_color")));
		
		Color bg_color = Parser.ColorFromString(Config.get("Ingame_button_bg_color"));
		Color border_color = Parser.ColorFromString(Config.get("Ingame_button_border_color"));
		Color highlight_color = Parser.ColorFromString(Config.get("Ingame_button_highlight"));
		menuPanel.addButton(Config.get("Ingame_menu_resume"), GameEvent.RETURN_TO_GAME, bg_color, border_color, highlight_color);
		menuPanel.addButton(Config.get("Ingame_menu_save"), GameEvent.SAVE_GAME, bg_color, border_color, highlight_color);
		menuPanel.addButton(Config.get("Ingame_menu_exitgame"), GameEvent.EXIT_GAME, bg_color, border_color, highlight_color);

		this.add(menuPanel);
		this.setOpaque(false);
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.setVisible(true);
	}
	

}
