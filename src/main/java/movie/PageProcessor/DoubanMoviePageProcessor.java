package movie.PageProcessor;

import movie.utils.BeanUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by sdww on 2015/11/24.
 */
public class DoubanMoviePageProcessor implements PageProcessor {

    public static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(BeanUtils.class);

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100).setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.57 Safari/537.36");

    @Override
    public Site getSite() {
        return site;
    }

    @Override
    public void process(Page page) {
        //添加下一步页面
        List<String> urls = page.getHtml().links().regex("http://www.douban.com/tag/爱情/movie\\?start=[\\d]+").all();
        urls.addAll(page.getHtml().links().regex("http://movie.douban.com/subject/[\\d]+/\\?from=tag_all").all());
        page.addTargetRequests(urls);

        //如果是电影列表页的话直接继续接下来的步骤
        if(!page.getRequest().getUrl().matches("http://movie.douban.com/subject/[\\d]+(.*)")) {
            return;
        }

        //抓取相应信息
        /**
         * 注：使用jsoup时与js使用的CSS选择器有些许不同，例如a[rel='v:starring']中间的''在JS中代表字符串，但是于jsoup中却不需要此字符
         */
        page.putField("title", page.getHtml().$("h1 span[property=v:itemreviewed]", "innerHtml").get());
        page.putField("image", page.getHtml().$("#mainpic img", "src").get());
        page.putField("director", page.getHtml().xpath("div[@id='info']//a[@rel='v:directedBy']/text()").all());
        page.putField("actor", page.getHtml().$("#info .attrs a[rel=v:starring]", "innerHtml").all());
        page.putField("genre", page.getHtml().$("#info span[property=v:genre]", "innerHtml").all());

        //只记录大陆上映时间
        List<String> dates = page.getHtml().$("#info span[property=v:initialReleaseDate]", "innerHtml").all();
        for(String date:dates) {
            if(date.contains("中国大陆")) {
                page.putField("initialReleaseDate", date);
            }
        }

        try {
            page.putField("runtime", Integer.valueOf(page.getHtml().$("#info span[property=v:runtime]", "innerHtml").regex("[\\d]+").get()));
        } catch (Exception e) {
            logger.warn(e.getStackTrace().toString());
        }

        try {
            page.putField("averageScore", new BigDecimal(page.getHtml().$("strong[property=v:average]", "innerHtml").get()));
        } catch (Exception e) {
            logger.warn(e.getStackTrace().toString());
        }

        try {
            page.putField("ratingNum", Integer.valueOf(page.getHtml().$(".rating_sum span[property=v:votes]", "innerHtml").get()));
        } catch (Exception e) {
            logger.warn(e.getStackTrace().toString());
        }
        
        page.putField("summary", page.getHtml().$("span[property=v:summary]", "innerHtml").get());
    }
}
