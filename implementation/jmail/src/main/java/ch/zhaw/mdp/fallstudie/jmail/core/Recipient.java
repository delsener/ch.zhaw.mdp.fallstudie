package ch.zhaw.mdp.fallstudie.jmail.core;

/**
 * Recipient model
 */
public class Recipient {

	private final String address;

	/**
	 * Constructor.
	 * 
	 * @param address
	 *            the mail address.
	 */
	public Recipient(String address) {
		this.address = address;
	}

	public String getAddress() {
		return this.address;
	}

}
