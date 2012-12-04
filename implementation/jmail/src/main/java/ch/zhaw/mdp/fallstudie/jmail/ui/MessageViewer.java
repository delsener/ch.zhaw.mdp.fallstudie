package ch.zhaw.mdp.fallstudie.jmail.ui;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import ch.zhaw.mdp.fallstudie.jmail.core.MailMessage;
import ch.zhaw.mdp.fallstudie.jmail.core.Recipient;

public class MessageViewer implements ListSelectionListener {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

	private final JScrollPane scrollPane;
	private final JTable table;
	private final DefaultTableModel model = new DefaultTableModel(new String[] { "Subject", "From", "Date" }, 0);
	private final List<MailMessage> messages;
	private final List<MessageSelectionListener> selectionListeners = new LinkedList<MessageSelectionListener>();

	public MessageViewer() {
		this.table = new JTable(this.model);
		this.table.setShowGrid(false);
		this.table.setShowHorizontalLines(true);
		this.table.getSelectionModel().addListSelectionListener(this);

		this.scrollPane = new JScrollPane(this.table);
		this.scrollPane.getViewport().setBackground(Color.WHITE);
		this.scrollPane.setBorder(BorderFactory.createEmptyBorder());

		this.messages = new ArrayList<MailMessage>();

		// DUMMY VALUES
		this.generateMockMessages();
	}

	public JComponent getComponent() {
		return this.scrollPane;
	}

	private void addMessages(List<MailMessage> messages) {
		for (MailMessage message : messages) {
			Vector<String> row = new Vector<String>();
			row.add(message.getSubject());
			row.add(message.getSender().getAddress());
			row.add(MessageViewer.dateFormat.format(message.getTimeSent()));
			this.model.addRow(row);
			this.messages.add(message);
		}
	}

	public void addMessageSelectionListener(MessageSelectionListener listener) {
		this.selectionListeners.add(listener);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		final int selectedRow = this.table.getSelectedRow();
		if (selectedRow == -1 || selectedRow >= this.messages.size()) {
			return;
		}

		MailMessage message = this.messages.get(selectedRow);

		for (MessageSelectionListener listener : this.selectionListeners) {
			listener.messageSelected(message);
		}
	}

	public void filterMessages(String filter) {
		this.model.setRowCount(0);
		this.messages.clear();
		// TODO: filter messages
	}

	private void generateMockMessages() {
		// receivers
		List<Recipient> receivers = new ArrayList<Recipient>();
		receivers.add(new Recipient("roger.knecht@students.zhaw.ch"));
		receivers.add(new Recipient("david.elsener@students.zhaw.ch"));
		receivers.add(new Recipient("oliver.streuli@students.zhaw.ch"));
		receivers.add(new Recipient("markus.peloso@students.zhaw.ch"));

		// mail messages
		List<MailMessage> messages = new ArrayList<MailMessage>();
		for (int i = 0; i < 10; i++) {
			MailMessage mailMessage = new MailMessage(new Recipient("jmail@zhaw.ch"), receivers, "Mail-Content of the mail number " + (i + 1) + "!", "Mail " + (i + 1));
			messages.add(mailMessage);
		}

		this.addMessages(messages);
	}
}
