package ch.zhaw.mdp.fallstudie.jmail.core.sendreceive;

import ch.zhaw.mdp.fallstudie.jmail.core.account.Account;
import ch.zhaw.mdp.fallstudie.jmail.core.messages.MailMessage;

public interface IMailSender {

	/**
	 * @param account
	 * @param message
	 * 
	 * @return <code>true</code> on success otherwise it returns
	 *         <code>false</code>
	 */
	public boolean sendMail(Account account, MailMessage message);

	/**
	 * @return Returns the exception that occurred during the
	 *         {@link IMailSender#sendMail} method. Returns null if there was no
	 *         exception
	 */
	public Exception getLastException();

}
