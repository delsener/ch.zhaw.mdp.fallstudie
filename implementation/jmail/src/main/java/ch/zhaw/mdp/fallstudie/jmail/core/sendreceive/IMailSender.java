package ch.zhaw.mdp.fallstudie.jmail.core.sendreceive;

import ch.zhaw.mdp.fallstudie.jmail.core.MailServer;
import ch.zhaw.mdp.fallstudie.jmail.core.MailMessage;

public interface IMailSender {

	/**
	 * @param mailServer
	 * @param message
	 * 
	 * @return <code>true</code> on success otherwise it returns
	 *         <code>false</code>
	 */
	public boolean sendMail(MailServer mailServer, MailMessage message);

	/**
	 * @return Returns the exception that occurred during the
	 *         {@link IMailSender#sendMail} method. Returns null if there was no
	 *         exception
	 */
	public Exception getLastException();

}
