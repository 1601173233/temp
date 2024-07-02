package cn.com.toolWeb.service;

import cn.com.toolWeb.model.dto.action.ExcelCombineDTO;
import cn.com.toolWeb.model.vo.action.ExcelInfoVo;
import cn.com.toolWeb.model.vo.action.FileInfoVo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * excel 相关服务功能
 *
 * @author jack.huang
 */
public interface IExcelService {

    /**
     * 根据excel 文件解析excel的相关信息
     *
     * @param fileList execl文件
     * @return excel相关信息
     */
    List<ExcelInfoVo> getExcelInfo(List<FileInfoVo> fileList);

    /**
     * excel 合并后结果
     *
     * @param excelCombineDTO 合并信息
     * @param httpServletResponse 请求结果
     */
    void combineExcel(ExcelCombineDTO excelCombineDTO,
                      HttpServletResponse httpServletResponse) throws IOException;
}
