package movie.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by sdww on 2015/11/23.
 */
public class BeanUtils {

    public static final Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    /**
     * 利用反射向bean中设置相应值
     * @item:向此类中设置值的类
     * @fieldName:该field的名称
     * @value:向该field中设的具体值
     */
    public static void setProperty(Object item, Field field, Object value) {
        try {
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), item.getClass());
            Method setMethod = propertyDescriptor.getWriteMethod();
            setMethod.invoke(item, value);
        } catch (Exception e) {
            logger.warn(e.getStackTrace().toString());
        }
    }
}
