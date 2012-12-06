package ch.zhaw.mdp.fallstudie.jmail.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ch.zhaw.mdp.fallstudie.jmail.core.messages.MailMessage;
import ch.zhaw.mdp.fallstudie.jmail.ui.components.SolidJSplitPane;

public class ReadingPane extends JPanel {

	private static final long serialVersionUID = 286297803231473534L;
	private static Font PLAIN_FONT = new Font("Sans Serif", Font.PLAIN, 11);
	private static Font BOLD_FONT = new Font("Sans Serif", Font.BOLD, 11);

	private JLabel from = new JLabel();
	private JLabel to = new JLabel();
	private JLabel subject = new JLabel();
	private JEditorPane message = new JEditorPane();
	private JPanel infoPanel;

	public ReadingPane() {
		super(new BorderLayout());

		JPanel actionPanel = new JPanel();
		actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.X_AXIS));
		actionPanel.add(new JButton("Reply"));

		infoPanel = new JPanel(new GridBagLayout());
		infoPanel.setBackground(new Color(248, 248, 248));
		infoPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
				SolidJSplitPane.BORDER_COLOR));

		addInfoPanelItem(new JLabel("Subject"), BOLD_FONT, Color.GRAY, 0, 0, 0);
		addInfoPanelItem(subject, BOLD_FONT, Color.BLACK, 1, 0, 1);
		addInfoPanelItem(new JLabel("From"), PLAIN_FONT, Color.GRAY, 0, 1, 0);
		addInfoPanelItem(from, PLAIN_FONT, Color.BLACK, 1, 1, 1);
		addInfoPanelItem(new JLabel("To"), PLAIN_FONT, Color.GRAY, 0, 2, 0);
		addInfoPanelItem(to, PLAIN_FONT, Color.BLACK, 1, 2, 1);

		message.setContentType("text/html");
		message.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		message.setEditable(false);

		//Put the editor pane in a scroll pane.
		JScrollPane messageScrollPane = new JScrollPane(message);
		messageScrollPane.setVerticalScrollBarPolicy(
		                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		messageScrollPane.setPreferredSize(new Dimension(250, 145));
		messageScrollPane.setMinimumSize(new Dimension(10, 10));

		// this.add(actionPanel, BorderLayout.NORTH);
		this.add(infoPanel, BorderLayout.NORTH);
		this.add(messageScrollPane, BorderLayout.CENTER);
	}

	public void setMessage(MailMessage message) {
		this.message.setText(message.getContent());
		this.from.setText(message.getSender().getAddress());
		this.to.setText(message.getRecipientsDisplayString());
		this.subject.setText(message.getSubject());
	}

	private void addInfoPanelItem(JComponent component, Font font, Color color,
			int x, int y, int weight) {
		Insets insets = new Insets(0, 10, 5, 0);
		GridBagConstraints c = new GridBagConstraints(x, y, 1, 1, weight, 1,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, insets,
				0, 0);
		component.setFont(font);
		component.setForeground(color);
		infoPanel.add(component, c);
	}
}
