package cz.mudraon.controller;

import cz.mudraon.shared.Message;
import cz.mudraon.client.ClientTempDB;
import cz.mudraon.client.MessageValidator;
import cz.mudraon.client.QuartzMessage;
import cz.mudraon.shared.MessageApi;
import java.util.Date;
import java.util.Map;
import org.apache.log4j.Logger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
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

    private Scheduler scheduler = null;

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
            if (scheduler == null && api != null){
                quartzInit();
            }

            // create clientMessage
            _message = new Message(_messageData.getMessage(), new Date());

            String serverMessage = api.getOnlyMessage(_message);
            String serverDate = api.getOnlyDate(_message);

            //String received = serverMessage + "<br>" + serverDate + "<br>";
            String received = api.getAllMessage(_message);

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

    private void quartzInit() {
        JobDataMap dataMap = new JobDataMap();
        dataMap.put("messageApi", api);
        JobDetail job = JobBuilder.newJob(QuartzMessage.class).withIdentity("jobSend", "group1").setJobData(dataMap).build();
        Trigger trigger = TriggerBuilder.newTrigger().
                withIdentity("triggerSend", "group1").
                withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(30).repeatForever()).
                build();
        try {
            scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            logger.error("Scheduler hasn't been created.", e);
        }
    }
}
