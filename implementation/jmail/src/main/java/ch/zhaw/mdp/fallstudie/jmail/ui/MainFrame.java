package ch.zhaw.mdp.fallstudie.jmail.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;

import ch.zhaw.mdp.fallstudie.jmail.core.Message;
import ch.zhaw.mdp.fallstudie.jmail.ui.accounts.AccountViewer;
import ch.zhaw.mdp.fallstudie.jmail.ui.components.SolidJSplitPane;

public class MainFrame implements MessageSelectionListener {


	private JFrame frame = new JFrame("JMail");
	private JMenuBar menubar = new JMenuBar();
	private ReadingPane readingPane = new ReadingPane();
	private MessageViewer messageViewer = new MessageViewer();
	private StatusBar statusBar = new StatusBar();
	private MessageBox messageBox;

	public void createAndShowGUI() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// menubar
		frame.setJMenuBar(createMenuBar());

		// content
		Component content = new SolidJSplitPane(JSplitPane.VERTICAL_SPLIT,
				messageViewer.getComponent(), readingPane, 0.8);
		messageViewer.addMessageSelectionListener(this);

		messageBox = new MessageBox(messageViewer);
		frame.add(new SolidJSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				messageBox.getComponent(), content, 0.3),
				BorderLayout.CENTER);

		// statusbar
		frame.add(statusBar, BorderLayout.SOUTH);
		statusBar.setStatus("Ready");

		frame.setLocationRelativeTo(null);
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		Dimension frameSize = Toolkit.getDefaultToolkit().getScreenSize();
		frameSize.setSize(frameSize.getWidth() * 0.8,
				frameSize.getHeight() * 0.8);
		frame.setSize(frameSize);
		frame.setVisible(true);
	}

	private JMenuBar createMenuBar() {
		menubar.setBackground(new Color(233, 239, 245));
		menubar.setBorder(BorderFactory.createEmptyBorder());

		// menu items
		JMenuItem menuItemNewMessage = new JMenuItem("New message..");
		menuItemNewMessage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: Create new message (open dialog ..)
			}
		});
		
		JMenuItem menuItemAccounts = new JMenuItem("Accounts..");
		menuItemAccounts.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new AccountViewer(messageBox);
			}
		});

		JMenuItem menuItemExit = new JMenuItem("Exit");
		menuItemExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		JMenuItem menuItemHelpAbout = new JMenuItem("About");

		// menus
		JMenu menuFile = new JMenu("File");
		JMenu menuEdit = new JMenu("Edit");
		JMenu menuView = new JMenu("View");
		JMenu menuHelp = new JMenu("Help");

		menuFile.add(menuItemNewMessage);
		menuFile.add(menuItemAccounts);
		menuFile.add(menuItemExit);
		menuHelp.add(menuItemHelpAbout);
		menubar.add(menuFile);
		menubar.add(menuEdit);
		menubar.add(menuView);
		menubar.add(menuHelp);

		return menubar;
	}

	@Override
	public void messageSelected(Message message) {
		readingPane.setMessage(message);
	}
}
