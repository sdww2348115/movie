package movie.PageProcessor.ConcreteProcessor;

import us.codecraft.webmagic.Page;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by sdww on 2015/12/2.
 */
public class MovieProcessor extends BaseProcessor{

    public static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MovieProcessor.class);

    public MovieProcessor() {
        super("http://movie.douban.com/subject/[\\d]+(.*)");
    }

    @Override
    public void process(Page page) {
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
