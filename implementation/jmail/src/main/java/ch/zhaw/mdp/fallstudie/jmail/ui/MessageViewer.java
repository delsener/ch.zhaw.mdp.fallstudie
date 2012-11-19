package ch.zhaw.mdp.fallstudie.jmail.ui;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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

import ch.zhaw.mdp.fallstudie.jmail.core.Message;
import ch.zhaw.mdp.fallstudie.jmail.core.Receiver;

public class MessageViewer implements ListSelectionListener {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"dd.MM.yyyy HH:mm");

	private JScrollPane scrollPane;
	private JTable table;
	private DefaultTableModel model = new DefaultTableModel(new String[] {
			"Subject", "From", "Date"}, 0);
	private List<Message> messages;
	private List<MessageSelectionListener> selectionListeners = new LinkedList<MessageSelectionListener>();

	public MessageViewer() {
		table = new JTable(model);
		table.setShowGrid(false);
		table.setShowHorizontalLines(true);
		table.getSelectionModel().addListSelectionListener(this);

		scrollPane = new JScrollPane(table);
		scrollPane.getViewport().setBackground(Color.WHITE);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());

		messages = new ArrayList<Message>();
		
		// DUMMY VALUES
		generateMockMessages();
	}

	public JComponent getComponent() {
		return scrollPane;
	}

	private void addMessages(List<Message> messages) {
		for (Message message : messages) {
			Vector<String> row = new Vector<String>();
			row.add(message.getSubject());
			row.add(message.getSender().getRecipient());
			row.add(dateFormat.format(message.getCreationTime()));
			model.addRow(row);
			this.messages.add(message);
		}
	}

	public void addMessageSelectionListener(MessageSelectionListener listener) {
		this.selectionListeners.add(listener);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		final int selectedRow = table.getSelectedRow();
		if (selectedRow == -1 || selectedRow >= messages.size()) {
			return;
		}

		Message message = messages.get(selectedRow);

		for (MessageSelectionListener listener : selectionListeners) {
			listener.messageSelected(message);
		}
	}

	public void filterMessages(String filter) {
		model.setRowCount(0);
		messages.clear();
		// TODO: filter messages
	}
	
	private void generateMockMessages() {
		// receivers
		List<Receiver> receivers = new ArrayList<Receiver>();
		receivers.add(new Receiver("roger.knecht@students.zhaw.ch"));
		receivers.add(new Receiver("david.elsener@students.zhaw.ch"));
		receivers.add(new Receiver("oliver.streuli@students.zhaw.ch"));
		receivers.add(new Receiver("markus.peloso@students.zhaw.ch"));

		// mail messages
		List<Message> messages = new ArrayList<Message>();
		GregorianCalendar calendar = new GregorianCalendar();
		for (int i = 0; i < 10; i++) {
			calendar.add(Calendar.MINUTE, 1 * i);
			Message mailMessage = new Message(new Receiver("jmail@zhaw.ch"), receivers,
					"Mail-Content of the mail number " + (i + 1) + "!", "Mail "
							+ (i + 1), calendar.getTime());
			messages.add(mailMessage);
		}
		
		addMessages(messages);
	}
}
