package ch.zhaw.mdp.fallstudie.jmail.core.sendreceive;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.mdp.fallstudie.jmail.core.MailServer;
import ch.zhaw.mdp.fallstudie.jmail.core.account.Account;
import ch.zhaw.mdp.fallstudie.jmail.core.messages.MailMessage;

public class TestMailReceiver {

	public static void main(String[] args) {
		MailServer mailServer = new MailServer(995);
		mailServer.setHost("pop.gmx.com");
		mailServer.setUsername("mdp.jmail@gmx.ch");
		mailServer.setPassword("MDP#Fallstudie");

		Account account = new Account();
		account.setInServer(mailServer);
		
		List<MailMessage> messages = new ArrayList<MailMessage>();

		IMailReceiver receiver = new MailReceiver();
		if (!receiver.receiveMails(account, messages)) {
			Exception exception = receiver.getLastException();
			if (exception != null) {
				exception.printStackTrace();
			}
		}
		else {
			for (MailMessage message : messages) {
				TestMailReceiver.printMessage(message);
			}
		}
	}

	private static void printMessage(MailMessage message) {
		System.out.println("--------------------------------------------------------------------------------");
		System.out.println("Sender: " + message.getSender().getAddress());
		System.out.println("Receiver: " + message.getRecipientsDisplayString());
		System.out.println("Subject: " + message.getSubject());
		System.out.println("Time sent: " + message.getTimeSent());
		System.out.println("--------------------------------------------------------------------------------");
		System.out.println(message.getContent());
		System.out.println("--------------------------------------------------------------------------------");
		System.out.println();
	}
}
