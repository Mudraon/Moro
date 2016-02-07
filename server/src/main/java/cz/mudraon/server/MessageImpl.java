package cz.mudraon.server;

import cz.mudraon.shared.Message;
import cz.mudraon.shared.MessageApi;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;

public class MessageImpl implements MessageApi {

    private final SimpleDateFormat output = new SimpleDateFormat("HH:mm:ss.SSS");
    private final Logger logger = Logger.getLogger(MessageImpl.class);
    private final PostgreSQLClass db = new PostgreSQLClass();

    /**
     * Slouží také i pro zápis (jako jediné)
     *
     * @param _messageObj entita co se bude ukládat
     * @return
     */
    @Override

    public String getAllMessage(Message _messageObj) {
        if (_messageObj.getServerDate() == null){
            _messageObj.setServerDate(new Date().toString());
        }
        String _message = "Message received from client: " + _messageObj.getMessage() + " --- " + "Date received from client: " + output.format(_messageObj.getDate()) + ", Server date: " + output.format(new Date());
        boolean isSaved = db.saveHibernate(_messageObj);
        if (!isSaved) {
            _message += "<span style='color:red'> !!! Can't save into DB !!!</span>";
        } else {
            _message += "<span style='color:green'> !!! Save into DB !!!</span>";
        }
        _message += "<br>";
        logger.info("Server received object with " + _messageObj.hashCode() + " hashcode");
        return _message;
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
