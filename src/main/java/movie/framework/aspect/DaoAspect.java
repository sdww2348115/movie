package movie.framework.aspect;

import movie.entity.Movie;
import movie.utils.BeanUtils;
import org.aspectj.lang.JoinPoint;

/**
 * Created by sdww on 2015/11/26.
 */
public class DaoAspect {

    public static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(BeanUtils.class);

    /**
     * 在将Movie类传入数据库之前先处理，如果时间为null则置0.此处在数据库设置DEFAULT 0更好，此处只为练习AOP编程
     * @param joinpoint
     */
    public void before(JoinPoint joinpoint) {
        try {
            Object[] args = joinpoint.getArgs();
            for (Object obj : args) {
                if (Movie.class.isInstance(obj)) {
                    Movie movie = (Movie) obj;
                    if (movie.getRuntime() == null) {
                        movie.setRuntime(0);
                    }
                }
            }
        } catch (Exception e) {
            logger.warn(e.getStackTrace().toString());
        }
    }

}
