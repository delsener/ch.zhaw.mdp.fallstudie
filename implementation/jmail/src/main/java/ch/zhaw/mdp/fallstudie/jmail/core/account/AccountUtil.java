package ch.zhaw.mdp.fallstudie.jmail.core.account;

import java.io.File;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ch.zhaw.mdp.fallstudie.jmail.core.utils.PersistenceUtil;

/**
 * Utility class to store and load accounts.
 * 
 */
public final class AccountUtil {

	private static final String FILE_NAME = "accounts.jmail";
	private static final File ACCOUNT_FILE = new File(System.getProperty("user.home") + "/" + AccountUtil.FILE_NAME);

	public static void saveAccounts(List<Account> accounts) {
		PersistenceUtil.saveObject(AccountUtil.ACCOUNT_FILE, (Serializable) accounts);
	}

	@SuppressWarnings("unchecked")
	public static List<Account> loadAccounts() {
		if (!AccountUtil.checkFile()) {
			return new ArrayList<Account>(0);
		}
		return (List<Account>) PersistenceUtil.loadObject(AccountUtil.ACCOUNT_FILE);
	}

	private static boolean checkFile() {
		if (!AccountUtil.ACCOUNT_FILE.exists()) {
			File sourceFile;
			URL resource = AccountUtil.class.getClassLoader().getResource(AccountUtil.FILE_NAME);
			if (resource == null) {
				return false;
			}
			sourceFile = new File(resource.getPath());

			// FIXME: This does not work !!!
			PersistenceUtil.saveObject(AccountUtil.ACCOUNT_FILE, PersistenceUtil.loadObject(sourceFile));
		}
		return true;
	}
}
