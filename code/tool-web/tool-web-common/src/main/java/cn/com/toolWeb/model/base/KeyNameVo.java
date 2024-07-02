package cn.com.toolWeb.model.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 返回key-value的对象.
 */
@Data
@ApiModel(value = "KeyNameVo", description = "返回key-value的对象")
public class KeyNameVo {
    /** 键. */
    @ApiModelProperty(value = "键")
    private String key;

    /** 值. */
    @ApiModelProperty(value = "值")
    private String value;
}
