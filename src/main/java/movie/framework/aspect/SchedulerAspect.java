package movie.framework.aspect;

import org.aspectj.lang.JoinPoint;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.QueueScheduler;

/**
 * Created by sdww on 2015/12/4.
 */
public class SchedulerAspect {

    public static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SchedulerAspect.class);

    public void before(JoinPoint joinPoint) {
        Spider spider = (Spider)joinPoint.getTarget();
        QueueScheduler scheduler = (QueueScheduler)spider.getScheduler();
        logger.info("Alive threads : " + spider.getThreadAlive() + "\n" + "Left Requests Count: " + scheduler.getLeftRequestsCount(spider) + " Total Requests Count: " + scheduler.getTotalRequestsCount(spider));
    }
}
