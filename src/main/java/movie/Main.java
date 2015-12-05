package movie;

import movie.PageProcessor.DoubanMoviePageProcessor;
import movie.PageProcessor.DoubanMoviePageProcessorB;
import movie.pipeline.MoviePipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

/**
 * Created by sdww on 2015/11/24.
 */
@Component("main")
public class Main {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/spring/applicationContext*.xml");
        final Spider spider = (Spider)applicationContext.getBean("movieSpider");
        spider.run();
    }
}
