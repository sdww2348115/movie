package movie.PageProcessor.ConcreteProcessor;

import us.codecraft.webmagic.Page;

import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;

/**
 * 豆瓣电影tag列表页processor
 * Created by sdww on 2015/12/2.
 */
public class ListPageProcessor extends BaseProcessor{
    public static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ListPageProcessor.class);

    public ListPageProcessor() {
        super("http[s]{0,1}://www.douban.com/tag/[\\u4e00-\\u9fa5a-zA-Z0-9]+/movie");
    }

    @Override
    public void process(Page page) {

        //因为豆瓣的列表页面为动态生成的，当一个列表页没有具体电影信息的话，即可忽略此页后续的链接
        if(page.getHtml().links().regex("http[s]{0,1}://movie.douban.com/subject/[\\d]+/\\?from=tag_all").all() == null || page.getHtml().links().regex("http[s]{0,1}://movie.douban.com/subject/[\\d]+/\\?from=tag_all").all().size() == 0) {

            String tag = "UNKNOWN";
            Scanner scanner = new Scanner(page.getUrl().get());
            final String pattern = "http[s]{0,1}://www.douban.com/tag/([\\u4e00-\\u9fa5a-zA-Z0-9]+)/movie";
            if(scanner.hasNext(pattern)) {
                scanner.next(pattern);
                MatchResult matchResult = scanner.match();
                tag = matchResult.group(1);
            }

            logger.warn("tag " + tag + " complete!");
            return;
        }

        //添加该页的所有列表页与具体电影页
        List<String> urls = page.getHtml().links().regex("http[s]{0,1}://www.douban.com/tag/[\\u4e00-\\u9fa5a-zA-Z0-9]+/movie\\?start=[\\d]+").all();
        urls.addAll(page.getHtml().links().regex("http[s]{0,1}://movie.douban.com/subject/[\\d]+/\\?from=tag_all").all());
        page.addTargetRequests(urls);
        return;
    }
}
