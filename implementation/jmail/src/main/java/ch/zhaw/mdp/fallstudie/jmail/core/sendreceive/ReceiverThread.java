package ch.zhaw.mdp.fallstudie.jmail.core.sendreceive;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import ch.zhaw.mdp.fallstudie.jmail.core.account.Account;
import ch.zhaw.mdp.fallstudie.jmail.core.account.AccountUtil;
import ch.zhaw.mdp.fallstudie.jmail.core.messages.MailMessage;
import ch.zhaw.mdp.fallstudie.jmail.core.messages.MessagePersistenceUtil;
import ch.zhaw.mdp.fallstudie.jmail.core.messages.MessageType;
import ch.zhaw.mdp.fallstudie.jmail.ui.MessageViewer;
import ch.zhaw.mdp.fallstudie.jmail.ui.StatusBar;

public class ReceiverThread extends Thread {

	private final StatusBar statusBar;
	private final MessageViewer messageViewer;
	private final MailReceiver mailReceiver = new MailReceiver();

	public ReceiverThread(MessageViewer messageViewer, StatusBar statusBar) {
		this.messageViewer = messageViewer;
		this.statusBar = statusBar;
	}

	@Override
	public void run() {
		this.statusBar.setStatus("Receiving mails ...");
		final List<MailMessage> messages = new ArrayList<MailMessage>();
		List<Account> accounts = AccountUtil.loadAccounts();

		boolean successful = true;
		for (Account account : accounts) {
			successful = successful ? this.mailReceiver.receiveMails(account, messages) : successful;
		}

		if (successful) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					ReceiverThread.this.messageViewer.setMessages(MessageType.INBOX, messages);
					MessagePersistenceUtil.saveMessages(MessageType.INBOX, messages);
					ReceiverThread.this.messageViewer.refreshFilteredMessages();
					ReceiverThread.this.statusBar.setStatus("Ready");
				}
			});
		}
		else {
			Exception exception = this.mailReceiver.getLastException();
			exception.printStackTrace();
		}
	}

}
