package ch.zhaw.mdp.fallstudie.jmail.ui.dialogs;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;

import ch.zhaw.mdp.fallstudie.jmail.core.Recipient;
import ch.zhaw.mdp.fallstudie.jmail.core.account.Account;
import ch.zhaw.mdp.fallstudie.jmail.core.account.AccountUtil;
import ch.zhaw.mdp.fallstudie.jmail.core.messages.MailMessage;
import ch.zhaw.mdp.fallstudie.jmail.core.sendreceive.SenderThread;
import ch.zhaw.mdp.fallstudie.jmail.ui.MessageViewer;
import ch.zhaw.mdp.fallstudie.jmail.ui.StatusBar;

public class MessageDialog extends JDialog {

	private static final long serialVersionUID = -985524394970720765L;

	private final MessageViewer messageViewer;
	private final StatusBar statusBar;
	private JComboBox<Account> comboAccount;
	private JTextArea textContent;
	private JTextField textSubject;
	private JTextField textRecipients;

	public MessageDialog(MessageViewer messageViewer, StatusBar statusBar) {
		this.messageViewer = messageViewer;
		this.statusBar = statusBar;
		configure();
		createContent();
		pack();
	}

	private void configure() {
		setTitle("New Message");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setResizable(false);
	}

	private void createContent() {
		setLayout(new BorderLayout());

		JPanel container = new JPanel(new GridBagLayout());

		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(5, 5, 5, 5);

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 0;
		constraints.fill = GridBagConstraints.NONE;

		JLabel labelAccount = new JLabel("Account");
		container.add(labelAccount, constraints);

		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.weightx = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;

		comboAccount = new JComboBox<Account>();
		List<Account> accounts = AccountUtil.loadAccounts();
		comboAccount.setModel(new DefaultComboBoxModel<Account>(accounts.toArray(new Account[accounts.size()])));
		container.add(comboAccount, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weightx = 0;
		constraints.fill = GridBagConstraints.NONE;

		JLabel labelRecipient = new JLabel("Recipients");
		container.add(labelRecipient, constraints);

		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.weightx = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;

		textRecipients = new JTextField();
		container.add(textRecipients, constraints);

		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.weightx = 0;
		constraints.fill = GridBagConstraints.NONE;

		JLabel labelSubject = new JLabel("Subject");
		container.add(labelSubject, constraints);

		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.weightx = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;

		textSubject = new JTextField();
		container.add(textSubject, constraints);

		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.weightx = 0;
		constraints.gridheight = 10;
		constraints.fill = GridBagConstraints.NONE;

		JLabel labelContent = new JLabel("Content");
		container.add(labelContent, constraints);

		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 10;
		constraints.fill = GridBagConstraints.BOTH;

		textContent = new JTextArea(10, 42);
		container.add(textContent, constraints);

		constraints.gridx = 0;
		constraints.gridy = 14;
		constraints.weightx = 1;
		constraints.weighty = 0;
		constraints.gridwidth = 2;
		constraints.gridheight = 1;
		constraints.insets = new Insets(5, 5, 5, 2);
		constraints.fill = GridBagConstraints.HORIZONTAL;

		JPanel buttonsArea = new JPanel();
		buttonsArea.setLayout(new GridBagLayout());
		container.add(buttonsArea, constraints);

		constraints.gridwidth = 1;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1.0;
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.EAST;

		JButton okButton = new JButton("Ok");
		buttonsArea.add(okButton, constraints);

		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.weightx = 0.0;
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.EAST;

		JButton cancelButton = new JButton("Cancel");
		buttonsArea.add(cancelButton, constraints);

		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// parse recipients
				String recipientsText = textRecipients.getText().trim();
				if (StringUtils.isEmpty(recipientsText)) {
					displayValidationErrorMessage("Recepients must not be empty");
					return;
				}
				String[] recipientSplits = StringUtils.split(recipientsText, ";");
				List<Recipient> recipients = new ArrayList<Recipient>(recipientSplits.length);
				for (String recipientSplit : recipientSplits) {
					recipients.add(new Recipient(recipientSplit));
				}

				// parse subject
				String subject = textSubject.getText();
				if (StringUtils.isEmpty(subject)) {
					if (JOptionPane.showConfirmDialog(MessageDialog.this, "You didn't specify a subject. Continue?") != JOptionPane.YES_OPTION) {
						return;
					}
				}

				// parse content
				String content = textContent.getText();

				// create mail message
				Account selectedAccount = (Account) comboAccount.getSelectedItem();
				MailMessage mailMessage = new MailMessage(selectedAccount, new Recipient(selectedAccount.getAddress()), recipients, subject, content);

				// send
				new SenderThread(messageViewer, statusBar, mailMessage).start();

				MessageDialog.this.dispose();
			}

			private void displayValidationErrorMessage(String message) {
				JOptionPane.showMessageDialog(MessageDialog.this, message, "Error", JOptionPane.ERROR_MESSAGE);
			}

		});

		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MessageDialog.this.dispose();
			}
		});

		add(container, BorderLayout.NORTH);
		initDefaultValues();
	}

	private void initDefaultValues() {
		// default values for testing
		textRecipients.setText("jmail.test@yahoo.com;mdp.jmail@gmx.ch;david.elsener@gmx.net");
		textSubject.setText("I'm a test mail");
		textContent.setText("I'm the content of the test mail.");
	}

}
