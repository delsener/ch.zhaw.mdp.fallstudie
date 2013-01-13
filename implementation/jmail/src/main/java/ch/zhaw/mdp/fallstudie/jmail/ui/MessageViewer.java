package ch.zhaw.mdp.fallstudie.jmail.ui;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import ch.zhaw.mdp.fallstudie.jmail.core.account.Account;
import ch.zhaw.mdp.fallstudie.jmail.core.messages.MailMessage;
import ch.zhaw.mdp.fallstudie.jmail.core.messages.MessagePersistenceUtil;
import ch.zhaw.mdp.fallstudie.jmail.core.messages.MessageType;

public class MessageViewer implements ListSelectionListener {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"dd.MM.yyyy HH:mm");

	private final JScrollPane scrollPane;
	private final JTable table;
	private final DefaultTableModel model = new DefaultTableModel(new String[] {
			"Subject", "From", "Date" }, 0);
	
	private final Map<MessageType, List<MailMessage>> messageMap;
	private final List<MailMessage> filteredMessages = new ArrayList<MailMessage>();
	
	private final List<MessageSelectionListener> selectionListeners = new LinkedList<MessageSelectionListener>();
	
	private MessageType currentMessageTypeFilter = null;
	private Account currentAccountFilter = null;
	
	public MessageViewer() {
		this.table = new JTable(this.model);
		this.table.setShowGrid(false);
		this.table.setShowHorizontalLines(true);
		this.table.getSelectionModel().addListSelectionListener(this);

		this.scrollPane = new JScrollPane(this.table);
		this.scrollPane.getViewport().setBackground(Color.WHITE);
		this.scrollPane.setBorder(BorderFactory.createEmptyBorder());

		this.messageMap = new HashMap<MessageType, List<MailMessage>>();
		MessageType[] messageTypes = MessageType.values();
		for (MessageType messageType : messageTypes) {
			this.messageMap.put(messageType, new ArrayList<MailMessage>());
			setMessages(messageType, MessagePersistenceUtil.loadMessages(messageType));
		}
		filterMessages(null, null);
	}

	public JComponent getComponent() {
		return this.scrollPane;
	}

	public void setMessages(MessageType messageType, List<MailMessage> messages) {
		this.model.setRowCount(0);
		this.messageMap.get(messageType).clear();
		for (MailMessage message : messages) {
			addMessageToModel(message);
			this.messageMap.get(messageType).add(message);
		}
		this.table.updateUI();
	}
	
	private void addMessageToModel(MailMessage message) {
		Vector<String> row = new Vector<String>();
		row.add(message.getSubject());
		row.add(message.getSender().getAddress());
		if (message.getTimeSent() != null) {
			row.add(MessageViewer.dateFormat.format(message.getTimeSent()));
		}
		this.model.addRow(row);
	}

	public void addMessageSelectionListener(MessageSelectionListener listener) {
		this.selectionListeners.add(listener);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		final int selectedRow = this.table.getSelectedRow();
		if (selectedRow == -1 || selectedRow >= this.filteredMessages.size()) {
			return;
		}
		
		MailMessage message = filteredMessages.get(selectedRow);

		for (MessageSelectionListener listener : this.selectionListeners) {
			listener.messageSelected(message);
		}
	}
	
	public void refreshFilteredMessages() {
		filterMessages(currentMessageTypeFilter, currentAccountFilter);
	}

	public void filterMessages(MessageType messageType, Account account) {
		filteredMessages.clear();
		
		this.model.setRowCount(0);
		this.currentAccountFilter = account;
		this.currentMessageTypeFilter = messageType;
		if (messageType == null || account == null) {
			return;
		}
		
		for (MailMessage mailMessage : messageMap.get(messageType)) {
			if (account == null || account.getAccountName().equals(mailMessage.getAccount().getAccountName())) {
				addMessageToModel(mailMessage);
				filteredMessages.add(mailMessage);
			}
		}
	}
}
