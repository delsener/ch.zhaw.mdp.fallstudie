package ch.zhaw.mdp.fallstudie.jmail.core.validator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ch.zhaw.mdp.fallstudie.jmail.core.Recipient;
import ch.zhaw.mdp.fallstudie.jmail.core.messages.MailMessage;

public class MessageValidator implements IMessageValidator {

	public static final String REGEX_VALID_EMAIL_ADDRESS = "^[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$";

	@Override
	public boolean validate(MailMessage message) {
		if (!MessageValidator.validateAbstractMessage(message)) {
			return false;
		}
		if (!MessageValidator.messageHasSubject(message)) {
			return false;
		}
		if (!MessageValidator.validateMailRecipient(message.getSender())) {
			return false;
		}
		if (!MessageValidator.validateMailReceivers(message.getReceivers())) {
			return false;
		}
		return true;
	}

	private static boolean validateAbstractMessage(MailMessage message) {
		if (!MessageValidator.messageHasSender(message)) {
			return false;
		}
		if (!MessageValidator.messageHasRecipient(message)) {
			return false;
		}
		if (!MessageValidator.messageHasContent(message)) {
			return false;
		}

		return true;
	}

	private static boolean messageHasSender(MailMessage message) {
		return message.getSender() != null ? true : false;
	}

	private static boolean messageHasRecipient(MailMessage message) {
		return message.getReceivers().size() > 0 ? true : false;
	}

	private static boolean messageHasContent(MailMessage message) {
		return message.getContent() != null && !message.getContent().isEmpty() ? true : false;
	}

	private static boolean messageHasSubject(MailMessage message) {
		return message.getSubject() != null && !message.getSubject().isEmpty() ? true : false;
	}

	private static boolean validateMailReceivers(List<? extends Recipient> receivers) {
		for (Recipient receiver : receivers) {
			if (!MessageValidator.validateMailRecipient(receiver)) {
				return false;
			}
		}
		return true;
	}

	private static boolean validateMailRecipient(Recipient receiver) {
		Pattern pattern = Pattern.compile(MessageValidator.REGEX_VALID_EMAIL_ADDRESS, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(receiver.getAddress());

		return matcher.matches();
	}

}
