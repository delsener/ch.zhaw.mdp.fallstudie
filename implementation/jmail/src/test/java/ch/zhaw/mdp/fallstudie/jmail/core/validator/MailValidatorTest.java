package ch.zhaw.mdp.fallstudie.jmail.core.validator;

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

public class MailValidatorTest {

	private Account account;

	@Before
	public void setUp() {
		MailServer mailServer = new MailServer(25);
		mailServer.setHost("mail.gmx.net");
		mailServer.setUsername("mdp.jmail@gmx.ch");
		mailServer.setPassword("MDP#Fallstudie");

		this.account = new Account();
		this.account.setOutServer(mailServer);
		this.account.setAddress("mdp.jmail@gmx.ch");
	}

	@Test
	public void testValidMessage() {
		Recipient sender = new Recipient("mdp.jmail@gmx.ch");
		List<Recipient> receivers = new ArrayList<Recipient>();
		receivers.add(new Recipient("mdp.jmail@gmx.ch"));
		String subject = "Test Mail - Date: " + new Date().toString();
		String content = "Hi. I'm a test mail generated on: " + new Date().toString();

		MailMessage message = new MailMessage(this.account, sender, receivers, subject, content);
		Assert.assertTrue(message.isValid(new MessageValidator()));
	}

	@Test
	public void testMessageWithoutSubject() {
		Recipient sender = new Recipient("mdp.jmail@gmx.ch");
		List<Recipient> receivers = new ArrayList<Recipient>();
		receivers.add(new Recipient("mdp.jmail@gmx.ch"));
		String content = "Hi. I'm a test mail generated on: " + new Date().toString();

		MailMessage message = new MailMessage(this.account, sender, receivers, null, content);
		Assert.assertFalse(message.isValid(new MessageValidator()));
	}

	@Test
	public void testMessageWithInvalidSender() {
		Recipient sender = new Recipient("123");
		List<Recipient> receivers = new ArrayList<Recipient>();
		receivers.add(new Recipient("mdp.jmail@gmx.ch"));
		String subject = "Test Mail - Date: " + new Date().toString();
		String content = "Hi. I'm a test mail generated on: " + new Date().toString();

		MailMessage message = new MailMessage(this.account, sender, receivers, subject, content);
		Assert.assertFalse(message.isValid(new MessageValidator()));
	}

	@Test
	public void testMessageWithInvalidReceiver() {
		Recipient sender = new Recipient("mdp.jmail@gmx.ch");
		List<Recipient> receivers = new ArrayList<Recipient>();
		receivers.add(new Recipient("123"));
		String subject = "Test Mail - Date: " + new Date().toString();
		String content = "Hi. I'm a test mail generated on: " + new Date().toString();

		MailMessage message = new MailMessage(this.account, sender, receivers, subject, content);
		Assert.assertFalse(message.isValid(new MessageValidator()));
	}

}
