package cz.mudraon.shared;

public class Message {
    
    private String Message;
    private String Time;

    public Message() {
    }

    public Message(String Message, String Time) {
        this.Message = Message;
        this.Time = Time;
    }
   
    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }
}
