package ch.zhaw.mdp.fallstudie.jmail.validator;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;

public class MailValidatorTest {

//	@Test
//	public void testValidMessage() {
//		MailMessage message = new MailMessage();
//
//		message.setSender(new Recipient("test@test.com"));
//		message.addRecipient(new Recipient("mail@mail.com"));
//		message.setText("Hello. How are you?");
//		message.setSubject("E-Mail");
//
//		Assert.assertTrue(message.isValid(new MailValidator()));
//	}
//
//	@Test
//	public void testMessageWithoutSubject() {
//		MailMessage message = new MailMessage();
//
//		message.setSender(new Recipient("test@test.com"));
//		message.addRecipient(new Recipient("mail@mail.com"));
//		message.setText("Hello. How are you?");
//
//		Assert.assertFalse(message.isValid(new MailValidator()));
//	}
//
//	@Test
//	public void testMessageWithInvalidSender() {
//		MailMessage message = new MailMessage();
//
//		message.setSender(new Recipient("123"));
//		message.addRecipient(new Recipient("mail@mail.com"));
//		message.setText("Hello. How are you?");
//		message.setSubject("E-Mail");
//
//		Assert.assertFalse(message.isValid(new MailValidator()));
//	}
//
//	@Test
//	public void testMessageWithInvalidRecipient() {
//		MailMessage message = new MailMessage();
//
//		message.setSender(new Recipient("test@test.com"));
//		message.addRecipient(new Recipient("123"));
//		message.setText("Hello. How are you?");
//		message.setSubject("E-Mail");
//
//		Assert.assertFalse(message.isValid(new MailValidator()));
//	}
//
//	@Test
//	public void testMessageWithValidAttachment() {
//		MailMessage message = new MailMessage();
//
//		message.setSender(new Recipient("test@test.com"));
//		message.addRecipient(new Recipient("mail@mail.com"));
//		message.setText("Hello. How are you?");
//		message.setSubject("E-Mail");
//		message.addAttachment(new File(".project"));
//
//		Assert.assertTrue(message.isValid(new MailValidator()));
//	}
//
//	@Test
//	public void testMessageWithInvalidAttachment() {
//		MailMessage message = new MailMessage();
//
//		message.setSender(new Recipient("test@test.com"));
//		message.addRecipient(new Recipient("mail@mail.com"));
//		message.setText("Hello. How are you?");
//		message.setSubject("E-Mail");
//		message.addAttachment(new File("this-file-does-not-exist"));
//
//		Assert.assertFalse(message.isValid(new MailValidator()));
//	}

}
