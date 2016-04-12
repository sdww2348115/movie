package movie.PageProcessor;

import movie.PageProcessor.ConcreteProcessor.BaseProcessor;
import movie.PageProcessor.ConcreteProcessor.ListPageProcessor;
import movie.PageProcessor.ConcreteProcessor.MovieProcessor;
import movie.PageProcessor.ConcreteProcessor.TagPageProcessor;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by sdww on 2015/12/2.
 */
public class DoubanMoviePageProcessorB implements PageProcessor {

    public static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DoubanMoviePageProcessorB.class);

    private List<BaseProcessor> processors = new LinkedList<BaseProcessor>();

    private long startTime = 0;

    private List<String> userAgents = new LinkedList<String>();

    private Site site = Site.me().setRetryTimes(3).setSleepTime(10000).setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.57 Safari/537.36");

    @Override
    public Site getSite() {
        return site;
    }

    /**
     * 豆瓣的pageProcessor，默认添加已有的3个
     * 由于此构建函数于spider类创建时创建，所以不存在并发性问题
     */
    public DoubanMoviePageProcessorB() {
        userAgents.add("Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML like Gecko) Chrome/28.0.1469.0 Safari/537.36");
        userAgents.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.57 Safari/537.36");
        userAgents.add("Mozilla/5.0 (Windows NT 5.1; rv:36.0) Gecko/20100101 Firefox/36.0");
        userAgents.add("Mozilla/5.0 (Windows NT 6.2; rv:41.0) Gecko/20100101 Firefox/43.0");
        userAgents.add("Mozilla/5.0 (Windows NT 6.3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
        userAgents.add("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
        userAgents.add("Mozilla/5.0 (Windows NT 6.2; rv:39.0) Gecko/20100101 Firefox/37.0");
        userAgents.add("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.89 Safari/537.36");
        processors.add(new MovieProcessor());
        processors.add(new ListPageProcessor());
        processors.add(new TagPageProcessor());
    }

    @Override
    public void process(Page page) {

        if(System.currentTimeMillis() - startTime >= 1000 * 60 * 30) {
            startTime = System.currentTimeMillis();
            site.setUserAgent(userAgents.remove(0));
        }

        try {
            if(page == null) {
                throw new Exception("page is null");
            }
            /**
             * 责任链模式，一旦accept为true便调用process函数
             */
            for(BaseProcessor processor:processors) {
                if(processor.accept(page)) {
                    processor.process(page);
                }

                if(page.getTargetRequests().size() == 0) {
                    logger.info("page {} has no targetUrl", page.getUrl().get());
                }
            }
        }catch (Exception e) {
            logger.warn(e.getStackTrace().toString());
        }
    }
}
