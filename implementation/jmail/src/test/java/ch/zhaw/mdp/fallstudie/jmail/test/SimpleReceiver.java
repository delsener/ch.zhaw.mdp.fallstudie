package ch.zhaw.mdp.fallstudie.jmail.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;

/**
 * A simple email receiver class.
 */
public class SimpleReceiver {
	/**
	 * Main method to receive messages from the mail server specified as command
	 * line arguments.
	 */
	public static void main(String args[]) {
		try {
			String popServer = "pop.gmx.net";
			String popUser = "mdp.jmail@gmx.ch";
			String popPassword = "MDP#Fallstudie";
			receive(popServer, popUser, popPassword);
		} catch (Exception ex) {
			System.out.println("Usage: java com.lotontech.mail.SimpleReceiver" + " popServer popUser popPassword");
		}
	}

	/**
	 * "receive" method to fetch messages and process them.
	 */
	public static void receive(String popServer, String popUser, String popPassword) {
		Store store = null;
		Folder folder = null;
		try {
			// -- Get hold of the default session --
			Properties props = System.getProperties();
			Session session = Session.getDefaultInstance(props, null);
			// -- Get hold of a POP3 message store, and connect to it --
			store = session.getStore("pop3");
			store.connect(popServer, popUser, popPassword);

			// -- Try to get hold of the default folder --
			folder = store.getDefaultFolder();
			if (folder == null)
				throw new Exception("No default folder");
			// -- ...and its INBOX --
			folder = folder.getFolder("INBOX");
			if (folder == null)
				throw new Exception("No POP3 INBOX");
			// -- Open the folder for read only --
			folder.open(Folder.READ_ONLY);
			// -- Get the message wrappers and process them --
			Message[] msgs = folder.getMessages();
			for (int msgNum = 0; msgNum < msgs.length; msgNum++) {
				printMessage(msgs[msgNum]);
				// msgs[msgNum].setFlag(Flags.Flag.RECENT, false); // Remove "unread"-flag, does not work client-side
			}
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// -- Close down nicely --
			try {
				if (folder != null)
					folder.close(false);
				if (store != null)
					store.close();
			} catch (Exception ex2) {
				ex2.printStackTrace();
			}
		}
	}

	/**
	 * "printMessage()" method to print a message.
	 */
	public static void printMessage(Message message) {
		try {
			// Get the header information
			String from = ((InternetAddress) message.getFrom()[0]).getPersonal();
			if (from == null)
				from = ((InternetAddress) message.getFrom()[0]).getAddress();
			System.out.println("FROM: " + from);
			String subject = message.getSubject();
			System.out.println("SUBJECT: " + subject);
			// -- Get the message part (i.e. the message itself) --
			Part messagePart = message;
			Object content = messagePart.getContent();
			// -- or its first body part if it is a multipart message --
			if (content instanceof Multipart) {
				messagePart = ((Multipart) content).getBodyPart(0);
				System.out.println("[ Multipart Message ]");
			}
			// -- Get the content type --
			String contentType = messagePart.getContentType();
			// -- If the content is plain text, we can print it --
			System.out.println("CONTENT:" + contentType);
			if (contentType.startsWith("text/plain") || contentType.startsWith("text/html")) {
				InputStream is = messagePart.getInputStream();
				BufferedReader reader = new BufferedReader(	new InputStreamReader(is));
				String thisLine = reader.readLine();
				while (thisLine != null) {
					System.out.println(thisLine);
					thisLine = reader.readLine();
				}
			}
			System.out.println("-----------------------------");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}