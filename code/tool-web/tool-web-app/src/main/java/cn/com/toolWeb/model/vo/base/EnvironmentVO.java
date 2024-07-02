package cn.com.toolWeb.model.vo.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 环境表出参对象
 *
 * @author hyj
 * @since 2021年07月13日
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "EnvironmentVO对象", description = "环境表出参对象")
public class EnvironmentVO {

    /** ID */
    @ApiModelProperty(value = "ID")
    private Long id;

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