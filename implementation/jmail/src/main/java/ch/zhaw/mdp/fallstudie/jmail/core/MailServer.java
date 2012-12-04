package ch.zhaw.mdp.fallstudie.jmail.core;

import java.io.Serializable;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * Contains information about a POP3 or SMTP server.
 */
public class MailServer implements Serializable {

	private static final long serialVersionUID = 1324673418078116783L;

	private String host;
	private int port;
	private String username;
	private String password;

	/**
	 * Constructor.
	 * 
	 * @param port
	 *            the port.
	 */
	public MailServer(int port) {
		this.port = port;
	}

	public String getHost() {
		return this.host;
	}

	public int getPort() {
		return this.port;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Authenticator getAuthenticator() {
		return new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(MailServer.this.getUsername(), MailServer.this.getPassword());
			}
		};
	}

}
