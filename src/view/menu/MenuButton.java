package view.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;

import util.Config;

/**
 * A class that generates a prettier button (than the standard one).
 * @author Gustav
 * @version 2016-03-04
 */

@SuppressWarnings("serial")
public class MenuButton 
extends JButton
{
	private Color bg_color = new Color(200,200,240,200);
	private Color border_color = new Color(100,100,140,200);
	private Color highlight_color = new Color(255,0,255,255);
	private int corner_r = 20;
	
	/**
	 * Constructor that generates a button with some text.
	 * @param string	The text on the button
	 */
	public MenuButton(String string) {
		super(string);
		setContentAreaFilled(false);
		corner_r = Integer.parseInt(Config.get("Menu_corner_r"));
		this.setFont(getFont().deriveFont(Font.BOLD, Integer.parseInt(Config.get("Menu_button_fontsize"))));
	}

	/**
	 * Changes the background color.
	 * @param c the new background color
	 */
	public void setBackground(Color c) {
		bg_color=c;
	}
	
	/**
	 * Changes the border color.
	 * @param c the new border color
	 */
	public void setBorderColor(Color c) {
		border_color=c;
	}
	
	/**
	 * Changes the highlight color (of the text).
	 * @param c the new highlight color
	 */
	public void setHighlightColor(Color c) {
		highlight_color=c;
	}
	
	/**
	 * Paints the button
	 * @param g_in		the graphics object that is used
	 */
	@Override
	public void paintComponent(Graphics g_in) {
		Graphics2D g = (Graphics2D) g_in;
		int width = this.getWidth();
		int height = this.getHeight();

		g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);

		g.setColor(bg_color);
		g.fillRoundRect(0, 0, width-1, height-1, corner_r, corner_r);
		g.setColor(border_color);
		g.drawRoundRect(0, 0, width-1, height-1, corner_r, corner_r);
		
		if (getModel().isRollover())
			g.setColor(highlight_color);
		else if (getModel().isPressed())
			g.setColor(Color.WHITE);
		else
			g.setColor(Color.BLACK);
        
		String text=this.getText();
        g.setFont(this.getFont());
        Rectangle2D r = g.getFont().getStringBounds(text, g.getFontRenderContext());
        g.drawString(text, (int)(width/2-r.getCenterX()),(int)(height/2-r.getCenterY()));
	}
	
	/** Do not paint other borders than in paintComponent */
	@Override public void paintBorder(Graphics g_in) {}
	
	/**
	 * Checks if some coordinates are within the button. Needed to override due to the rounded corners.
	 * @param x the x-coordinate (in pixels) 
	 * @param y the y-coordinate (in pixels) 
	 */
	@Override
	public boolean contains (int x, int y) {
		int width = this.getWidth();
		int height = this.getHeight();
		return (new RoundRectangle2D.Double(0, 0, width-1, height-1, corner_r, corner_r)).contains(x, y);
	}
	
	/**
	 * returns the preferred size of the button. It is calculated based on how much space the text occupies.
	 * @return The preferred size
	 */
	@Override
	public Dimension getPreferredSize() {
		FontMetrics fm = this.getFontMetrics(this.getFont());
		Rectangle2D r = fm.getStringBounds(this.getText(), this.getGraphics());
		return new Dimension((int)r.getWidth()+corner_r,(int)r.getHeight()+corner_r/2);
	}
	
}
