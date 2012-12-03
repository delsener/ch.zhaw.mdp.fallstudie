package ch.zhaw.mdp.fallstudie.jmail.controller;

import java.util.Observer;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class MessageSenderTest {

//	@Mock
//	private Observer messageSenderObserver;
//	@Mock
//	private IMailSender senderMock;
//	@Mock
//	private IMailValidator validatorMock;
//
//	@Before
//	public void setUp() {
//		MockitoAnnotations.initMocks(this);
//		this.messageSender = new MailSender(this.senderMock, this.validatorMock);
//		this.messageSender.addObserver(this.messageSenderObserver);
//	}
//
//	@Test
//	public void testSendMail() {
//		MailMessage message = new MailMessage();
//		Mockito.when(this.validatorMock.validate(message)).thenReturn(true);
//
//		this.messageSender.sendMail(message);
//		Mockito.verify(this.senderMock).send(message);
//		Mockito.verify(this.messageSenderObserver).update(this.messageSender, message);
//
//		Assert.assertTrue(this.messageSender.getOutbox().size() == 1);
//		Assert.assertEquals(message, this.messageSender.getOutbox().get(0));
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void testSendInvalidMail() {
//		PrintMail message = new PrintMail();
//		Mockito.when(this.validatorMock.validate(message)).thenReturn(false);
//		this.messageSender.sendMail(message);
//	}

}
