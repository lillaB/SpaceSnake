package view.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import util.Config;
import view.GameComponent;

/**
 * A class that generates a panel with some (optional) text and buttons.
 * @author Gustav
 * @version 2016-03-04
 */

@SuppressWarnings("serial")
public class MenuPanel 
extends JPanel 
implements MouseListener, ActionListener
{

	private Color bg_color = new Color(200,200,240,200);
	private Color border_color = new Color(100,100,140,200);
	private int corner_r = 20;
	private GameComponent parent;
	
	JPanel titlePanel, buttonPanel;
	JLabel titleLabel;
	
	/**
	 * Constructor
	 * @param parent	The gameComponent that will send fireEvent on events.
	 * @param title		The title shown above the buttons
	 */
	public MenuPanel (GameComponent parent, String title) {
		super();
		setup(title);
		this.parent = parent;	
	}
	
	/**
	 * Builds the actual menu.
	 * @param title
	 */
	private void setup(String title) {
		titlePanel = buildTitlePanel(title);
		buttonPanel = buildButtonPanel();		
		
		int padding = Integer.parseInt(Config.get("Menu_padding"));
		setBorder(new EmptyBorder(padding, padding, padding, padding));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		corner_r = Integer.parseInt(Config.get("Menu_corner_r"));
		
		this.add(titlePanel);
		this.add(buttonPanel);
		
		this.addMouseListener(this);
	}
	
	/**
	 * A private method that builds a JPanel with the title.
	 * @return		A JPanel with the title
	 */
	private JPanel buildTitlePanel(String title) {
		JPanel titlePanel = new JPanel(new GridLayout(0,1));
		titlePanel.setOpaque(false);
		titleLabel = new JLabel(title, SwingConstants.CENTER);
		titleLabel.setFont(new Font(Font.SERIF, Font.BOLD, Integer.parseInt(Config.get("Menu_title_fontsize"))));
		titlePanel.add(titleLabel);
		return titlePanel;
	}
	
	/**
	 * A private method that builds a JPanel with the buttons.
	 * @return		A JPanel with all the buttons
	 */
	private JPanel buildButtonPanel() {
		JPanel buttonPanel = new JPanel(new GridLayout(0,1,0,Integer.parseInt(Config.get("Menu_button_spacing"))));
		buttonPanel.setOpaque(false);
		buttonPanel.setMaximumSize(new Dimension(Integer.parseInt(Config.get("Menu_max_button_width")),100));
		return buttonPanel;
	}
	
	/**
	 * method to add buttons.
	 * @param text				The text on the button
	 * @param command			The string sent as a command
	 * @param bg_color
	 * @param border_color
	 * @param highlight_color
	 */
	public void addButton(String text, String command, Color bg_color, Color border_color, Color highlight_color){
		MenuButton button = new MenuButton(text);
		button.setBackground(bg_color);
		button.setBorderColor(border_color);
		button.setHighlightColor(highlight_color);
		button.addActionListener(this);
		button.setActionCommand(command);
		buttonPanel.add(button);
	}
	
	/**
	 * Sets the background color
	 * @param c		The new background color
	 */
	public void setBackground(Color c) {
		bg_color=c;
	}

	/**
	 * Sets the border ground color
	 * @param c		The new border ground color
	 */
	public void setBorderColor(Color c) {
		border_color=c;
	}
	
	/**
	 * Sets the preferred width of the buttons
	 * @param width		The preferred width
	 */
	public void setWidth(int width) {
		buttonPanel.setMaximumSize(new Dimension(width,100));
	}
	
	/**
	 * Paints the menu (the background of it).
	 * @param g_in The graphics object that is used.
	 */
	@Override
	public void paintComponent(Graphics g_in) {
		Graphics2D g = (Graphics2D) g_in;
		int width = this.getWidth();
		int height = this.getHeight();

		g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);

		g.setColor(bg_color);
		//g.fillRect(0, 0, width, height);
		g.fillRoundRect(0, 0, width-1, height-1, corner_r, corner_r);
		g.setColor(border_color);
		g.drawRoundRect(0, 0, width-1, height-1, corner_r, corner_r);
	}
	
	/** Do not paint any borders. */
	@Override public void paintBorder(Graphics g) {}
	
	/**
	 * Needed for the rounded corners. Checks if some coordinate is withinit.
	 * @param x		the x-coordinate
	 * @param y		the y-coordinate
	 */
	@Override
	public boolean contains (int x, int y) {
		int width = this.getWidth();
		int height = this.getHeight();
		return (new RoundRectangle2D.Double(0, 0, width-1, height-1, corner_r, corner_r)).contains(x, y);
	}

	////////////////////////////////
	// Mouse actions: Do nothing. //
	////////////////////////////////
	/** Do nothing */
	@Override public void mouseClicked(MouseEvent arg0) {}
	/** Do nothing */
	@Override public void mouseEntered(MouseEvent arg0) {}
	/** Do nothing */
	@Override public void mouseExited(MouseEvent arg0) {}
	/** Do nothing */
	@Override public void mousePressed(MouseEvent arg0) {}
	/** Do nothing */
	@Override public void mouseReleased(MouseEvent arg0) {}

	/**
	 * When some action has happened it should be sent to the listeners of the panel.
	 * @param e		The ActionEvent that happened
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		parent.fireEvent(e);
	}
	
}
