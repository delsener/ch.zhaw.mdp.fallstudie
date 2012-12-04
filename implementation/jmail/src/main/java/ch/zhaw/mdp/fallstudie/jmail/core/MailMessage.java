package ch.zhaw.mdp.fallstudie.jmail.core;

import java.util.Date;
import java.util.List;

/**
 * This class represents a mail message.
 */
public class MailMessage {

	private final Recipient sender;
	private final List<Recipient> recipients;
	private final String subject;
	private final String content;

	private Date timeSent;
	private boolean transmitted;

	/**
	 * Constructor.
	 * 
	 * @param sender
	 *            the sender of the message.
	 * @param recipients
	 *            list of the recipients.
	 * @param subject
	 *            the subject.
	 * @param content
	 *            the content.
	 */
	public MailMessage(Recipient sender, List<Recipient> recipients, String subject, String content) {
		this.sender = sender;
		this.recipients = recipients;
		this.subject = subject;
		this.content = content;

		this.timeSent = null;
		this.transmitted = false;
	}

	/**
	 * Returns the sender.
	 * 
	 * @return the sender.
	 */
	public Recipient getSender() {
		return this.sender;
	}

	/**
	 * Returns the recipients.
	 * 
	 * @return the recipients.
	 */
	public List<? extends Recipient> getReceivers() {
		return this.recipients;
	}

	/**
	 * Returns the subject.
	 * 
	 * @return the subject.
	 */
	public String getSubject() {
		return this.subject;
	}

	/**
	 * Returns the content.
	 * 
	 * @return the content.
	 */
	public String getContent() {
		return this.content;
	}

	/**
	 * Returns the time sent.
	 * 
	 * @return the time sent.
	 */
	public Date getTimeSent() {
		return this.timeSent;
	}

	/**
	 * Set the time sent.
	 * 
	 * @param timeSent
	 */
	public void setTimeSent(Date timeSent) {
		this.timeSent = timeSent;
	}

	/**
	 * Returns the transmission flag.
	 * 
	 * @return the transmission flag.
	 */
	public boolean isTransmitted() {
		return this.transmitted;
	}

	/**
	 * Set the transmission flag.
	 * 
	 * @param transmission
	 *            .
	 */
	public void setTransmitted(boolean transmitted) {
		this.transmitted = transmitted;
	}

	/**
	 * Returns the string representation of the recipients.
	 * 
	 * @return the string representation of the recipients.
	 */
	public String getRecipientsDisplayString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (Recipient recipient : this.recipients) {
			stringBuilder.append(recipient.getAddress()).append(";");
		}
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		return stringBuilder.toString();
	}
}