package cz.mudraon.shared;

import java.util.Date;

public class Message implements java.io.Serializable {

    private String message;
    private Date date;
    private String serverDate;

    public Message() {
    }

    public Message(String _message, Date _date) {
        message = _message;
        date = _date;
    }

    public Message(String _message, String _serverDate) {
        message = _message;
        serverDate = _serverDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getServerDate() {
        return serverDate;
    }

    public void setServerDate(String serverDate) {
        this.serverDate = serverDate;
    }

}
