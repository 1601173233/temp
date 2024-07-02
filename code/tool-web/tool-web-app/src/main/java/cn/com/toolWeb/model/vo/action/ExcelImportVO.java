package cn.com.codeBuilder.model.vo.action;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * 导入返回数据对象
 *
 * @author hyj
 * @since 2021年05月06日
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "ExcelImportVO对象", description = "导入返回数据对象")
public class ExcelImportVO {
    /** 导入成功数量 */
    @ApiModelProperty(value = "导入成功数量")
    private Integer successCount;

    /** 导入失败数量 */
    @ApiModelProperty(value = "导入失败数量")
    private Integer failCount;

    /** 导入异常信息 */
    @ApiModelProperty(value = "导入异常信息")
    private List<String> errorMsgList;

    /**
     * 构造方法
     */
    public ExcelImportVO() {
        errorMsgList = new ArrayList<>();
        successCount = 0;
        failCount = 0;
    }

    /**
     * 添加异常信息
     * @param errorMsg 异常信息
     */
    public void addFailMsg(String errorMsg) {
        errorMsgList.add(errorMsg);
        failCount++;
    }
}