package ch.zhaw.mdp.fallstudie.jmail.core.sendreceive;

import java.util.List;

import ch.zhaw.mdp.fallstudie.jmail.core.account.Account;
import ch.zhaw.mdp.fallstudie.jmail.core.messages.MailMessage;

public interface IMailReceiver {

	/**
	 * @param account
	 * @param message
	 * 
	 * @return <code>true</code> on success otherwise it returns
	 *         <code>false</code>
	 */
	public boolean receiveMails(Account account, List<MailMessage> messages);

	/**
	 * @return Returns the exception that occurred during the
	 *         {@link IMailReceiver#receiveMail} method. Returns null if there
	 *         was no exception
	 */
	public Exception getLastException();

}
