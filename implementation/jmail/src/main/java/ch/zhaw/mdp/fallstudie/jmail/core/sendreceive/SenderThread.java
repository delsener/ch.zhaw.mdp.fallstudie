package ch.zhaw.mdp.fallstudie.jmail.core.sendreceive;

import java.util.List;

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
		statusBar.setStatus("Sending mail ...");
		List<MailMessage> messages = MessagePersistenceUtil.loadMessages(MessageType.SENT);
		messages.add(mailMessage);
		mailSender.sendMail(mailMessage.getAccount(), mailMessage);
		messageViewer.setMessages(MessageType.SENT, messages);
		MessagePersistenceUtil.saveMessages(MessageType.SENT, messages);
		messageViewer.refreshFilteredMessages();
		statusBar.setStatus("Ready");
	}
	
}
