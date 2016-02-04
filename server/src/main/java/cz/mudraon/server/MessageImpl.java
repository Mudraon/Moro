package cz.mudraon.server;

import cz.mudraon.shared.MessageApi;
import java.util.Date;

public class MessageImpl implements MessageApi{

    @Override
    public String Message(String message) {
        return "Message received from client: " + message; 
    }

    @Override
    public String Time(String time) {
        return "Date received from client: " + time + ", Date on server: " + new Date().toString(); 
    }
    
}
