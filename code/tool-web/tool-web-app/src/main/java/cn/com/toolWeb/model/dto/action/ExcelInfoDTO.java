package cn.com.toolWeb.model.dto.action;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * excel 入参信息
 *
 * @author jack.huang
 */
@Data
@ApiModel(value = "ExcelInfoDTO对象", description = "excel 入参信息")
public class ExcelInfoDTO {
    /** excel 名称 */
    @ApiModelProperty(value = "excel 名称")
    String name;

    /** 文件路径 */
    @ApiModelProperty(value = "文件路径")
    String filePath;

    /** sheet页名称 */
    @ApiModelProperty(value = "sheet页名称")
    List<String> sheetNameList;
}
