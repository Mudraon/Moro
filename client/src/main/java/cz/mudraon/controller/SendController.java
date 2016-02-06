package cz.mudraon.controller;

import cz.mudraon.shared.Message;
import cz.mudraon.client.ClientTempDB;
import cz.mudraon.client.MessageValidator;
import cz.mudraon.shared.MessageApi;
import java.util.Date;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SendController {

    private final Logger logger = Logger.getLogger(SendController.class);
    private final ClientTempDB memoryDB = new ClientTempDB();
    private Message _message;

    private ApplicationContext ctx = null;
    private MessageApi api = null;

    @RequestMapping("/send")
    public ModelAndView showMessage(ModelMap model) {
        //_message = new Message(_message, _date);
        //_message.setTime(new Date().toString());
        return new ModelAndView("send", "message", null);
    }

    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    public ModelAndView sendMessage(Model model, @ModelAttribute("message") Message _messageData, BindingResult result) {
        MessageValidator validate = new MessageValidator();
        validate.validate(_messageData, result);
        if (!result.hasErrors()) {
            if (ctx == null || api == null) {
                beanInit();
            }

            // create clientMessage
            _message = new Message(_messageData.getMessage(), new Date());

            String serverMessage = api.getOnlyMessage(_message);
            String serverDate = api.getOnlyDate(_message);

            String received = serverMessage + "<br>" + serverDate + "<br>";

            // create serverMessage and add to local history 
            _message = new Message(serverMessage, serverDate);
            memoryDB.save(_message);

            // extract message to webPage and get all messages from local history
            received += "<br>HISTORY<br>";
            for (Message message : memoryDB.getListOfMessage()) {
                received += message.getMessage() + " --- " + message.getServerDate() + "<br>";
            }

            return new ModelAndView("sendMessage", "zpravaWeb", received);
        } else {
            return new ModelAndView("errorValidate", "error", result.getFieldError("message").getCode());
        }
    }

    private void beanInit() {
        try {
            ctx = new ClassPathXmlApplicationContext("spring-config-client.xml");
            api = (MessageApi) ctx.getBean("messageService");
            logger.info("Bean has been created!!!");
        } catch (Exception e) {
            logger.error("Bean hasn't been created.", e);
        }
    }
}
