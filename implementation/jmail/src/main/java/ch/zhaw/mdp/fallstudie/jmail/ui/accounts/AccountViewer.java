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
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ch.zhaw.mdp.fallstudie.jmail.core.account.Account;
import ch.zhaw.mdp.fallstudie.jmail.core.account.AccountUtil;
import ch.zhaw.mdp.fallstudie.jmail.ui.MessageBox;
import ch.zhaw.mdp.fallstudie.jmail.ui.components.SolidJSplitPane;

public class AccountViewer extends JDialog {

	/** Generated <code>serialVersionUID</code>. */
	private static final long serialVersionUID = 4541971923461342504L;

	private final MessageBox messageBox;

	private Account currentAccount;
	private final Map<String, JTextField> textBindings = new HashMap<String, JTextField>();
	private final Map<String, JCheckBox> booleanBindings = new HashMap<String, JCheckBox>();

	private JList<Account> accountList;

	public AccountViewer(MessageBox messageBox) {
		this.messageBox = messageBox;
		this.configureDialog();
		List<Account> accounts = AccountUtil.loadAccounts();
		if (accounts == null) {
			return;
		}

		this.accountList.setListData(accounts.toArray(new Account[accounts
				.size()]));
		this.setVisible(true);
		this.pack();
	}

	public Account getCurrentAccount() {
		return this.currentAccount;
	}

	public void setCurrentAccount(Account currentAccount) {
		this.writeToModel();
		this.currentAccount = currentAccount;
		this.writeFromModel();
		this.updateEnablement();
	}

	private void configureDialog() {
		this.setTitle("Accounts");
		this.setModal(true);
		this.setSize(800, 600);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.createContent();
	}

	private void createContent() {
		// general settings (layout, ...)
		this.setLayout(new BorderLayout());

		// account list
		this.accountList = new JList<Account>();
		this.accountList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				AccountViewer.this
						.setCurrentAccount((Account) AccountViewer.this.accountList
								.getSelectedValue());
			}
		});

		// account detail view
		JPanel accountDetail = this.accountDetailPanel();

		// split pane
		SolidJSplitPane splitPane = new SolidJSplitPane(
				JSplitPane.HORIZONTAL_SPLIT, this.accountList, accountDetail,
				0.3);
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
		this.textBindings.put("accountName", textAccountName);

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
		this.textBindings.put("emailName", textEmailName);

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
		this.textBindings.put("address", textAddress);

		// -- email address
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.weightx = 0.0;
		constraints.fill = GridBagConstraints.NONE;

		JLabel labelSSLAuth = new JLabel("Use SSL/TLS authentication");
		accountDetail.add(labelSSLAuth, constraints);

		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.weightx = 1.0;
		constraints.fill = GridBagConstraints.HORIZONTAL;

		JCheckBox textSSLAuth = new JCheckBox();
		accountDetail.add(textSSLAuth, constraints);
		this.booleanBindings.put("sslAuth", textSSLAuth);

		// -- in server
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.gridwidth = 2;
		constraints.weightx = 1.0;
		constraints.insets = new Insets(0, 0, 0, 0);
		constraints.fill = GridBagConstraints.HORIZONTAL;

		JPanel inServerPanel = new JPanel(new GridBagLayout());
		inServerPanel.setBorder(BorderFactory
				.createTitledBorder("Incoming Server"));
		accountDetail.add(inServerPanel, constraints);

		this.fillMailServerArea(0, inServerPanel, constraints);

		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.gridwidth = 2;
		constraints.weightx = 1.0;
		constraints.insets = new Insets(0, 0, 0, 0);
		constraints.fill = GridBagConstraints.HORIZONTAL;

		JPanel outServerPanel = new JPanel(new GridBagLayout());
		outServerPanel.setBorder(BorderFactory
				.createTitledBorder("Outgoing Server"));
		accountDetail.add(outServerPanel, constraints);

		this.fillMailServerArea(1, outServerPanel, constraints);

		container.add(accountDetail, BorderLayout.NORTH);

		JPanel buttonContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		Button newButton = new Button("New Account");
		newButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				List<Account> accounts = new ArrayList<Account>();
				for (int i = 0; i < AccountViewer.this.accountList.getModel()
						.getSize(); i++) {
					accounts.add((Account) AccountViewer.this.accountList
							.getModel().getElementAt(i));
				}

				Account newAccount = new Account();
				newAccount.setAccountName("Account Name");
				accounts.add(newAccount);

				AccountViewer.this.accountList.setListData(accounts
						.toArray(new Account[accounts.size()]));
				AccountViewer.this.accountList.setSelectedValue(newAccount,
						true);
				AccountViewer.this.setCurrentAccount(newAccount);
			}
		});
		buttonContainer.add(newButton);
		buttonContainer.add(new JSeparator(SwingConstants.VERTICAL));

		Button resetButton = new Button("Reset");
		resetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				List<Account> accounts = AccountUtil.loadAccounts();
				AccountViewer.this.accountList.setListData(accounts
						.toArray(new Account[accounts.size()]));
				AccountViewer.this.setCurrentAccount(null);
			}
		});

		buttonContainer.add(resetButton);

		Button saveButton = new Button("Save");
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (AccountViewer.this.currentAccount == null) {
					return;
				}

				AccountViewer.this.writeToModel();

				List<Account> accounts = new ArrayList<Account>();
				for (int i = 0; i < AccountViewer.this.accountList.getModel()
						.getSize(); i++) {
					accounts.add((Account) AccountViewer.this.accountList
							.getModel().getElementAt(i));
				}
				AccountUtil.saveAccounts(accounts);
				Account selectedValue = (Account) AccountViewer.this.accountList
						.getSelectedValue();
				selectedValue.setAccountName(AccountViewer.this.currentAccount
						.getAccountName());
				AccountViewer.this.accountList
						.setModel(AccountViewer.this.accountList.getModel());
				AccountViewer.this.accountList.setSelectedValue(selectedValue,
						true);
				AccountViewer.this.messageBox.reloadNodes();
			}
		});

		buttonContainer.add(saveButton);

		Button closeButton = new Button("Close");
		closeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AccountViewer.this.dispose();
			}
		});

		buttonContainer.add(closeButton);

		container.add(buttonContainer, BorderLayout.SOUTH);

		this.updateEnablement();

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
		this.textBindings.put(id + "host", textServerInHost);

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
		this.textBindings.put(id + "port", textServerInPort);

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
		this.textBindings.put(id + "username", textServerInUsername);

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
		this.textBindings.put(id + "password", textServerInPassword);
	}

	private void updateEnablement() {
		for (JTextField textField : this.textBindings.values()) {
			textField.setEnabled(this.currentAccount != null);
			textField.setEditable(this.currentAccount != null);
		}
	}

	private void writeToModel() {
		if (this.currentAccount == null) {
			return;
		}

		this.currentAccount.setAccountName(this.textBindings.get("accountName")
				.getText());
		this.currentAccount.setEmailName(this.textBindings.get("emailName")
				.getText());
		this.currentAccount.setAddress(this.textBindings.get("address").getText());
		this.currentAccount.setUseSSLAuth(this.booleanBindings.get("sslAuth").isSelected());

		this.currentAccount.getInServer().setHost(
				this.textBindings.get("0host").getText());
		this.currentAccount.getInServer().setPort(
				Integer.valueOf(this.textBindings.get("0port").getText()));
		this.currentAccount.getInServer().setUsername(
				this.textBindings.get("0username").getText());
		this.currentAccount.getInServer().setPassword(
				this.textBindings.get("0password").getText());

		this.currentAccount.getOutServer().setHost(
				this.textBindings.get("1host").getText());
		this.currentAccount.getOutServer().setPort(
				Integer.valueOf(this.textBindings.get("1port").getText()));
		this.currentAccount.getOutServer().setUsername(
				this.textBindings.get("1username").getText());
		this.currentAccount.getOutServer().setPassword(
				this.textBindings.get("1password").getText());
	}

	private void writeFromModel() {
		if (this.currentAccount == null) {
			for (JTextField textField : this.textBindings.values()) {
				textField.setText("");
			}
			return;
		}

		this.textBindings.get("accountName").setText(
				this.currentAccount.getAccountName());
		this.textBindings.get("emailName").setText(
				this.currentAccount.getEmailName());
		this.textBindings.get("address").setText(this.currentAccount.getAddress());
		this.booleanBindings.get("sslAuth").setSelected(this.currentAccount.isUseSSLAuth());

		this.textBindings.get("0host").setText(
				this.currentAccount.getInServer().getHost());
		this.textBindings.get("0port").setText(
				String.valueOf(this.currentAccount.getInServer().getPort()));
		this.textBindings.get("0username").setText(
				this.currentAccount.getInServer().getUsername());
		this.textBindings.get("0password").setText(
				this.currentAccount.getInServer().getPassword());

		this.textBindings.get("1host").setText(
				this.currentAccount.getOutServer().getHost());
		this.textBindings.get("1port").setText(
				String.valueOf(this.currentAccount.getOutServer().getPort()));
		this.textBindings.get("1username").setText(
				this.currentAccount.getOutServer().getUsername());
		this.textBindings.get("1password").setText(
				this.currentAccount.getOutServer().getPassword());
	}
}
