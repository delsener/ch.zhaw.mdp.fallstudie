package ch.zhaw.mdp.fallstudie.jmail.ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class StatusBar extends JPanel {

	private static final long serialVersionUID = -2347620241633076348L;

	private JLabel status = new JLabel();

	public StatusBar() {
		setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));
		setPreferredSize(new Dimension(0, 18));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		add(status);
		add(new JSeparator(JSeparator.VERTICAL));
	}

	public void setStatus(String status) {
		this.status.setText(status);
	}
}
