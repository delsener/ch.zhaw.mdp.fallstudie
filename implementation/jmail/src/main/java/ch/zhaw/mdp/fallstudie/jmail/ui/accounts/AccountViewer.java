package ch.zhaw.mdp.fallstudie.jmail.ui.accounts;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ch.zhaw.mdp.fallstudie.jmail.core.Account;
import ch.zhaw.mdp.fallstudie.jmail.core.AccountUtil;
import ch.zhaw.mdp.fallstudie.jmail.ui.MessageBox;
import ch.zhaw.mdp.fallstudie.jmail.ui.components.SolidJSplitPane;

public class AccountViewer extends JDialog {

	/** Generated <code>serialVersionUID</code>. */
	private static final long serialVersionUID = 4541971923461342504L;

	private final MessageBox messageBox;

	private Account currentAccount;
	private Map<String, JTextField> bindings = new HashMap<String, JTextField>();

	private JList<Account> accountList;

	public AccountViewer(MessageBox messageBox) {
		this.messageBox = messageBox;
		configureDialog();
		List<Account> accounts = AccountUtil.loadAccounts();
		if (accounts == null) {
			return;
		}

		accountList.setListData(accounts.toArray(new Account[accounts.size()]));
		this.setVisible(true);
		this.pack();
	}

	public Account getCurrentAccount() {
		return currentAccount;
	}

	public void setCurrentAccount(Account currentAccount) {
		writeToModel();
		this.currentAccount = currentAccount;
		writeFromModel();
		updateEnablement();
	}

	private void configureDialog() {
		this.setTitle("Accounts");
		this.setModal(true);
		this.setSize(800, 600);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		createContent();
	}

	private void createContent() {
		// general settings (layout, ...)
		this.setLayout(new BorderLayout());

		// account list
		accountList = new JList<Account>();
		accountList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				setCurrentAccount(accountList.getSelectedValue());
			}
		});

		// account detail view
		JPanel accountDetail = accountDetailPanel();

		// split pane
		SolidJSplitPane splitPane = new SolidJSplitPane(
				JSplitPane.HORIZONTAL_SPLIT, accountList, accountDetail, 0.3);
		splitPane.setDividerLocation(150);
		this.add(splitPane, BorderLayout.CENTER);
	}

	private JPanel accountDetailPanel() {
		JPanel container = new JPanel(new BorderLayout());

		JPanel accountDetail = new JPanel();
		accountDetail.setLayout(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(5, 8, 5, 5);

		// -- account name
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 0.0;
		constraints.fill = GridBagConstraints.NONE;

		JLabel labelAccountName = new JLabel("Account name");
		accountDetail.add(labelAccountName, constraints);

		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.weightx = 1.0;
		constraints.fill = GridBagConstraints.HORIZONTAL;

		JTextField textAccountName = new JTextField();
		accountDetail.add(textAccountName, constraints);
		bindings.put("accountName", textAccountName);

		// -- email name
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weightx = 0.0;
		constraints.fill = GridBagConstraints.NONE;

		JLabel labelEmailName = new JLabel(
				"Email name (used when sending mails)");
		accountDetail.add(labelEmailName, constraints);

		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.weightx = 1.0;
		constraints.fill = GridBagConstraints.HORIZONTAL;

		JTextField textEmailName = new JTextField();
		accountDetail.add(textEmailName, constraints);
		bindings.put("emailName", textEmailName);

		// -- email address
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.weightx = 0.0;
		constraints.fill = GridBagConstraints.NONE;

		JLabel labelAddress = new JLabel("Email address");
		accountDetail.add(labelAddress, constraints);

		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.weightx = 1.0;
		constraints.fill = GridBagConstraints.HORIZONTAL;

		JTextField textAddress = new JTextField();
		accountDetail.add(textAddress, constraints);
		bindings.put("address", textAddress);

		// -- in server
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth = 2;
		constraints.weightx = 1.0;
		constraints.insets = new Insets(0, 0, 0, 0);
		constraints.fill = GridBagConstraints.HORIZONTAL;

		JPanel inServerPanel = new JPanel(new GridBagLayout());
		inServerPanel.setBorder(BorderFactory
				.createTitledBorder("Incoming Server"));
		accountDetail.add(inServerPanel, constraints);

		fillMailServerArea(0, inServerPanel, constraints);

		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.gridwidth = 2;
		constraints.weightx = 1.0;
		constraints.insets = new Insets(0, 0, 0, 0);
		constraints.fill = GridBagConstraints.HORIZONTAL;

		JPanel outServerPanel = new JPanel(new GridBagLayout());
		outServerPanel.setBorder(BorderFactory
				.createTitledBorder("Outgoing Server"));
		accountDetail.add(outServerPanel, constraints);

		fillMailServerArea(1, outServerPanel, constraints);

		container.add(accountDetail, BorderLayout.NORTH);

		JPanel buttonContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		Button newButton = new Button("New Account");
		newButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				List<Account> accounts = new ArrayList<Account>();
				for (int i = 0; i < accountList.getModel().getSize(); i++) {
					accounts.add(accountList.getModel().getElementAt(i));
				}
				
				Account newAccount = new Account();
				newAccount.setAccountName("Account Name");
				accounts.add(newAccount);

				accountList.setListData(accounts.toArray(new Account[accounts
						.size()]));
				accountList.setSelectedValue(newAccount, true);
				setCurrentAccount(newAccount);
			}
		});
		buttonContainer.add(newButton);
		buttonContainer.add(new JSeparator(JSeparator.VERTICAL));

		Button resetButton = new Button("Reset");
		resetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				List<Account> accounts = AccountUtil.loadAccounts();
				accountList.setListData(accounts.toArray(new Account[accounts
						.size()]));
				setCurrentAccount(null);
			}
		});

		buttonContainer.add(resetButton);

		Button saveButton = new Button("Save");
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (currentAccount == null) {
					return;
				}

				writeToModel();

				List<Account> accounts = new ArrayList<Account>();
				for (int i = 0; i < accountList.getModel().getSize(); i++) {
					accounts.add(accountList.getModel().getElementAt(i));
				}
				AccountUtil.saveAccounts(accounts);
				Account selectedValue = accountList.getSelectedValue();
				selectedValue.setAccountName(currentAccount.getAccountName());
				accountList.setModel(accountList.getModel());
				accountList.setSelectedValue(selectedValue, true);
				messageBox.reloadNodes();
			}
		});

		buttonContainer.add(saveButton);

		Button closeButton = new Button("Close");
		closeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		buttonContainer.add(closeButton);

		container.add(buttonContainer, BorderLayout.SOUTH);

		updateEnablement();

		return container;
	}

	private void fillMailServerArea(int id, JPanel group,
			GridBagConstraints constraints) {
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.gridwidth = 1;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 0.0;
		constraints.fill = GridBagConstraints.NONE;

		JLabel labelInServerHost = new JLabel("Host");
		group.add(labelInServerHost, constraints);

		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.weightx = 1.0;
		constraints.fill = GridBagConstraints.HORIZONTAL;

		JTextField textServerInHost = new JTextField();
		group.add(textServerInHost, constraints);
		bindings.put(id + "host", textServerInHost);

		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weightx = 0.0;
		constraints.fill = GridBagConstraints.NONE;

		JLabel labelInServerPort = new JLabel("Port");
		group.add(labelInServerPort, constraints);

		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.weightx = 1.0;
		constraints.fill = GridBagConstraints.HORIZONTAL;

		JTextField textServerInPort = new JTextField();
		group.add(textServerInPort, constraints);
		bindings.put(id + "port", textServerInPort);

		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.weightx = 0.0;
		constraints.fill = GridBagConstraints.NONE;

		JLabel labelInServerUsername = new JLabel("Username");
		group.add(labelInServerUsername, constraints);

		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.weightx = 1.0;
		constraints.fill = GridBagConstraints.HORIZONTAL;

		JTextField textServerInUsername = new JTextField();
		group.add(textServerInUsername, constraints);
		bindings.put(id + "username", textServerInUsername);

		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.weightx = 0.0;
		constraints.fill = GridBagConstraints.NONE;

		JLabel labelInServerPassword = new JLabel("Password");
		group.add(labelInServerPassword, constraints);

		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.weightx = 1.0;
		constraints.fill = GridBagConstraints.HORIZONTAL;

		JTextField textServerInPassword = new JPasswordField();
		group.add(textServerInPassword, constraints);
		bindings.put(id + "password", textServerInPassword);
	}

	private void updateEnablement() {
		for (JTextField textField : bindings.values()) {
			textField.setEnabled(currentAccount != null);
			textField.setEditable(currentAccount != null);
		}
	}

	private void writeToModel() {
		if (currentAccount == null) {
			return;
		}
		
		currentAccount.setAccountName(bindings.get("accountName").getText());
		currentAccount.setEmailName(bindings.get("emailName").getText());
		currentAccount.setAddress(bindings.get("address").getText());

		currentAccount.getInServer().setHost(bindings.get("0host").getText());
		currentAccount.getInServer().setPort(
				Integer.valueOf(bindings.get("0port").getText()));
		currentAccount.getInServer().setUsername(
				bindings.get("0username").getText());
		currentAccount.getInServer().setPassword(
				bindings.get("0password").getText());

		currentAccount.getOutServer().setHost(bindings.get("1host").getText());
		currentAccount.getOutServer().setPort(
				Integer.valueOf(bindings.get("1port").getText()));
		currentAccount.getOutServer().setUsername(
				bindings.get("1username").getText());
		currentAccount.getOutServer().setPassword(
				bindings.get("1password").getText());
	}

	private void writeFromModel() {
		if (currentAccount == null) {
			for (JTextField textField : bindings.values()) {
				textField.setText("");
			}
			return;
		}

		bindings.get("accountName").setText(currentAccount.getAccountName());
		bindings.get("emailName").setText(currentAccount.getEmailName());
		bindings.get("address").setText(currentAccount.getAddress());

		bindings.get("0host").setText(currentAccount.getInServer().getHost());
		bindings.get("0port").setText(
				String.valueOf(currentAccount.getInServer().getPort()));
		bindings.get("0username").setText(
				currentAccount.getInServer().getUsername());
		bindings.get("0password").setText(
				currentAccount.getInServer().getPassword());

		bindings.get("1host").setText(currentAccount.getOutServer().getHost());
		bindings.get("1port").setText(
				String.valueOf(currentAccount.getOutServer().getPort()));
		bindings.get("1username").setText(
				currentAccount.getOutServer().getUsername());
		bindings.get("1password").setText(
				currentAccount.getOutServer().getPassword());
	}
}
