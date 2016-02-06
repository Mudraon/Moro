package cz.mudraon.client;

import cz.mudraon.shared.Message;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author Mutagen
 */
public class MessageValidator  implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return (Message.class.equals(type));
    }

    @Override
    public void validate(Object o, Errors errors) {
       Message message = (Message) o;
       if (message.getMessage() == null || message.getMessage().equals("")){
           errors.rejectValue("message", "Message has been blank.");
       }
    }
    
}
