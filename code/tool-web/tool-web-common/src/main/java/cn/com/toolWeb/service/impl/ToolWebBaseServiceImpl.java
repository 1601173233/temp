package cn.com.toolWeb.service.impl;

import cn.com.toolWeb.model.base.PageParamsVo;
import cn.com.toolWeb.model.base.PageResult;
import cn.com.toolWeb.service.IToolWebBaseService;
import cn.com.toolWeb.util.BeanUtils;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.Serializable;
import java.util.List;

/**
 * 基础服务类
 *
 * @author jack.huang
 * @since 2021-03-03
 */
public class ToolWebBaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements IToolWebBaseService<T> {

    @Override
    public T getOne(T t) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>(t);
        return super.getOne(queryWrapper);
    }

    @Override
    public <VO> VO getOne(T t, Class<VO> c) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>(t);
        return BeanUtil.copyProperties(super.getOne(queryWrapper), c);
    }

    @Override
    public <VO> VO getById(Serializable id, Class<VO> c) {
        return BeanUtil.copyProperties(super.getById(id), c);
    }

    @Override
    public List<T> list(T t) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>(t);
        return list(queryWrapper);
    }

    @Override
    public <VO> List<VO> list(T t, Class<VO> c) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>(t);
        return list(queryWrapper, c);
    }

    @Override
    public <VO> List<VO> list(Wrapper<T> t, Class<VO> c) {
        return BeanUtils.convertList(list(t), c);
    }

    @Override
    public <VO> PageResult<VO> pageVo(PageParamsVo queryParams, Wrapper<T> t, Class<VO> c) {
        Page page = page(queryParams.getPage(), t);
        queryParams.setResultList(BeanUtils.convertList(page.getRecords(), c));
        return queryParams.getPageResult();
    }
}
