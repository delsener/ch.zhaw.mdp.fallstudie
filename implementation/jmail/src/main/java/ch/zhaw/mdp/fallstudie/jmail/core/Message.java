package ch.zhaw.mdp.fallstudie.jmail.core;

import java.util.Date;
import java.util.List;

/**
 * This class represents a mail message. 
 */
public class Message {

	private final Receiver sender;
	private final List<Receiver> receivers;
	private final String subject;
	private final String content;
	private final Date creationTime;
	private final Date sendTime;
	private boolean transmitted = false;

	/**
	 * Constructor.
	 * 
	 * @param sender the sender of the message.
	 * @param receivers list of the receivers.
	 * @param subject the subject.
	 * @param content the content.
	 * @param sendTime the send time.
	 */
	public Message(Receiver sender, List<Receiver> receivers,
			String subject, String content, Date sendTime) {
		this.sender = sender;
		this.receivers = receivers;
		this.subject = subject;
		this.content = content;
		this.sendTime = sendTime;
		this.creationTime = new Date();
	}

	/**
	 * Returns the sender.
	 * 
	 * @return the sender.
	 */
	public Receiver getSender() {
		return sender;
	}

	/**
	 * Returns the receivers.
	 * 
	 * @return the receivers.
	 */
	public List<? extends Receiver> getReceivers() {
		return receivers;
	}

	/**
	 * Returns the subject.
	 * 
	 * @return the subject.
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Returns the content.
	 * 
	 * @return the content.
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Returns the creation time.
	 * 
	 * @return the creation time.
	 */
	public Date getCreationTime() {
		return creationTime;
	}

	/**
	 * Returns the transmission flag.
	 * 
	 * @return the transmission flag.
	 */
	public boolean isTransmitted() {
		return transmitted;
	}

	/**
	 * Set the transmission flag.
	 * 
	 * @param transmission.
	 */
	public void setTransmitted(boolean transmitted) {
		this.transmitted = transmitted;
	}

	/**
	 * Returns the send time.
	 * 
	 * @return the send time.
	 */
	public Date getSendTime() {
		return sendTime;
	}

	/**
	 * Returns the string representation of the receivers.
	 * 
	 * @return the string representation of the receivers.
	 */
	public String getReceiversDisplayString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (Receiver receiver : receivers) {
			stringBuilder.append(receiver.getRecipient()).append(";");
		}
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		return stringBuilder.toString();
	}
}
