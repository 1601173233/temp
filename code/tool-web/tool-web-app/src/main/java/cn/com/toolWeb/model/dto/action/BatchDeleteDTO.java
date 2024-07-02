package cn.com.codeBuilder.model.dto.action;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 批量删除的对象
 *
 * @author hyj
 * @since 2021年04月25日
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "BatchDeleteDTO对象", description = "批量删除参数对象")
public class BatchDeleteDTO {
    /** id列表 */
    @NotNull(message = "id列表不能为空")
    @ApiModelProperty(value = "id列表")
    List<Long> idList;
}