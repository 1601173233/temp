package cn.com.toolWeb.model.dto.action;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 文件路径的对象
 *
 * @author hyj
 * @since 2021年04月25日
 */
@Data
@ApiModel(value = "FilePathDTO对象", description = "文件路径的对象")
public class FilePathDTO {
    /** 文件路径 */
    @NotNull(message = "文件路径列表不能为空")
    @ApiModelProperty(value = "文件路径")
    List<String> filePathList;
}