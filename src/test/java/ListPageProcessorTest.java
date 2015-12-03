import junit.framework.TestCase;
import movie.PageProcessor.ConcreteProcessor.ListPageProcessor;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.PlainText;

/**
 * Created by sdww on 2015/12/3.
 */
public class ListPageProcessorTest extends TestCase {

    public void testProcess() throws Exception {
        ListPageProcessor listPageProcessor = new ListPageProcessor();
        Page page = new Page();
        page.setUrl(new PlainText("http://www.douban.com/tag/爱情/movie"));
        page.setHtml(new Html("<a href=\"/v/ac2365706\" target=\"_blank\" class=\"thumb\"><img src=\"http://cdn.aixifan.com/dotnet/artemis/u/cms/www/201512/031022594rf81enp.jpg\" class=\"preview\"><div class=\"area-title mask\"><p class=\"title\">玩的就是心跳 口袋妖怪漆黑的魅影自虐攻略</p></div></a>"));
        listPageProcessor.process(page);
        assertEquals(page.getTargetRequests().size(), 0);
    }
}
