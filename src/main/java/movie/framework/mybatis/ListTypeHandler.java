package movie.framework.mybatis;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sdww on 2015/11/24.
 */
@MappedJdbcTypes(includeNullJdbcType = true, value = {JdbcType.VARCHAR})
public class ListTypeHandler extends BaseTypeHandler<List> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List list, JdbcType jdbcType) throws SQLException {
        if(list == null) return;
        StringBuilder stringBuilder = new StringBuilder('/');
        for(Object obj:list) {
            stringBuilder.append(obj.toString());
            stringBuilder.append('/');
        }
        ps.setString(i, new String(stringBuilder));
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        List<String> list = new LinkedList<String>();
        String string = rs.getString(columnName);
        if(list == null) return list;
        String[] vals = string.split("/");
        for(String val :vals) {
            if(StringUtils.isNotBlank(val)) {
                list.add(val);
            }
        }
        return list;
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        List<String> list = new LinkedList<String>();
        String string = rs.getString(columnIndex);
        if(list == null) return list;
        String[] vals = string.split("/");
        for(String val :vals) {
            if(StringUtils.isNotBlank(val)) {
                list.add(val);
            }
        }
        return list;
    }

    @Override
    public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        List<String> list = new LinkedList<String>();
        String string = cs.getString(columnIndex);
        if(list == null) return list;
        String[] vals = string.split("/");
        for(String val :vals) {
            if(StringUtils.isNotBlank(val)) {
                list.add(val);
            }
        }
        return list;
    }
}
