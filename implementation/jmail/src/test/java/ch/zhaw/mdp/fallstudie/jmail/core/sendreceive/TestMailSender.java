package ch.zhaw.mdp.fallstudie.jmail.core.sendreceive;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch.zhaw.mdp.fallstudie.jmail.core.MailServer;
import ch.zhaw.mdp.fallstudie.jmail.core.Recipient;
import ch.zhaw.mdp.fallstudie.jmail.core.account.Account;
import ch.zhaw.mdp.fallstudie.jmail.core.messages.MailMessage;

public class TestMailSender {

	public static void main(String[] args) {
		MailServer mailServer = new MailServer(25);
		mailServer.setHost("mail.gmx.net");
		mailServer.setUsername("mdp.jmail@gmx.ch");
		mailServer.setPassword("MDP#Fallstudie");

		Account account = new Account();
		account.setOutServer(mailServer);
		
		Recipient receiver = new Recipient("mdp.jmail@gmx.ch");
		List<Recipient> receivers = new ArrayList<Recipient>();
		receivers.add(new Recipient("mdp.jmail@gmx.ch"));
		String subject = "Test Mail - Date: " + new Date().toString();
		String content = "Hi. I'm a test mail generated on: " + new Date().toString();

		MailMessage message = new MailMessage(account, receiver, receivers, subject, content);

		IMailSender sender = new MailSender();
		if (!sender.sendMail(mailServer, message)) {
			Exception exception = sender.getLastException();
			if (exception != null) {
				exception.printStackTrace();
			}
		}
	}
}
