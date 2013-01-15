package ch.zhaw.mdp.fallstudie.jmail.core.sendreceive;

import java.util.List;

import javax.swing.SwingUtilities;

import ch.zhaw.mdp.fallstudie.jmail.core.messages.MailMessage;
import ch.zhaw.mdp.fallstudie.jmail.core.messages.MessagePersistenceUtil;
import ch.zhaw.mdp.fallstudie.jmail.core.messages.MessageType;
import ch.zhaw.mdp.fallstudie.jmail.ui.MessageViewer;
import ch.zhaw.mdp.fallstudie.jmail.ui.StatusBar;

public class SenderThread extends Thread {

	private final StatusBar statusBar;
	private final MessageViewer messageViewer;
	private final MailSender mailSender = new MailSender();
	private final MailMessage mailMessage;

	public SenderThread(MessageViewer messageViewer, StatusBar statusBar, MailMessage mailMessage) {
		this.messageViewer = messageViewer;
		this.statusBar = statusBar;
		this.mailMessage = mailMessage;
	}

	@Override
	public void run() {
		this.statusBar.setStatus("Sending mail ...");
		final List<MailMessage> messages = MessagePersistenceUtil.loadMessages(MessageType.SENT);
		messages.add(this.mailMessage);
		boolean successful = this.mailSender.sendMail(this.mailMessage.getAccount(), this.mailMessage);

		if (successful) {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					SenderThread.this.messageViewer.setMessages(MessageType.SENT, messages);
					MessagePersistenceUtil.saveMessages(MessageType.SENT, messages);
					SenderThread.this.messageViewer.refreshFilteredMessages();
					SenderThread.this.statusBar.setStatus("Ready");
				}
			});
		}
		else {
			Exception exception = this.mailSender.getLastException();
			exception.printStackTrace();
		}
	}

}
