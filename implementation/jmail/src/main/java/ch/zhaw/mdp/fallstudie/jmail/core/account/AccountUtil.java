package ch.zhaw.mdp.fallstudie.jmail.core.account;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to store and load accounts.
 * 
 */
public final class AccountUtil {

	private static final String FILE_NAME = "accounts.jmail";
	private static final File ACCOUNT_FILE = new File(System.getProperty("user.home") + "/" + AccountUtil.FILE_NAME);

	public static void saveAccounts(List<Account> accounts) {
		AccountUtil.checkFile();
		AccountUtil.internalSaveAccounts(accounts);
	}

	public static List<Account> loadAccounts() {
		if (!AccountUtil.checkFile()) {
			return new ArrayList<Account>(0);
		}
		return AccountUtil.loadAccounts(AccountUtil.ACCOUNT_FILE);
	}

	private static void internalSaveAccounts(List<Account> accounts) {
		// write serialized accounts to file
		try {
			OutputStream file = new FileOutputStream(AccountUtil.ACCOUNT_FILE);
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);
			try {
				output.writeObject(accounts);
			}
			finally {
				output.close();
			}
		}
		catch (IOException ex) {
			System.out.println("Error saving accounts");
			ex.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	private static List<Account> loadAccounts(File sourceFile) {
		// read serialized accounts from file
		List<Account> recoveredQuarks = new ArrayList<Account>();
		try {
			// use buffering
			InputStream file = new FileInputStream(sourceFile);
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream(buffer);
			try {
				// deserialize the List
				List<Account> temp = (List<Account>) input.readObject();
				recoveredQuarks = temp != null ? temp : recoveredQuarks;
			}
			finally {
				input.close();
			}
		}
		catch (ClassNotFoundException ex) {
			System.out.println("Error loading accounts");
			ex.printStackTrace();
		}
		catch (IOException ex) {
			System.out.println("Error loading accounts");
			ex.printStackTrace();
		}

		return recoveredQuarks;
	}

	private static boolean checkFile() {
		if (!AccountUtil.ACCOUNT_FILE.exists()) {
			File sourceFile;
			URL resource = AccountUtil.class.getClassLoader().getResource(AccountUtil.FILE_NAME);
			if (resource == null) {
				return false;
			}
			sourceFile = new File(resource.getPath());
			AccountUtil.internalSaveAccounts(AccountUtil.loadAccounts(sourceFile));
		}
		return true;
	}
}
