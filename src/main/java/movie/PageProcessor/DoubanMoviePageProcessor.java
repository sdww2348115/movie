package movie.PageProcessor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;
import us.codecraft.webmagic.selector.Selector;

import java.util.List;

/**
 * Created by sdww on 2015/11/24.
 */
public class DoubanMoviePageProcessor implements PageProcessor {

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

        //作者的选择器在此使用时不能正常工作，故使用xPath选择器
        //page.putField("title", page.getHtml().$("h1 span[property='v:itemreviewed']", "innerHtml"));
        page.putField("title", page.getHtml().xpath("h1/span[@property='v:itemreviewed']/text()").toString());
        page.putField("image", page.getHtml().$("#mainpic img", "src"));
        List<String> xxx = page.getHtml().xpath("div[@id='info']//a[@rel='v:directedBy']/text()").all();
        page.putField("director", page.getHtml().xpath("div[@id='info']//a[@rel='v:directedBy']/text()").all());
        page.putField("actor", page.getHtml().$("#info .attrs").$("a[rel='v:starring']", "innerHtml"));

    }
}
