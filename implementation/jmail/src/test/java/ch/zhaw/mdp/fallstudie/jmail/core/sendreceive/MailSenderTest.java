package ch.zhaw.mdp.fallstudie.jmail.core.sendreceive;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ch.zhaw.mdp.fallstudie.jmail.core.MailServer;
import ch.zhaw.mdp.fallstudie.jmail.core.Recipient;
import ch.zhaw.mdp.fallstudie.jmail.core.account.Account;
import ch.zhaw.mdp.fallstudie.jmail.core.messages.MailMessage;

public class MailSenderTest {

	private IMailSender sender;

	private MailServer mailServer;
	private Account account;
	private MailMessage message;

	@Before
	public void setUp() {
		this.mailServer = new MailServer(25);
		this.mailServer.setHost("mail.gmx.net");
		this.mailServer.setUsername("mdp.jmail@gmx.ch");
		this.mailServer.setPassword("MDP#Fallstudie");

		this.account = new Account();
		this.account.setOutServer(this.mailServer);
		this.account.setAddress("mdp.jmail@gmx.ch");

		Recipient receiver = new Recipient("mdp.jmail@gmx.ch");
		List<Recipient> receivers = new ArrayList<Recipient>();
		receivers.add(new Recipient("mdp.jmail@gmx.ch"));
		String subject = "Test Mail - Date: " + new Date().toString();
		String content = "Hi. I'm a test mail generated on: " + new Date().toString();

		this.message = new MailMessage(this.account, receiver, receivers, subject, content);

		this.sender = new MailSender();
	}

	@Test
	public void testSendMailMessage() throws InterruptedException {
		this.sender.sendMail(this.account, this.message);

		Thread.sleep(1000); // wait a second
		Assert.assertTrue(this.sender.getLastException() == null);
		Assert.assertFalse(this.message.getTimeSent() == null);
	}
}
