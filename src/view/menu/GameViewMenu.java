package view.menu;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import util.GameEvent;
import util.Config;
import util.Parser;
import view.GameComponent;

/**
 * This is a class that just contains the game view menu
 * 
 * @author Gustav
 * @version 2016-02-05
 */

@SuppressWarnings("serial")

public class GameViewMenu 
extends GameComponent
{		


	/**
	 * Constructor that generates the menu
	 */
	public GameViewMenu ()
	{		
		this.buildMenu();
	}
	
	
	/**
	 * Constructor that adds an ActionListener and creates the menu.
	 * @param AL		The ActionListener to which events get sent
	 */
	public GameViewMenu (ActionListener AL)
	{
		this();
		this.addActionListener(AL);
	}
	
	
	/**
	 * Where the menu actually gets created. Only called from the constructor.
	 */
	private void buildMenu()
	{
		MenuPanel menuPanel = new MenuPanel(this, "");
		menuPanel.setBackground(Parser.ColorFromString(Config.get("Gamemenu_bg_color")));
		menuPanel.setBorderColor(Parser.ColorFromString(Config.get("Gamemenu_border_color")));
		menuPanel.setWidth(Integer.parseInt(Config.get("Menu_corner_r")));
		
		Color bg_color = Parser.ColorFromString(Config.get("Gamemenu_button_bg_color"));
		Color border_color = Parser.ColorFromString(Config.get("Gamemenu_button_border_color"));
		Color highlight_color = Parser.ColorFromString(Config.get("Gamemenu_button_highlight"));
		menuPanel.addButton("+", GameEvent.ZOOM_IN, bg_color, border_color, highlight_color);
		menuPanel.addButton("-", GameEvent.ZOOM_OUT, bg_color, border_color, highlight_color); //"−" eller "\u2212"
		
		this.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.add(menuPanel);
		
		this.setOpaque(false);
		this.setVisible(true);
	}
	

}
