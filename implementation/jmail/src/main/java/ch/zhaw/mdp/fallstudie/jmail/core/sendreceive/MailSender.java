package ch.zhaw.mdp.fallstudie.jmail.core.sendreceive;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import ch.zhaw.mdp.fallstudie.jmail.core.MailServer;
import ch.zhaw.mdp.fallstudie.jmail.core.Recipient;
import ch.zhaw.mdp.fallstudie.jmail.core.messages.MailMessage;

public class MailSender implements IMailSender {

	private Exception lastException;

	public MailSender() {
		this.lastException = null;
	}

	@Override
	public boolean sendMail(final MailServer mailServer, final MailMessage mailMessage) {
		try {
			this.lastException = null;

			Properties props = System.getProperties();
			props.put("mail.smtp.host", mailServer.getHost());
			props.put("mail.smtp.port", mailServer.getPort());
			props.put("mail.smtp.auth", "true");

			Session session = Session.getInstance(props, mailServer.getAuthenticator());
			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(mailMessage.getSender().getAddress()));
			for (Recipient receiver : mailMessage.getReceivers()) {
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver.getAddress(), false));
			}

			message.setSubject(mailMessage.getSubject());
			message.setText(mailMessage.getContent());

			// TODO: Add attachments

			message.setHeader("X-Mailer", "LOTONtechEmail");
			message.setSentDate(new Date());

			Transport.send(message);

			mailMessage.setTimeSent(message.getSentDate());
			mailMessage.setTransmitted(true);
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

}
