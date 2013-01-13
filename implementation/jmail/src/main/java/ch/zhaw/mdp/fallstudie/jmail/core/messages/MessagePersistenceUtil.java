package ch.zhaw.mdp.fallstudie.jmail.core.messages;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.zhaw.mdp.fallstudie.jmail.core.utils.PersistenceUtil;

/**
 * Utility class to store and load accounts.
 * 
 */
public final class MessagePersistenceUtil {

	private static final Map<MessageType, File> FILES = new HashMap<MessageType, File>();
	
	static {
		MessageType[] messageTypes = MessageType.values();
		for (MessageType messageType : messageTypes) {
			FILES.put(messageType, new File(System.getProperty("user.home")
			+ "/" + messageType.name().toLowerCase() + ".jmail"));
		}
	}
	
	public static void saveMessages(MessageType messageType, List<MailMessage> mailMessages) {
		checkFile(messageType);
		PersistenceUtil.saveObject(FILES.get(messageType), (Serializable) mailMessages);
	}

	@SuppressWarnings("unchecked")
	public static List<MailMessage> loadMessages(MessageType messageType) {
		if (!FILES.get(messageType).exists()) {
			return new ArrayList<MailMessage>(0);
		}
		return (List<MailMessage>) PersistenceUtil.loadObject(FILES.get(messageType));
	}

	private static void checkFile(MessageType messageType) {
		try {
			if (!FILES.get(messageType).exists()) {
				FILES.get(messageType).createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
