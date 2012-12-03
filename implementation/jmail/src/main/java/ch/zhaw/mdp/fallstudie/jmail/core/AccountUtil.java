package ch.zhaw.mdp.fallstudie.jmail.core;

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
import java.util.List;

/**
 * Utility class to store and load accounts.
 * 
 */
public final class AccountUtil {

	private static final String FILE_NAME = "accounts.jmail";
	private static final File ACCOUNT_FILE = new File(
			System.getProperty("user.home") + "/" + FILE_NAME);

	public static void saveAccounts(List<Account> accounts) {
		checkFile();
		internalSaveAccounts(accounts);
	}

	public static List<Account> loadAccounts() {
		checkFile();
		return loadAccounts(ACCOUNT_FILE);
	}

	private static void internalSaveAccounts(List<Account> accounts) {
		// write serialized accounts to file
		try {
			OutputStream file = new FileOutputStream(ACCOUNT_FILE);
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);
			try {
				output.writeObject(accounts);
			} finally {
				output.close();
			}
		} catch (IOException ex) {
			System.out.println("Error saving accounts");
			ex.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	private static List<Account> loadAccounts(File sourceFile) {
		// read serialized accounts from file
		List<Account> recoveredQuarks = null;
		try {
			// use buffering
			InputStream file = new FileInputStream(sourceFile);
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream(buffer);
			try {
				// deserialize the List
				recoveredQuarks = (List<Account>) input.readObject();
			} finally {
				input.close();
			}
		} catch (ClassNotFoundException ex) {
			System.out.println("Error loading accounts");
			ex.printStackTrace();
		} catch (IOException ex) {
			System.out.println("Error loading accounts");
			ex.printStackTrace();
		}

		return recoveredQuarks;
	}

	private static void checkFile() {
		if (!ACCOUNT_FILE.exists()) {
			File sourceFile;
			sourceFile = new File(AccountUtil.class.getClassLoader()
					.getResource(FILE_NAME).getPath());
			internalSaveAccounts(loadAccounts(sourceFile));
		}
	}
}
