package ch.zhaw.mdp.fallstudie.jmail.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

import ch.zhaw.mdp.fallstudie.jmail.core.commands.MailCommand;
import ch.zhaw.mdp.fallstudie.jmail.core.messages.MailMessage;
import ch.zhaw.mdp.fallstudie.jmail.core.sendreceive.ReceiverThread;
import ch.zhaw.mdp.fallstudie.jmail.ui.accounts.AccountViewer;
import ch.zhaw.mdp.fallstudie.jmail.ui.components.SolidJSplitPane;
import ch.zhaw.mdp.fallstudie.jmail.ui.dialogs.MessageDialog;

public class MainFrame implements MessageSelectionListener {

	private final JFrame frame = new JFrame("JMail");
	private final JMenuBar menubar = new JMenuBar();
	private final ReadingPane readingPane = new ReadingPane();
	private final MessageViewer messageViewer = new MessageViewer();
	private final StatusBar statusBar = new StatusBar();
	private MessageBox messageBox;

	private final ActionListener mailActionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// receive
			if (MailCommand.RECEIVE.name().equals(e.getActionCommand())) {
				Thread receiverThread = new ReceiverThread(MainFrame.this.messageViewer, MainFrame.this.statusBar);
				receiverThread.start();
				return;
			}

			// create
			if (MailCommand.CREATE.name().equals(e.getActionCommand())) {
				MessageDialog messageDialog = new MessageDialog(MainFrame.this.messageViewer, MainFrame.this.statusBar);
				messageDialog.setVisible(true);
				return;
			}

			System.err.print("Action with command " + e.getActionCommand() + " is unknown.");
		}
	};;

	public void createAndShowGUI() {
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// menubar
		this.frame.setJMenuBar(this.createMenuBar());

		// toolbar
		JToolBar toolBar = new JToolBar("Still draggable");
		this.addToolbarButtons(toolBar);
		this.frame.add(toolBar, BorderLayout.PAGE_START);

		// content
		Component content = new SolidJSplitPane(JSplitPane.VERTICAL_SPLIT, this.messageViewer.getComponent(), this.readingPane, 0.8);
		this.messageViewer.addMessageSelectionListener(this);

		this.messageBox = new MessageBox(this.messageViewer);
		this.frame.add(new SolidJSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.messageBox.getComponent(), content, 0.3), BorderLayout.CENTER);

		// statusbar
		this.frame.add(this.statusBar, BorderLayout.SOUTH);
		this.statusBar.setStatus("Ready");

		this.frame.setLocationRelativeTo(null);
		this.frame.setExtendedState(this.frame.getExtendedState() | Frame.MAXIMIZED_BOTH);
		Dimension frameSize = Toolkit.getDefaultToolkit().getScreenSize();
		frameSize.setSize(frameSize.getWidth() * 0.8, frameSize.getHeight() * 0.8);
		this.frame.setSize(frameSize);
		this.frame.setVisible(true);
	}

	private JMenuBar createMenuBar() {
		this.menubar.setBackground(new Color(233, 239, 245));
		this.menubar.setBorder(BorderFactory.createEmptyBorder());

		// menu items
		JMenuItem menuItemNewMessage = new JMenuItem("New message..");
		menuItemNewMessage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MessageDialog messageDialog = new MessageDialog(MainFrame.this.messageViewer, MainFrame.this.statusBar);
				messageDialog.setVisible(true);
			}
		});

		JMenuItem menuItemAccounts = new JMenuItem("Accounts..");
		menuItemAccounts.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new AccountViewer(MainFrame.this.messageBox);
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
		this.menubar.add(menuFile);
		this.menubar.add(menuEdit);
		this.menubar.add(menuView);
		this.menubar.add(menuHelp);

		return this.menubar;
	}

	@Override
	public void messageSelected(MailMessage message) {
		this.readingPane.setMessage(message);
	}

	protected void addToolbarButtons(JToolBar toolBar) {
		JButton button = null;

		// receive button
		button = this.makeNavigationButton("mail_receive.png", "Receive all mails", "Receive", MailCommand.RECEIVE);
		toolBar.add(button);

		// write button
		button = this.makeNavigationButton("mail_create.png", "Write a new mail", "Write", MailCommand.CREATE);
		toolBar.add(button);

		// reply button
		button = this.makeNavigationButton("mail_reply.png", "Reply to selected mail", "Reply", MailCommand.REPLY);
		toolBar.add(button);

		// forward button
		button = this.makeNavigationButton("mail_forward.png", "Forward selected mail", "Forward", MailCommand.FORWARD);
		toolBar.add(button);
	}

	protected JButton makeNavigationButton(String iconName, String toolTipText, String altText, MailCommand command) {
		// Look for the image.
		String imgLocation = "/icons/" + iconName;
		URL imageURL = MainFrame.class.getResource(imgLocation);

		// Create and initialize the button.
		JButton button = new JButton(altText);
		button.setActionCommand(command.name());
		button.setToolTipText(toolTipText);
		button.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
		button.addActionListener(this.mailActionListener);

		if (imageURL != null) { // image found
			button.setIcon(new ImageIcon(imageURL, altText));
		}
		else { // no image found
			button.setText(altText);
			System.err.println("Resource not found: " + imgLocation);
		}
		return button;
	}
}
