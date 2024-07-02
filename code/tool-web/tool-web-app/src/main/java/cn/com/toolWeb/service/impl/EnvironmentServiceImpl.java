package cn.com.toolWeb.service.impl;

import cn.com.toolWeb.mapper.EnvironmentMapper;
import cn.com.toolWeb.model.base.PageParamsVo;
import cn.com.toolWeb.model.base.PageResult;
import cn.com.toolWeb.model.dto.base.EnvironmentDTO;
import cn.com.toolWeb.model.dto.query.EnvironmentPageQueryDTO;
import cn.com.toolWeb.model.entity.Environment;
import cn.com.toolWeb.model.vo.base.EnvironmentVO;
import cn.com.toolWeb.service.IEnvironmentService;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.util.List;

/**
 * 环境表服务
 *
 * @author hyj
 * @since 2021年07月13日
 */
@Slf4j
@Service
public class EnvironmentServiceImpl
        extends ToolWebBaseServiceImpl<EnvironmentMapper, Environment> implements IEnvironmentService {

    @Override
    public List<EnvironmentVO> listVo(EnvironmentDTO environmentDTO) {
        return listVo(new LambdaQueryWrapper<>());
    }

    @Override
    public List<EnvironmentVO> listVo(Wrapper<Environment> queryWrapper) {
        List<EnvironmentVO> environmentVOList = list(queryWrapper, EnvironmentVO.class);
        return environmentVOList;
    }

    @Override
    public PageResult<EnvironmentVO> pageVo(EnvironmentPageQueryDTO environmentPageQueryDTO) {
        PageParamsVo queryParams = new PageParamsVo(environmentPageQueryDTO);
        return pageVo(queryParams, new LambdaQueryWrapper<>());
    }

    @Override
    public PageResult<EnvironmentVO> pageVo(PageParamsVo queryParams, Wrapper<Environment> queryWrapper) {
        PageResult<EnvironmentVO> result = pageVo(queryParams, queryWrapper, EnvironmentVO.class);
        for (EnvironmentVO environmentVO : result.getData()) {
            environmentVO.setPassword(new String(Base64Utils.decodeFromString(environmentVO.getPassword())));
        }
        return result;
    }

    @Override
    public EnvironmentVO getVoById(Long id) {
        EnvironmentVO environmentVO = getById(id, EnvironmentVO.class);
        environmentVO.setPassword(new String(Base64Utils.decodeFromString(environmentVO.getPassword())));
        return environmentVO;
    }

    @Override
    public Boolean save(EnvironmentDTO environmentDTO) {
        Environment environment = BeanUtil.copyProperties(environmentDTO, Environment.class);
        environment.setPassword(Base64Utils.encodeToString(environment.getPassword().getBytes()));
        return save(environment);
    }

    @Override
    public Boolean updateById(EnvironmentDTO environmentDTO) {
        Environment environment = BeanUtil.copyProperties(environmentDTO, Environment.class);
        environment.setPassword(Base64Utils.encodeToString(environment.getPassword().getBytes()));
        return updateById(environment);
    }
}