package ch.zhaw.mdp.fallstudie.jmail.ui.components;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Rectangle;

import javax.swing.JPanel;

public class GradientJPanel extends JPanel {

	private static final long serialVersionUID = 7229640831295439108L;

	private Color bottom;
	private Color top;

	public GradientJPanel(LayoutManager layout, Color bottom, Color top) {
		super(layout);
		this.bottom = bottom;
		this.top = top;
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Rectangle bounds = g.getClipBounds();

		GradientPaint paint = new GradientPaint(0, 0, top, 0, bounds.height, bottom);
		g2d.setPaint(paint);
		g2d.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
	}
}
