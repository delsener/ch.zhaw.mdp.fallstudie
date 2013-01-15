package ch.zhaw.mdp.fallstudie.jmail.core.validator;

import ch.zhaw.mdp.fallstudie.jmail.core.messages.MailMessage;

public interface IMessageValidator {

	public abstract boolean validate(MailMessage message);

}