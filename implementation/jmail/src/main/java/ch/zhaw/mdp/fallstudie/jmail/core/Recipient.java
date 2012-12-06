package ch.zhaw.mdp.fallstudie.jmail.core;

import java.io.Serializable;

/**
 * Recipient model
 */
public class Recipient implements Serializable {

	private static final long serialVersionUID = -5269224144443392438L;
	
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
