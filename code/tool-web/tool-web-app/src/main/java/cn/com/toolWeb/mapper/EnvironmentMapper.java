package cn.com.toolWeb.mapper;

import cn.com.toolWeb.model.entity.Environment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 环境表持久层接口
 *
 * @author hyj
 * @since 2021年07月13日
 */
@Mapper
public interface EnvironmentMapper extends BaseMapper<Environment> {

}