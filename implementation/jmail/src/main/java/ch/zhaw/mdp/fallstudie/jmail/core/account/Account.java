package ch.zhaw.mdp.fallstudie.jmail.core.account;

import java.io.Serializable;

import ch.zhaw.mdp.fallstudie.jmail.core.MailServer;

/**
 * Represents an email account.
 */
public class Account implements Serializable {

	private static final long serialVersionUID = -1025225780265942218L;

	private String accountName;
	private String emailName;
	private String address;
	private MailServer outServer;
	private MailServer inServer;
	private boolean useSSLAuth = false;

	/**
	 * Default Constructor.
	 */
	public Account() {
		this.outServer = new MailServer(587);
		this.inServer = new MailServer(110);
	}

	/**
	 * Get the Name of the account.
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * Get the E-Mail Adress of the account.
	 */
	public String getEmailName() {
		return emailName;
	}

	public String getAddress() {
		return address;
	}

	public MailServer getOutServer() {
		return outServer;
	}

	public MailServer getInServer() {
		return inServer;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public void setEmailName(String emailName) {
		this.emailName = emailName;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setOutServer(MailServer outServer) {
		this.outServer = outServer;
	}

	public void setInServer(MailServer inServer) {
		this.inServer = inServer;
	}

	public boolean isUseSSLAuth() {
		return useSSLAuth;
	}

	public void setUseSSLAuth(boolean useSSLAuth) {
		this.useSSLAuth = useSSLAuth;
	}

	@Override
	public String toString() {
		return accountName;
	}
}
