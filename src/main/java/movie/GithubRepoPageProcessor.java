package movie;

import movie.dao.MovieDao;
import movie.entity.Movie;
import movie.pipeline.MoviePipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

@Component
public class GithubRepoPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    @Autowired
    MoviePipeline moviePipeline;

    @Override
    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());
        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
        if (page.getResultItems().get("name")==null){
            //skip this page
            page.setSkip(true);
        }
        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
        System.out.println(page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public void go() {
        Spider.create(new GithubRepoPageProcessor()).addUrl("https://github.com/code4craft").addPipeline(moviePipeline).thread(5).run();
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/spring/applicationContext*.xml");
        final GithubRepoPageProcessor githubRepoPageProcessor = applicationContext.getBean(GithubRepoPageProcessor.class);
        githubRepoPageProcessor.go();
    }
}