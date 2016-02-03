package cz.mudraon.shared;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mutagen
 */
public class ClientTempDB implements MessageApi {

    private List<Message> listOfMessage;
    private String _message;
    private String _time;

    public ClientTempDB() {
        listOfMessage = new ArrayList<>();
    }

    @Override
    public String Message(String message) {
        _message = message;
        return message;
    }

    @Override
    public String Time(String time) {
        _time = time;
        return time;
    }

    public void save() {
        Message _obj = new Message(_message, _time);
        listOfMessage.add(_obj);
    }

    public void save(Message _obj) {
        listOfMessage.add(_obj);
    }

    public void save(String message, String time) {
        Message _obj = new Message(message, time);
        listOfMessage.add(_obj);
    }

    public List<cz.mudraon.shared.Message> getListOfMessage() {
        return listOfMessage;
    }
}
