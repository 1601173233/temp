package cn.com.toolWeb.model.dto.action;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * 文件合并操作对象
 *
 * @author hyj
 * @since 2021年04月25日
 */
@Data
@ApiModel(value = "ExcelCombineDTO对象", description = "文件合并操作对象")
public class ExcelCombineDTO {
    /** excel列表 */
    @NotNull(message = "excel列表不能为空")
    @ApiModelProperty(value = "excel列表")
    List<ExcelInfoDTO> excelInfoList;

    /** 合并类型：（1：sheet 不合并， 2：合并成一个sheet， 3：同名sheet合并， 4：同名sheet忽略） */
    @ApiModelProperty(value = "合并类型：（1：sheet 不合并， 2：合并成一个sheet， 3：同名sheet合并， 4：同名sheet忽略）")
    private Integer isCombineType;

    /** 开始合并的行数 */
    @ApiModelProperty(value = "开始合并的行数")
    private Integer startLine;

    /** 是否第一个sheet 把所有行都拷贝进去 */
    @ApiModelProperty(value = "是否第一个sheet 把所有行都拷贝进去")
    private Boolean isFirstAllLine;

    /** sheet 去重类型（1：不校验重复，2：完全匹配算重复，3：选择匹配重复） */
    @ApiModelProperty(value = "sheet 去重类型（1：不校验重复，2：完全匹配算重复，3：选择匹配重复）")
    private Integer sheetDuplicateRemovalType;

    /** 是否展示来源表格 */
    @ApiModelProperty(value = "是否展示来源表格")
    private Integer showSourceType;

    /** 去重范围 */
    @ApiModelProperty(value = "sheet 去重类型（1：不校验重复，2：完全匹配算重复，3：选择匹配重复）")
    List<ExcelSheetRangeDTO> sheetDuplicateRemovalRange;

    /** 去重范围位置 */
    Set<Integer> sheetDuplicateRemovalRangeIndex;
}