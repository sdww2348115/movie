package movie.PageProcessor;

import movie.PageProcessor.ConcreteProcessor.BaseProcessor;
import movie.PageProcessor.ConcreteProcessor.ListPageProcessor;
import movie.PageProcessor.ConcreteProcessor.MovieProcessor;
import movie.PageProcessor.ConcreteProcessor.TagPageProcessor;
import movie.utils.UserAgentUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.ProxyPool;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by sdww on 2015/12/2.
 */
public class DoubanMoviePageProcessorB implements PageProcessor {

    public static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DoubanMoviePageProcessorB.class);

    private List<BaseProcessor> processors = new LinkedList<BaseProcessor>();

    private long startTime = 0;

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
        List<String[]> proxys = new LinkedList<String[]>();
        proxys.add(new String[]{"14.145.133.219", "9797"});
        site.setHttpProxyPool(proxys);
        processors.add(new MovieProcessor());
        processors.add(new ListPageProcessor());
        processors.add(new TagPageProcessor());
    }

    @Override
    public void process(Page page) {

       /* if(System.currentTimeMillis() - startTime >= 1000 * 60 * 3) {
            startTime = System.currentTimeMillis();
            site.setUserAgent(UserAgentUtils.getUserAgent());
            logger.info("userAgent is update :{}", site.getUserAgent());
        }*/

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
