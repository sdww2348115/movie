package movie;

import movie.PageProcessor.DoubanMoviePageProcessorB;
import movie.pipeline.MoviePipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.QueueScheduler;

/**
 * 工程的自定义Bean
 * Created by sdww on 2015/12/4.
 */
@Configuration
public class MovieConfig {

    @Autowired
    MoviePipeline moviePipeline;

    @Bean
    public Spider movieSpider() {
        return Spider.create(new DoubanMoviePageProcessorB()).addUrl("http://movie.douban.com/tag/").addPipeline(moviePipeline).thread(5);
    }

    /**
     * 由于spring的AOP只支持接口继承，创建一个queueScheduler来打印相应日志，查看queueScheduler中的url个数
     * @return
     */
    @Bean
    public QueueScheduler queueScheduler() {
        return new QueueScheduler();
    }
}
