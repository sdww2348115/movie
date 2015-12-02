package movie.PageProcessor.ConcreteProcessor;

import us.codecraft.webmagic.Page;

/**
 * Created by sdww on 2015/12/2.
 */
public abstract class BaseProcessor {

    private String targetUrl;

    public BaseProcessor(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    /**
     * 具体处理类
     * @param page
     */
    public abstract void process(Page page);

    /**
     * 是否用process方法处理该Page对象
     * @param page
     * @return ture or false
     */
    public boolean accept(Page page) {
        if(page == null || page.getUrl().get() == null) {
            return false;
        } else {
            if(page.getUrl().get().matches(targetUrl)) {
                return true;
            } else {
                return false;
            }
        }

    }
}
