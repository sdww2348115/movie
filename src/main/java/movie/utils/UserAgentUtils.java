package movie.utils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.downloader.HttpClientDownloader;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sdww on 2016/4/12.
 */
public class UserAgentUtils {

    private static List<String> userAgents = new LinkedList<String>();

    private static Downloader downloader = new HttpClientDownloader();

    private static Request request = new Request("http://www.atool.org/useragent.php");

    private static Task task = new Task() {
        @Override
        public String getUUID() {
            return null;
        }

        @Override
        public Site getSite() {
            return Site.me().setRetryTimes(3).setSleepTime(10000).setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.57 Safari/537.36");
        }
    };

    public static String getUserAgent() {

        //如果队列中没有userAgent，则通过http://www.atool.org/useragent.php网页下载相应的动态userAgent
        if(userAgents.size() == 0) {
            Page page = downloader.download(request, task);
            List<String> userAgentsStr = page.getHtml().$(".new_tools_list li", "innerHtml").all();
            Pattern pattern = Pattern.compile("Windows");
            //Matcher matcher = pattern.matcher()
            for(String str: userAgentsStr) {
                Matcher matcher = pattern.matcher(str);
                if(matcher.find()) {
                    userAgents.add(str);
                }
            }
        }
        return userAgents.remove(0);
    }

    public static void main(String[] args) {
        String userAgent = UserAgentUtils.getUserAgent();
    }
}
