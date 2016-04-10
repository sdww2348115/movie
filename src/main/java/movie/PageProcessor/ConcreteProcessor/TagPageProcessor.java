package movie.PageProcessor.ConcreteProcessor;

import us.codecraft.webmagic.Page;

import java.util.LinkedList;
import java.util.List;

/**
 * 豆瓣电影tag首页processor
 * Created by sdww on 2015/12/2.
 */
public class TagPageProcessor extends BaseProcessor{

    private static final String urlTemplete = "https://www.douban.com/tag/%s/movie";

    public TagPageProcessor() {
        super("http[s]{0,1}://movie.douban.com/tag/");
    }

    @Override
    public void process(Page page) {
        List<String> urls = new LinkedList<String>();
        List<String> tags = page.getHtml().$("a[class=tag]", "innerHtml").all();
        for(String tag:tags) {
            String url = String.format(urlTemplete, tag);
            urls.add(url);
        }
        page.addTargetRequests(urls);
    }
}
