package cz.mudraon.client;

import cz.mudraon.shared.Message;
import cz.mudraon.shared.MessageApi;
import java.util.Date;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author Mutagen
 */
public class QuartzMessage implements Job {

    private final Logger logger = Logger.getLogger(QuartzMessage.class);
    private MessageApi api;

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        api = (MessageApi) jec.getJobDetail().getJobDataMap().get("messageApi");
        Message _message = new Message("Scheduler message to server", new Date());
        String sendMessage = api.getOnlyMessage(_message);
        // Při zápisu do DB použít api.getAllMessage(_message);
        logger.info(sendMessage);
        //System.out.println(sendMessage);
    }
}
