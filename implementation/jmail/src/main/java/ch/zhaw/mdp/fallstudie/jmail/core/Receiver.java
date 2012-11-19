package ch.zhaw.mdp.fallstudie.jmail.core;


/**
 * Receiver model (email as recipient).
 */
public class Receiver {

	private final String mail;

	/**
	 * Constructor.
	 * 
	 * @param mail the mail address.
	 */
	public Receiver(String mail) {
		this.mail = mail;
	}

	public String getRecipient() {
		return mail;
	}

}
