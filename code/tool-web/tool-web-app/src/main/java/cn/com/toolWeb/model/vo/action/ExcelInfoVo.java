package cn.com.toolWeb.model.vo.action;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * excel 的相关信息
 *
 * @author jack.huang
 */
@Data
@ApiModel(value = "ExcelInfoVo对象", description = "excel 的相关信息对象")
public class ExcelInfoVo {
    /** excel 名称 */
    @ApiModelProperty(value = "excel 名称")
    String name;

    /** 文件路径 */
    @ApiModelProperty(value = "文件路径")
    String filePath;

    /** sheet页名称 */
    @ApiModelProperty(value = "sheet页名称")
    List<String> sheetNameList;

    /** 验证码 */
    @ApiModelProperty(value = "验证码")
    String md5;
}
