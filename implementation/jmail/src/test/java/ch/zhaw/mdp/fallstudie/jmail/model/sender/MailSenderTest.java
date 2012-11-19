package ch.zhaw.mdp.fallstudie.jmail.model.sender;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class MailSenderTest {

	private IMailSender sender;

	@Mock
	private IMailValidator mailValidatorMock;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		this.sender = new MailSender(this.sender, this.messageValidatorMock);
	}

	@Test
	public void testSendMailMessage() throws InterruptedException {
		MailMessage message = new MailMessage();
		Mockito.when(this.messageValidatorMock.validate(message)).thenReturn(true);

		this.sender.sendMessage(message);

		Thread.sleep(1000); // wait a second
		Assert.assertTrue(message.isSent());
	}

}
