package cn.com.codeBuilder.util;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 集合类工具类
 *
 * PS: 纯粹我不会写stream相关函数，所以封装一下
 * @author jack.huang
 */
@Slf4j
public class CollectUtils {

    /**
     * 用list数据流去重
     *
     * @param list 列表
     * @return 去重后的列表
     */
    public static <T> List<T> distinct(Collection<T> list) {
        if (ObjectUtil.isEmpty(list)) {
            return new ArrayList<>();
        }

        return list.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
    }

    /**
     * 获取指定字段去重的值
     *
     * @param list 列表
     * @param mapper 字段
     * @return 去重后的指定字段的列表
     */
    public static <T, R> List<R> distinctParams(List<T> list, Function<? super T, ? extends R> mapper) {
        if (ObjectUtil.isEmpty(list)) {
            return new ArrayList<>();
        }

        return list.stream().filter(Objects::nonNull).map(mapper).distinct().collect(Collectors.toList());
    }

    /**
     * 获取指定字段去重的值（Set）
     *
     * @param list 列表
     * @param mapper 字段
     * @return 去重后的指定字段的列表
     */
    public static <T, R> Set<R> distinctParamsSet(List<T> list, Function<? super T, ? extends R> mapper) {
        if (ObjectUtil.isEmpty(list)) {
            return new HashSet<>();
        }

        return list.stream().filter(Objects::nonNull).map(mapper).collect(Collectors.toSet());
    }

    /**
     * 获取指定字段去重的值
     *
     * @param list 列表
     * @param mapper 字段
     * @return 去重后的指定字段的列表
     */
    public static <T, R> List<R> paramsList(List<T> list, Function<? super T, ? extends R> mapper) {
        if (ObjectUtil.isEmpty(list)) {
            return new ArrayList<>();
        }

        return list.stream().filter(Objects::nonNull).map(mapper).collect(Collectors.toList());
    }

    /**
     * 过滤
     *
     * @param list 列表
     * @return 过滤后的列表
     */
    public static <T> List<T> filter(Collection<T> list, Predicate<? super T> mapper) {
        if (ObjectUtil.isEmpty(list)) {
            return new ArrayList<>();
        }

        return list.stream().filter(Objects::nonNull).filter(mapper).collect(Collectors.toList());
    }

    /**
     * 根据指定字段进行分类
     *
     * @param list 列表
     * @param classifier 字段
     * @return 分类后的值
     */
    public static <T, R> Map<R, List<T>> groupBy(Collection<T> list, Function<? super T, ? extends R> classifier) {
        if (ObjectUtil.isEmpty(list)) {
            return new HashMap<>(1);
        }

        return list.stream().filter(Objects::nonNull).collect(Collectors.groupingBy(classifier));
    }

    /**
     * map归类指定对象
     *
     * @param list 列表
     * @param keyMapper 字段
     * @return 分类后的值
     */
    public static <T, K> Map<K, T> toMap(Collection<T> list, Function<? super T, ? extends K> keyMapper) {
        if (ObjectUtil.isEmpty(list)) {
            return new HashMap<>(1);
        }

        return toMap(list, keyMapper, o -> o);
    }

    /**
     * map归类指定对象
     *
     * @param list 列表
     * @param keyMapper 字段
     * @param mapSupplier map构造方法
     * @return 分类后的值
     */
    public static <T, K, M extends Map<K, T>> Map<K, T> toTargetMap(Collection<T> list,
                                                                    Function<? super T, ? extends K> keyMapper,
                                                                    Supplier<M> mapSupplier) {
        if (ObjectUtil.isEmpty(list)) {
            return new HashMap<>(1);
        }

        return toMap(list, keyMapper, o -> o, mapSupplier);
    }

    /**
     * map归类指定对象
     *
     * @param list 列表
     * @param keyMapper key字段
     * @param valueMapper value字段
     * @return 分类后的值
     */
    public static <T, K, U> Map<K, U> toMap(Collection<T> list,
                                            Function<? super T, ? extends K> keyMapper,
                                            Function<? super T, ? extends U> valueMapper) {
        return toMap(list, keyMapper, valueMapper, HashMap::new);
    }

    /**
     * map归类指定对象
     *
     * @param list 列表
     * @param keyMapper key字段
     * @param valueMapper value字段
     * @param mapSupplier map构造方法
     * @return 分类后的值
     */
    public static <T, K, U, M extends Map<K, U>> Map<K, U> toMap(Collection<T> list,
                                                                 Function<? super T, ? extends K> keyMapper,
                                                                 Function<? super T, ? extends U> valueMapper,
                                                                 Supplier<M> mapSupplier) {
        if (ObjectUtil.isEmpty(list)) {
            return mapSupplier.get();
        }

        return list.stream().filter(Objects::nonNull).collect(Collectors.toMap(keyMapper, valueMapper, (oldValue, newValue) -> newValue, mapSupplier));
    }
}