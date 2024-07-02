package cn.com.toolWeb.model.dto.base;

import cn.com.toolWeb.vaild.Save;
import cn.com.toolWeb.vaild.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 环境表入参对象
 *
 * @author hyj
 * @since 2021年07月13日
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "EnvironmentDTO对象", description = "环境表入参对象")
public class EnvironmentDTO {

    /** ID */
    @ApiModelProperty(value = "ID")
    private Long id;

    /** 编码 */
    @NotEmpty(message = "编码不能为空！", groups = {Save.class, Update.class})
    @ApiModelProperty(value = "编码")
    private String code;

    /** 名称 */
    @NotEmpty(message = "名称不能为空！", groups = {Save.class, Update.class})
    @ApiModelProperty(value = "名称")
    private String name;

    /** 访问地址 */
    @NotEmpty(message = "访问地址不能为空！", groups = {Save.class, Update.class})
    @ApiModelProperty(value = "访问地址")
    private String url;

    /** 端口号 */
    @NotEmpty(message = "端口号不能为空！", groups = {Save.class, Update.class})
    @ApiModelProperty(value = "端口号")
    private String port;

    /** 用户名 */
    @NotEmpty(message = "用户名不能为空！", groups = {Save.class, Update.class})
    @ApiModelProperty(value = "用户名")
    private String userName;

    /** 密码 */
    @NotEmpty(message = "密码不能为空！", groups = {Save.class, Update.class})
    @ApiModelProperty(value = "密码")
    private String password;

    /** 数据库类型（1：mysql 2：pgsql） */
    @ApiModelProperty(value = "数据库类型（1：mysql 2：pgsql）")
    private Integer dbType;
}