package ch.zhaw.mdp.fallstudie.jmail.test;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * A simple email sender class.
 */
public class SimpleSender {
	/**
	 * Main method to send a message given on the command line.
	 */
	public static void main(String args[]) {
		try {
			String smtpServer = "mail.gmx.net";
			String smtpUser = "mdp.jmail@gmx.ch";
			String smtpPassword = "MDP#Fallstudie";

			String to = "mdp.jmail@gmx.ch";
			String from = "mdp.jmail@gmx.ch";
			String subject = "Test Mail - Date: " + new Date().toString();
			String body = "Hi. I'm a test mail generated on: "
					+ new Date().toString();

			send(smtpServer, smtpUser, smtpPassword, to, from, subject, body);
		} catch (Exception ex) {
			System.out.println("Usage: java com.lotontech.mail.SimpleSender smtpServer toAddress fromAddress subjectText bodyText");
		}
		System.exit(0);
	}

	/**
	 * "send" method to send the message.
	 */
	public static void send(String smtpServer, final String smtpUser, final String smtpPassword, String to, String from, String subject,
			String body) {
		try {
			Properties props = System.getProperties();
			// -- Attaching to default Session, or we could start a new one --

			props.put("mail.smtp.host", smtpServer);
			props.put("mail.smtp.auth", "true");  
			
			Authenticator authenticator = new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(smtpUser, smtpPassword);
				}
			};
			Session session = Session.getInstance(props, authenticator);

			// -- Create a new message --
			Message msg = new MimeMessage(session);

			// -- Set the FROM and TO fields --
			msg.setFrom(new InternetAddress(from));
			msg.setRecipients(Message.RecipientType.TO,	InternetAddress.parse(to, false));

			// -- We could include CC recipients too --
			// if (cc != null)
			// msg.setRecipients(Message.RecipientType.CC
			// ,InternetAddress.parse(cc, false));

			// -- Set the subject and body text --
			msg.setSubject(subject);
			msg.setText(body);

			// -- Set some other header information --
			msg.setHeader("X-Mailer", "LOTONtechEmail");
			msg.setSentDate(new Date());

			// -- Send the message --
			Transport.send(msg);
			System.out.println("Message sent OK.");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}