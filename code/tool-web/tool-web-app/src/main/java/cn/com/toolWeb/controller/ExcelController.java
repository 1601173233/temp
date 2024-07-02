package cn.com.toolWeb.controller;

import cn.com.toolWeb.error.ExceptionUtil;
import cn.com.toolWeb.model.dto.action.ExcelCombineDTO;
import cn.com.toolWeb.model.response.PageResponseMsg;
import cn.com.toolWeb.model.response.ResponseMsg;
import cn.com.toolWeb.model.vo.action.ExcelInfoVo;
import cn.com.toolWeb.model.vo.action.FileInfoVo;
import cn.com.toolWeb.service.IExcelService;
import cn.com.toolWeb.util.FileUtils;
import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * excel处理控制层
 *
 * @author jack.huang
 * @since 2021年07月13日
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/excel")
@Api(tags = "excel处理控制层：ExcelController", value = "Web - ExcelController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ExcelController {
    /** excel 服务 */
    @Resource
    IExcelService excelService;

    /**
     * 获取excel文件的相关信息
     *
     * @param files 上传的文件
     * @return 表列表
     */
    @PostMapping("/getExcelInfo")
    @ApiOperation(value = "获取excel文件的相关信息")
    public PageResponseMsg<List<ExcelInfoVo>> getExcelInfo(@RequestParam("file") MultipartFile[] files) throws IOException {
        if (files == null || files.length == 0) {
            ExceptionUtil.illegalParameter();
        }

        List<FileInfoVo> fileList = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            fileList.add(FileUtils.saveFile(files[i], "excel"));
        }

        return PageResponseMsg.succeed(excelService.getExcelInfo(fileList));
    }

    /**
     * excel 信息合并
     *
     * @param excelCombineDTO excel 参数
     * @return 合并后的文件
     */
    @PostMapping("/combineExcel")
    @ApiOperation(value = "excel 信息合并")
    public ResponseMsg combineExcel(@RequestBody ExcelCombineDTO excelCombineDTO,
                                    HttpServletResponse httpServletResponse) throws IOException {
        if (excelCombineDTO == null || ObjectUtil.isEmpty(excelCombineDTO.getExcelInfoList())) {
            ExceptionUtil.illegalParameter();
        }

        excelService.combineExcel(excelCombineDTO, httpServletResponse);
        return ResponseMsg.succeed();
    }
}
