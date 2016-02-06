package cz.mudraon.server;

import cz.mudraon.shared.Message;
import cz.mudraon.shared.MessageApi;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;

public class MessageImpl implements MessageApi{

    private final SimpleDateFormat output = new SimpleDateFormat("HH:mm:ss.SSS");
    private final Logger logger = Logger.getLogger(MessageImpl.class);
    
    @Override
    public String getAllMessage(Message _messageObj) {
        logger.info("Server received object with " + _messageObj.hashCode() + " hashcode");
        return "Message received from client: " + _messageObj.getMessage() + " --- " + "Date received from client: " + output.format(_messageObj.getDate()) + ", Server date: " + output.format(new Date());
    }

    @Override
    public String getOnlyMessage(Message _messageObj) {
        logger.info("Server received object with " + _messageObj.hashCode() + " hashcode");
        return "Message received from client: " + _messageObj.getMessage();
    }

    @Override
    public String getOnlyDate(Message _messageObj) {
        logger.info("Server received object with " + _messageObj.hashCode() + " hashcode");
        return "Date received from client: " + output.format(_messageObj.getDate()) + ", Server date: " + output.format(new Date());
    }
   
}
