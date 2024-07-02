package cn.com.toolWeb.model.dto.action;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * excel 表格区域对象
 *
 * @author hyj
 * @since 2021年04月25日
 */
@Data
@ApiModel(value = "ExcelSheetRangeDTO对象", description = "excel 表格区域对象")
public class ExcelSheetRangeDTO {
    /** 开始区域 */
    @ApiModelProperty(value = "开始区域")
    private String start;

    /** 结束区域 */
    @ApiModelProperty(value = "结束区域")
    private String end;

}