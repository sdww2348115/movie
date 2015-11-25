package movie;

import movie.PageProcessor.DoubanMoviePageProcessor;
import movie.PageProcessor.GithubRepoPageProcessor;
import movie.dao.MovieDao;
import movie.entity.Movie;
import movie.pipeline.MoviePipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

/**
 * Created by sdww on 2015/11/24.
 */
@Component
public class Main {

    @Autowired
    MoviePipeline moviePipeline;

    public void go() {
        Spider.create(new DoubanMoviePageProcessor()).addUrl("http://www.douban.com/tag/爱情/movie").addPipeline(moviePipeline).thread(5).run();
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/spring/applicationContext*.xml");
        final Main main = applicationContext.getBean(Main.class);
        main.go();
    }
}
