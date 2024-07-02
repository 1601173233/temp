package cn.com.toolWeb.service;


import cn.com.toolWeb.model.base.PageParamsVo;
import cn.com.toolWeb.model.base.PageResult;
import cn.com.toolWeb.model.dto.base.EnvironmentDTO;
import cn.com.toolWeb.model.dto.query.EnvironmentPageQueryDTO;
import cn.com.toolWeb.model.entity.Environment;
import cn.com.toolWeb.model.vo.base.EnvironmentVO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;

import java.util.List;

/**
 * 环境表 服务
 *
 * @author hyj
 * @since 2021年07月13日
 */
public interface IEnvironmentService extends IToolWebBaseService<Environment> {

    /**
     * 获取环境表列表
     *
     * @param environmentDTO 环境表信息
     * @return 环境表列表
     */
    List<EnvironmentVO> listVo(EnvironmentDTO environmentDTO);

    /**
     * 获取环境表列表
     *
     * @param queryWrapper 参数
     * @return 环境表列表
     */
    List<EnvironmentVO> listVo(Wrapper<Environment> queryWrapper);

    /**
     * 获取环境表分页列表
     *
     * @param environmentPageQueryDTO 环境表分页信息
     * @return 环境表分页列表
     */
    PageResult<EnvironmentVO> pageVo(EnvironmentPageQueryDTO environmentPageQueryDTO);

    /**
     * 获取环境表列表
     *
     * @param queryParams 分页信息
     * @param queryWrapper 参数
     * @return 环境表列表
     */
    PageResult<EnvironmentVO> pageVo(PageParamsVo queryParams, Wrapper<Environment> queryWrapper);

    /**
     * 根据ID获取环境表
     *
     * @param id ID
     * @return 环境表
     */
    EnvironmentVO getVoById(Long id);

    /**
     * 保存环境表信息
     *
     * @param environmentDTO 环境表信息
     * @return 保存成功
     */
    Boolean save(EnvironmentDTO environmentDTO);

    /**
     * 更新环境表信息
     *
     * @param environmentDTO 环境表信息
     * @return 更新成功
     */
    Boolean updateById(EnvironmentDTO environmentDTO);
}