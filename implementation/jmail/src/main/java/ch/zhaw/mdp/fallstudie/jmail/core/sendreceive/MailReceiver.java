package ch.zhaw.mdp.fallstudie.jmail.core.sendreceive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;

import ch.zhaw.mdp.fallstudie.jmail.core.MailServer;
import ch.zhaw.mdp.fallstudie.jmail.core.Recipient;
import ch.zhaw.mdp.fallstudie.jmail.core.account.Account;
import ch.zhaw.mdp.fallstudie.jmail.core.messages.MailMessage;

public class MailReceiver implements IMailReceiver {

	private Exception lastException;

	public MailReceiver() {
		this.lastException = null;
	}

	@Override
	public boolean receiveMails(Account account, List<MailMessage> mailMessages) {
		Store store = null;
		Folder folder = null;

		MailServer mailServer = account.getInServer();

		try {
			this.lastException = null;

			Properties props = System.getProperties();
			Session session = Session.getInstance(props, mailServer.getAuthenticator());

			store = session.getStore("pop3");
			store.connect(mailServer.getHost(), mailServer.getUsername(), mailServer.getPassword());

			folder = store.getDefaultFolder();
			if (folder == null) {
				throw new Exception("No default folder");
			}

			folder = folder.getFolder("INBOX");
			if (folder == null) {
				throw new Exception("No POP3 INBOX");
			}

			folder.open(Folder.READ_ONLY);

			Message[] messages = folder.getMessages();
			for (Message message : messages) {
				MailMessage mailMessage = this.parseMailMessage(account, message);
				mailMessages.add(mailMessage);
			}
		}
		catch (Exception exception) {
			this.lastException = exception;
			return false;
		}

		return true;
	}

	@Override
	public Exception getLastException() {
		return this.lastException;
	}

	private MailMessage parseMailMessage(Account account, Message message) throws MessagingException, IOException {
		String messageFrom = ((InternetAddress) message.getFrom()[0]).getAddress();
		String messageSubject = message.getSubject();

		Address[] adresses = message.getAllRecipients();
		String[] messageRecipients = new String[message.getAllRecipients().length];
		for (int i = 0; i < adresses.length; i++) {
			messageRecipients[i] = ((InternetAddress) adresses[i]).getAddress();
		}

		Part messagePart = message;
		Object content = messagePart.getContent();
		if (content instanceof Multipart) {
			messagePart = ((Multipart) content).getBodyPart(0);
		}

		String contentType = messagePart.getContentType();
		StringBuilder messageContent = new StringBuilder();

		if (contentType.startsWith("text/plain") || contentType.startsWith("text/html")) {
			InputStream inputStream = messagePart.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

			String tempLine = reader.readLine();
			while (tempLine != null) {
				messageContent.append(tempLine);
				tempLine = reader.readLine();
			}
		}

		Recipient sender = new Recipient(messageFrom);
		List<Recipient> receivers = new ArrayList<Recipient>();
		for (String messageRecipient : messageRecipients) {
			receivers.add(new Recipient(messageRecipient));
		}

		MailMessage mailMessage = new MailMessage(account, sender, receivers, messageSubject.toString(), messageContent.toString());
		mailMessage.setTimeSent(message.getSentDate());
		mailMessage.setTransmitted(true);

		return mailMessage;
	}
}
