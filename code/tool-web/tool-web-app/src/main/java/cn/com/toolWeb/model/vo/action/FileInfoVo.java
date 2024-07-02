package cn.com.toolWeb.model.vo.action;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.File;
import java.util.List;

/**
 * 文件相关信息
 *
 * @author jack.huang
 */
@Data
@ApiModel(value = "FileInfoVo对象", description = "文件相关信息")
public class FileInfoVo {
    /** 名称 */
    @ApiModelProperty(value = "名称")
    String name;

    /** 文件路径 */
    @ApiModelProperty(value = "文件路径")
    String filePath;

    /** 路径 */
    @ApiModelProperty(value = "路径")
    String md5;

    /** 文件对象 */
    @ApiModelProperty(value = "文件对象")
    File file;
}
