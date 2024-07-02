package cn.com.toolWeb.model.entity;

import cn.com.toolWeb.model.base.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 环境表
 *
 * @author hyj
 * @since 2021年07月13日
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "Environment对象", description = "环境表")
@TableName("code_environment")
public class Environment extends SuperEntity {

    /** 编码 */
    @ApiModelProperty(value = "编码")
    private String code;

    /** 名称 */
    @ApiModelProperty(value = "名称")
    private String name;

    /** 访问地址 */
    @ApiModelProperty(value = "访问地址")
    private String url;

    /** 端口号 */
    @ApiModelProperty(value = "端口号")
    private String port;

    /** 用户名 */
    @ApiModelProperty(value = "用户名")
    private String userName;

    /** 密码 */
    @ApiModelProperty(value = "密码")
    private String password;

    /** 数据库类型（1：mysql 2：pgsql） */
    @ApiModelProperty(value = "数据库类型（1：mysql 2：pgsql）")
    private Integer dbType;

}