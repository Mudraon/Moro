package cz.mudraon.controller;

import cz.mudraon.shared.Message;
import cz.mudraon.shared.ClientTempDB;
import java.util.Date;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SendController {
    ClientTempDB memoryDB = new ClientTempDB();
    Message _message;
    
    //@RequestMapping(method = RequestMethod.GET)
    @RequestMapping("/send")
    public ModelAndView showMessage(ModelMap model) {
        _message = new Message();
        _message.setTime(new Date().toString());
        return new ModelAndView("send", "message", _message.getTime());
    }

    @RequestMapping(value = "/sendMessage",method = RequestMethod.POST, params = {"zprava"})
    public ModelAndView getMessageAndDate(@RequestParam String zprava, ModelMap model) {
        _message = new Message(zprava, new Date().toString());
        String received = "Received: " + _message.getMessage() + ", Date: " + _message.getTime() + "<br>";
        
        memoryDB.save(_message);
        
        received += "<br>HISTORY<br>";
        for (Message message : memoryDB.getListOfMessage()) {
            received += "Message: " + message.getMessage() + ", Date: " + message.getTime() + "<br>";
        }

        return new ModelAndView("sendMessage", "zpravaWeb", received);
    }

}
