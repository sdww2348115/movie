package movie.pipeline;

import movie.dao.MovieDao;
import movie.entity.Movie;
import movie.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.lang.reflect.Field;

/**
 * Created by sdww on 2015/11/23.
 */
@Component("moviePipeline")
public class MoviePipeline implements Pipeline {

    @Autowired
    MovieDao movieDao;

    /**
     * Process extracted results.
     * get movie info,keep them to dataBase
     */
    @Override
    public void process(ResultItems resultItems, Task task) {
        Movie movie = new Movie();
        Field[] fields = movie.getClass().getDeclaredFields();
        for(Field field:fields) {
            String value = resultItems.get(field.getName());
            BeanUtils.setProperty(movie, field, value);
        }
    }

}
