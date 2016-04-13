package movie.framework.aspect;

import org.aspectj.lang.JoinPoint;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.QueueScheduler;

/**
 * Created by sdww on 2015/12/4.
 */
public class SchedulerAspect {

    public static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SchedulerAspect.class);

    public void before(JoinPoint joinPoint) {

        try {

            QueueScheduler scheduler = (QueueScheduler) joinPoint.getTarget();

            //由源码可知，整个scheduler只有这么一个单例，task参数在此无用，所以给一个默认参数
            Task task = new Task() {
                @Override
                public String getUUID() {
                    return null;
                }

                @Override
                public Site getSite() {
                    return null;
                }
            };

            //找出task参数
            Object[] args = joinPoint.getArgs();
            for (Object arg : args) {
                if (Task.class.isInstance(arg)) {
                    task = (Task) arg;
                }
            }

            logger.info("Left Requests Count: " + scheduler.getLeftRequestsCount(task) + " Total Requests Count: " + scheduler.getTotalRequestsCount(task));
        } catch (Exception e) {
            logger.warn("SchedulerAspect before error!");
        }
    }
}
