package cn.com.toolWeb.model.dto.action;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * 字典项对象
 *
 * @author hyj
 * @since 2021年04月25日
 */
@Data
@ApiModel(value = "DictDTO对象", description = "字典项对象")
public class DictDTO {
    /** 字典项类型编码 */
    @ApiModelProperty(value = "字典项类型编码")
    List<String> dictTypeList;
}