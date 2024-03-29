package ch.zhaw.mdp.fallstudie.jmail.core.account;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
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
			InputStream resource = AccountUtil.class.getClassLoader().getResourceAsStream(AccountUtil.FILE_NAME);
			if (resource == null) {
				return false;
			}
			PersistenceUtil.saveObject(AccountUtil.ACCOUNT_FILE, PersistenceUtil.loadObject(resource));
		}
		return true;
	}
}
