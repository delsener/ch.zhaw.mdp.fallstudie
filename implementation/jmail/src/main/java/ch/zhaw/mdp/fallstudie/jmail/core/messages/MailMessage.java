package ch.zhaw.mdp.fallstudie.jmail.core.messages;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import ch.zhaw.mdp.fallstudie.jmail.core.Recipient;
import ch.zhaw.mdp.fallstudie.jmail.core.account.Account;
import ch.zhaw.mdp.fallstudie.jmail.core.validator.IMessageValidator;

/**
 * This class represents a mail message.
 */
public class MailMessage implements Serializable {

	private static final long serialVersionUID = -134067771668803519L;

	private final Account account;
	private final Recipient sender;
	private final List<Recipient> recipients;
	private final String subject;
	private final String content;

	private Date timeSent;
	private boolean transmitted;

	/**
	 * Constructor.
	 * 
	 * @param account
	 *            the account the message corresponds to.
	 * @param sender
	 *            the sender of the message.
	 * @param recipients
	 *            list of the recipients.
	 * @param subject
	 *            the subject.
	 * @param content
	 *            the content.
	 */
	public MailMessage(Account account, Recipient sender, List<Recipient> recipients, String subject, String content) {
		this.account = account;
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
	 * Getter for the account.
	 * 
	 * @return the account.
	 */
	public Account getAccount() {
		return this.account;
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

	/**
	 * Returns true if the message is valid. (Visitor pattern)
	 */
	public final boolean isValid(IMessageValidator messageValidator) {
		return messageValidator.validate(this);
	}
}
