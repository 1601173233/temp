package cn.com.toolWeb.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * bean 操作工具类
 * @author hyj
 */
public class BeanUtils {
    /**
     * 列对象转换
     *
     * @param list 列表
     * @param c 模板类
     * @param <T> 当前类型
     * @return 列表结果
     */
    public static <T> List<T> convertList(List<?> list, Class<T> c) {
        if (ObjectUtil.isEmpty(list)) {
            return new ArrayList<>();
        }

        List<T> result = new ArrayList<>();
        for (Object o : list) {
            if (o != null) {
                result.add(BeanUtil.copyProperties(o, c));
            }
        }

        return result;
    }
}
