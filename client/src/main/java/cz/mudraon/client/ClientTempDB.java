package cz.mudraon.client;

import cz.mudraon.shared.Message;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Mutagen
 */
public class ClientTempDB {

    private final Logger logger = Logger.getLogger(ClientTempDB.class);
    private final List<Message> listOfMessage;
    private String _message;
    private String _date;

    public ClientTempDB() {
        listOfMessage = new ArrayList<>();
    }

    public void save() {
        Message _obj = new Message(_message, _date);
        listOfMessage.add(_obj);
    }

    public void save(Message _obj) {
        listOfMessage.add(_obj);
    }

    public void save(String message, Date date) {
        Message _obj = new Message(message, date);
        try {
            listOfMessage.add(_obj);
            logger.info("Message has been saved.");
        } catch (Exception e) {
            logger.error("Message hasn't been saved.", e);
        }
    }

    public List<cz.mudraon.shared.Message> getListOfMessage() {
        return listOfMessage;
    }
}
