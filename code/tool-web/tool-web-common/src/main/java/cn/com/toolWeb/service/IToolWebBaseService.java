package cn.com.toolWeb.service;

import cn.com.toolWeb.model.base.PageParamsVo;
import cn.com.toolWeb.model.base.PageResult;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.List;

/**
 * 基础服务类
 *
 * @author jack.huang
 * @since 2021-03-03
 */
public interface IToolWebBaseService<T> extends IService<T> {
    /**
     * 获取单个对象
     * @param t 参数
     * @return 单个对象数据
     */
    T getOne(T t);

    /**
     * 获取单个对象
     *
     * @param t 参数
     * @param c 返回类型
     * @param <VO> 返回类型
     * @return 单个对象数据
     */
    <VO> VO getOne(T t, Class<VO> c);

    /**
     * 根据ID获取单个对象
     *
     * @param id 参数
     * @param c 返回类型
     * @param <VO> 返回类型
     * @return 单个对象数据
     */
    <VO> VO getById(Serializable id, Class<VO> c);

    /**
     * 获取列表数据
     * @param t 参数
     * @return 列表数据
     */
    List<T> list(T t);

    /**
     * 获取列表数据
     * @param t 参数
     * @param c 返回类型
     * @param <VO> 返回类型
     * @return 列表数据
     */
    <VO> List<VO> list(T t, Class<VO> c);

    /**
     * 获取列表数据
     * @param t 参数
     * @param c 返回类型
     * @param <VO> 返回类型
     * @return 列表数据
     */
    <VO> List<VO> list(Wrapper<T> t, Class<VO> c);

    /**
     * 获取列表数据
     *
     * @param queryParams 分页参数
     * @param t 参数
     * @param c 返回类型
     * @param <VO> 返回类型
     * @return 列表数据
     */
    <VO> PageResult<VO> pageVo(PageParamsVo queryParams, Wrapper<T> t, Class<VO> c);
}
