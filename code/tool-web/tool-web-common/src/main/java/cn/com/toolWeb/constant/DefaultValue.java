package cn.com.toolWeb.constant;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 默认值
 *
 * @author jack.huang
 */
public class DefaultValue {
    /** long 的默认值 */
    public static final Long LONG_DEFAULT = 0L;
    /** int 的默认值 */
    public static final Integer INT_DEFAULT = 0;
    /** varchar 的默认值 */
    public static final String VARCHAR_DEFAULT = "";
    /** dateTime 的默认值（最小值） 由于 0000:00:00转不出来 */
    public static final LocalDateTime TIME_DEFAULT_MIN = LocalDateTime.parse("0001-01-01 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    /** dateTime 的默认值字符串类型 */
    public static final String TIME_DEFAULT_STR = "0000:00:00 00:00:00";

    /**
     * 是否为默认值
     *
     * @return 是否为默认值
     */
    public static Boolean isDefault(Object o) {
        if (o == null) {
            return true;
        } else if (o instanceof Long) {
            return LONG_DEFAULT.equals(o);
        } else if (o instanceof Integer) {
            return INT_DEFAULT.equals(o);
        } else if (o instanceof String) {
            return VARCHAR_DEFAULT.equals(o);
        }
        return false;
    }
}
