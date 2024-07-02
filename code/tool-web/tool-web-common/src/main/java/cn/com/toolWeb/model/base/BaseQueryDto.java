package cn.com.toolWeb.model.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 分页参数.
 *
 * @author hyj
 * @since  2020-05-22 14:55:05
 */
@Data
public class BaseQueryDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 当前页 */
    @ApiModelProperty("当前页")
    private Integer pageNum = 1;

    /** 每页条数 */
    @ApiModelProperty("每页条数")
    private Integer pageSize = 10;

    /** 是否分页 */
    @JsonIgnore
    private Boolean isPage = true;
}
