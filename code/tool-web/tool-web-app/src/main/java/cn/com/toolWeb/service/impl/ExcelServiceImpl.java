package cn.com.toolWeb.service.impl;

import cn.com.toolWeb.enums.ExcelCombineTypeEnum;
import cn.com.toolWeb.enums.ExcelSheetDuplicateRemovalTypeEnum;
import cn.com.toolWeb.enums.ExcelShowSourceTypeEnum;
import cn.com.toolWeb.error.ExceptionUtil;
import cn.com.toolWeb.model.dto.action.ExcelCombineDTO;
import cn.com.toolWeb.model.dto.action.ExcelInfoDTO;
import cn.com.toolWeb.model.dto.action.ExcelSheetRangeDTO;
import cn.com.toolWeb.model.vo.action.ExcelInfoVo;
import cn.com.toolWeb.model.vo.action.FileInfoVo;
import cn.com.toolWeb.service.IExcelService;
import cn.com.toolWeb.util.FileUtils;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.WorkbookUtil;
import cn.hutool.poi.excel.cell.CellUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * excel 相关服务功能
 *
 * @author jack.huang
 */
@Component
public class ExcelServiceImpl implements IExcelService {

    @Override
    public List<ExcelInfoVo> getExcelInfo(List<FileInfoVo> fileInfoVoList) {
        if (ObjectUtil.isEmpty(fileInfoVoList)) {
            return new ArrayList<>();
        }

        List<FileInfoVo> mFileInfoVoList = new ArrayList<>();
        for (FileInfoVo fileInfoVo : fileInfoVoList) {
            File file = fileInfoVo.getFile();

            // 只处理一层文件夹
            if (file.isDirectory()) {
                for (File listFile : file.listFiles()) {
                    if (listFile.isDirectory()) {
                        mFileInfoVoList.add(fileInfoVo);
                    }
                }
            } else {
                mFileInfoVoList.add(fileInfoVo);
            }
        }

        mFileInfoVoList.removeIf(file -> !(file.getName().endsWith(".xls") || file.getName().endsWith(".xlsx")));

        List<ExcelInfoVo> excelInfoVoList = new ArrayList<>();
        for (FileInfoVo fileInfoVo : mFileInfoVoList) {
            ExcelInfoVo excelInfoVo = new ExcelInfoVo();
            excelInfoVo.setName(fileInfoVo.getName());
            excelInfoVo.setFilePath(fileInfoVo.getFilePath());
            excelInfoVo.setMd5(fileInfoVo.getMd5());

            ExcelReader reader = ExcelUtil.getReader(fileInfoVo.getFile());
            excelInfoVo.setSheetNameList(reader.getSheetNames());
            excelInfoVoList.add(excelInfoVo);
            reader.close();
        }
        return excelInfoVoList;
    }

    @Override
    public void combineExcel(ExcelCombineDTO excelCombineDTO, HttpServletResponse httpServletResponse) throws IOException {
        // 1. 参数初始化
        initParams(excelCombineDTO);

        // 2.没有sheetName，就清理掉
        List<ExcelInfoDTO> excelInfoList = excelCombineDTO.getExcelInfoList();
        excelInfoList.removeIf(excelInfoDTO -> StringUtils.isEmpty(excelInfoDTO.getFilePath()) || ObjectUtil.isEmpty(excelInfoDTO.getSheetNameList()));

        // 3.创建单元格
        Workbook workbook = WorkbookUtil.createBook(true);
        combineExcel(excelCombineDTO, workbook);

        httpServletResponse.setHeader("Content-disposition", "attachment;filename=combineExcel.xlsx");
        workbook.write(httpServletResponse.getOutputStream());
    }

    /**
     * excel 合并
     *
     * @param excelCombineDTO excel参数
     * @param workbook 输出文件
     */
    private void combineExcel(ExcelCombineDTO excelCombineDTO, Workbook workbook) {
        ExcelCombineTypeEnum excelCombineTypeEnum = ExcelCombineTypeEnum.getByCode(excelCombineDTO.getIsCombineType());
        if (excelCombineTypeEnum == null) {
            ExceptionUtil.illegalParameter("合并类型不支持！");
        }

        switch (excelCombineTypeEnum) {
            case NOT_COMBINE:
                combineExcelNotCombine(excelCombineDTO, workbook);
                break;
            case ONE:
                combineExcelOne(excelCombineDTO, workbook);
                break;
            case IGNORE:
                combineExcelIgnore(excelCombineDTO, workbook);
                break;
            case SAME_NAME:
                combineExcelSameName(excelCombineDTO, workbook);
                break;
            default:
                ExceptionUtil.illegalParameter("合并类型不支持！");
        }
    }

    /**
     * excel 合并（不合并sheet）
     *
     * @param excelCombineDTO excel参数
     * @param workbook 输出文件
     */
    private void combineExcelNotCombine(ExcelCombineDTO excelCombineDTO, Workbook workbook) {
        Set<String> sheetNameSet = new HashSet<>();
        for (ExcelInfoDTO excelInfoDto : excelCombineDTO.getExcelInfoList()) {
            File tmpFile = FileUtils.getTmpFile(excelInfoDto.getFilePath());

            // 同名sheet 进行重命名操作
            String regex = "\\(\\d{1,}\\)$";
            Pattern pattern = Pattern.compile(regex);
            for (String sheetName : excelInfoDto.getSheetNameList()) {
                ExcelReader reader = ExcelUtil.getReader(tmpFile, sheetName);

                while (sheetNameSet.contains(sheetName)) {
                    Matcher m = pattern.matcher(sheetName);
                    if (m.find()) {
                        String index = m.group(0).replace("(", "").replace(")", "");
                        index = "(" + (Integer.parseInt(index) + 1) + ")";
                        sheetName = sheetName.substring(0, sheetName.length() - m.group(0).length()) + index;
                    } else {
                        sheetName += "(1)";
                    }
                }

                sheetNameSet.add(sheetName);

                // 写出数据
                workbook.createSheet(sheetName);
                ExcelWriter excelWriter = new ExcelWriter(workbook.getSheet(sheetName));
                copySheet(reader, excelWriter);
                reader.close();
            }
        }
    }

    /**
     * excel 合并（合并成一个sheet）
     *
     * @param excelCombineDTO excel参数
     * @param workbook 输出文件
     */
    private void combineExcelOne(ExcelCombineDTO excelCombineDTO, Workbook workbook) {
        String name = excelCombineDTO.getExcelInfoList().get(0).getSheetNameList().get(0);

        for (ExcelInfoDTO excelInfoDto : excelCombineDTO.getExcelInfoList()) {
            for (String sheetName : excelInfoDto.getSheetNameList()) {
                if (name == null) {
                    name = sheetName;
                }
            }
        }

        // 写出数据
        workbook.createSheet(name);
        ExcelWriter excelWriter = new ExcelWriter(workbook.getSheet(name));
        combineSheet(excelWriter, excelCombineDTO, excelCombineDTO.getExcelInfoList());
    }

    /**
     * excel 合并（同名sheet合并）
     *
     * @param excelCombineDTO excel参数
     * @param workbook 输出文件
     */
    private void combineExcelSameName(ExcelCombineDTO excelCombineDTO, Workbook workbook) {
        List<ExcelInfoDTO> excelInfoList = excelCombineDTO.getExcelInfoList();
        HashMap<String, List<ExcelInfoDTO>> map = new LinkedHashMap<>(excelInfoList.size());

        for (ExcelInfoDTO excelInfoDto : excelInfoList) {
            // 同名sheet 合并
            for (String sheetName : excelInfoDto.getSheetNameList()) {
                map.computeIfAbsent(sheetName, t -> new ArrayList<>()).add(excelInfoDto);
            }
        }

        map.forEach((sheetName, excelInfoDtoList) -> {
            // 正常来说不应该修改数据集
            for (ExcelInfoDTO excelInfoDto : excelInfoDtoList) {
                excelInfoDto.setSheetNameList(new ArrayList<>());
                excelInfoDto.getSheetNameList().add(sheetName);
            }

            // 写出数据
            workbook.createSheet(sheetName);
            ExcelWriter excelWriter = new ExcelWriter(workbook.getSheet(sheetName));
            combineSheet(excelWriter, excelCombineDTO, excelInfoDtoList);
        });
    }

    /**
     * excel 合并（同名sheet忽略）
     *
     * @param excelCombineDTO excel参数
     * @param workbook 输出文件
     */
    private void combineExcelIgnore(ExcelCombineDTO excelCombineDTO,
                                    Workbook workbook) {
        Set<String> sheetNameSet = new HashSet<>();
        for (ExcelInfoDTO excelInfoDto : excelCombineDTO.getExcelInfoList()) {
            File tmpFile = FileUtils.getTmpFile(excelInfoDto.getFilePath());

            // 同名sheet忽略
            for (String sheetName : excelInfoDto.getSheetNameList()) {
                ExcelReader reader = ExcelUtil.getReader(tmpFile, sheetName);
                if (sheetNameSet.contains(sheetName)) {
                    continue;
                }

                sheetNameSet.add(sheetName);

                // 写出数据
                workbook.createSheet(sheetName);
                ExcelWriter excelWriter = new ExcelWriter(workbook.getSheet(sheetName));
                copySheet(reader, excelWriter);
                reader.close();
            }
        }
    }

    /**
     * 获取数据合并的结果
     *
     * @param writer 写对象
     * @param excelCombineDTO 参数
     * @param excelInfoList 待合并的文件信息
     * @return
     */
    private void combineSheet(ExcelWriter writer,
                              ExcelCombineDTO excelCombineDTO,
                              List<ExcelInfoDTO> excelInfoList) {
        // 如果只有一个表格，直接输出
        if (excelInfoList.size() == 1 && excelInfoList.get(0).getSheetNameList().size() == 1) {
            ExcelReader reader = ExcelUtil.getReader(FileUtils.getTmpFile(excelInfoList.get(0).getFilePath()), excelInfoList.get(0).getSheetNameList().get(0));
            copySheet(reader, writer);
            reader.close();
            return;
        }

        boolean isFirst = true;
        int startLine = excelCombineDTO.getStartLine();

        Set<String> checkSet = new HashSet<>();
        ExcelSheetDuplicateRemovalTypeEnum duplicateRemovalTypeEnum = ExcelSheetDuplicateRemovalTypeEnum.getByCode(excelCombineDTO.getSheetDuplicateRemovalType());
        if (duplicateRemovalTypeEnum == null) {
            ExceptionUtil.illegalParameter("异常去重类型");
        }

        ExcelShowSourceTypeEnum showSourceTypeEnum = ExcelShowSourceTypeEnum.getByCode(excelCombineDTO.getShowSourceType());
        if (showSourceTypeEnum == null) {
            ExceptionUtil.illegalParameter("异常显示来源类型");
        }

        int rowNum = 0;
        for (ExcelInfoDTO excelInfo : excelInfoList) {
            for (String sheetName : excelInfo.getSheetNameList()) {
                // 1. 判断开始的下标
                int line = startLine;
                if (startLine > 1) {
                    // 如果首个表格保留标题
                    if (isFirst && BooleanUtil.isTrue(excelCombineDTO.getIsFirstAllLine())) {
                        line = 1;
                    }
                }

                line = line - 1;

                // 2. 数据拉取
                ExcelReader reader = ExcelUtil.getReader(FileUtils.getTmpFile(excelInfo.getFilePath()), sheetName);

                // 3. 动态调整宽度
                copyWidth(reader.getSheet(), writer.getSheet(), isFirst);

                // 4. 数据转换
                List<List<Object>> mDataLists = reader.read(line);
                mDataLists.removeIf(mDataList -> checkIsDuplicate(excelCombineDTO, checkSet, duplicateRemovalTypeEnum, mDataList));
                if (ObjectUtil.isEmpty(mDataLists)) {
                    continue;
                }

                // 5. 展示数据来源
                int columnStart = 0;
                if (showSourceTypeEnum != ExcelShowSourceTypeEnum.NO
                    && (showSourceTypeEnum != ExcelShowSourceTypeEnum.FILE_NAME || excelInfoList.size() > 1)) {
                    columnStart = 1;

                    for (List<Object> mDataList : mDataLists) {
                        String sourceName = getSourceName(showSourceTypeEnum, excelInfoList, excelInfo, sheetName);
                        if (!StringUtils.isEmpty(sourceName)) {
                            mDataList.add(0, sourceName);
                        }
                    }
                }

                // 6. 写数据结果
                writer.write(mDataLists);

                // 7. 样式拷贝
                for (int r = 0; r < mDataLists.size(); r++) {
                    for (int c = 0; c < mDataLists.get(r).size(); c++) {
                        Cell cell = reader.getCell(c, r + line);
                        if (cell != null && cell.getCellStyle() != null) {
                            writer.createCellStyle(c + columnStart, r + rowNum);
                            writer.getOrCreateCellStyle(c + columnStart, r + rowNum).cloneStyleFrom(cell.getCellStyle());
                        }
                    }
                }

                isFirst = false;
                rowNum += mDataLists.size();
                reader.close();
            }
        }
    }

    /**
     * 参数初始化
     *
     * @param excelCombineDTO 参数
     */
    private void initParams(ExcelCombineDTO excelCombineDTO) {
        excelCombineDTO.setIsCombineType(ObjectUtil.defaultIfNull(excelCombineDTO.getIsCombineType(), ExcelCombineTypeEnum.NOT_COMBINE.getCode()));
        excelCombineDTO.setIsFirstAllLine(ObjectUtil.defaultIfNull(excelCombineDTO.getIsFirstAllLine(), false));
        excelCombineDTO.setStartLine(ObjectUtil.defaultIfNull(excelCombineDTO.getStartLine(), 1));
        excelCombineDTO.setSheetDuplicateRemovalType(ObjectUtil.defaultIfNull(excelCombineDTO.getSheetDuplicateRemovalType(), ExcelSheetDuplicateRemovalTypeEnum.NOT_CHECK.getCode()));
        excelCombineDTO.setShowSourceType(ObjectUtil.defaultIfNull(excelCombineDTO.getShowSourceType(), ExcelShowSourceTypeEnum.NO.getCode()));

        if (ExcelSheetDuplicateRemovalTypeEnum.RANGE.getCode().equals(excelCombineDTO.getSheetDuplicateRemovalType())) {
            if (ObjectUtil.isNotEmpty(excelCombineDTO.getSheetDuplicateRemovalRange())) {
                Set<Integer> set = new HashSet<>();
                excelCombineDTO.setSheetDuplicateRemovalRangeIndex(set);

                for (ExcelSheetRangeDTO excelSheetRangeDTO : excelCombineDTO.getSheetDuplicateRemovalRange()) {
                    int start = excelSheetRangeDTO.getStart().toCharArray()[0];
                    int end = excelSheetRangeDTO.getEnd().toCharArray()[0];

                    if (start > end || start > 'Z' || end < 'A') {
                        ExceptionUtil.illegalParameter("范围异常，只能输入[A] - [Z]");
                    }

                    start = Math.max('A', start) - 'A';
                    end = Math.min('Z', end) - 'A';
                    for (int c = start; c <= end; c++) {
                        set.add(c);
                    }
                }
            }

            if (ObjectUtil.isEmpty(excelCombineDTO.getSheetDuplicateRemovalRangeIndex())) {
                excelCombineDTO.setSheetDuplicateRemovalType(ExcelSheetDuplicateRemovalTypeEnum.NOT_CHECK.getCode());
            }
        }
    }

    /**
     * sheet 复制
     *
     * @param reader 源表格
     * @param writer 目标表格
     */
    private void copySheet(ExcelReader reader,
                          ExcelWriter writer) {
        Sheet sourceSheet = reader.getSheet();
        Sheet targetSheet = writer.getSheet();

        // 1.复制合并单元格
        for (CellRangeAddress mergedRegion : sourceSheet.getMergedRegions()) {
            CellUtil.mergingCells(targetSheet, mergedRegion.getFirstRow(), mergedRegion.getLastRow(),
                    mergedRegion.getFirstColumn(), mergedRegion.getLastColumn(),
                    reader.getOrCreateCellStyle(mergedRegion.getFirstColumn(), mergedRegion.getFirstRow()));
        }

        // 2.复制单元格的信息
        int rowCount = reader.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            Row row = sourceSheet.getRow(i);
            if (row == null) {
                continue;
            }

            Row row1 = targetSheet.createRow(i);
            if (row.getRowStyle() != null) {
                row1.getRowStyle().cloneStyleFrom(row.getRowStyle());
            }

            for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j);

                if (cell != null) {
                    Cell cell1 = writer.getOrCreateCell(j, i);
                    writer.createCellStyle(j, i);
                    copyCellValue(cell, cell1);
                }
            }
        }

        // 3.设置列宽
        copyWidth(sourceSheet, targetSheet);
    }

    /**
     * 复制单元格的信息
     */
    private void copyCellValue(Cell fromCell, Cell targetCell) {
        if (null == fromCell) {
            return;
        }

        CellType cellType = fromCell.getCellType();
        switch (cellType) {
            case NUMERIC:
                targetCell.setCellValue(fromCell.getNumericCellValue());
                break;
            case BOOLEAN:
                targetCell.setCellValue(fromCell.getBooleanCellValue());
                break;
            case FORMULA:
                targetCell.setCellFormula(fromCell.getCellFormula());
                break;
            case BLANK:
                targetCell.setBlank();
                break;
            case ERROR:
                targetCell.setCellType(cellType);
                targetCell.setCellValue(fromCell.getErrorCellValue());
                break;
            default:
                targetCell.setCellValue(fromCell.getStringCellValue());
        }

        targetCell.getCellStyle().cloneStyleFrom(fromCell.getCellStyle());
    }

    /**
     * 拷贝列宽
     *
     * @param sourceSheet 源表格
     * @param targetSheet 目标表格
     */
    private void copyWidth(Sheet sourceSheet, Sheet targetSheet) {
        copyWidth(sourceSheet, targetSheet, true);
    }

    /**
     * 拷贝列宽
     *
     * @param sourceSheet 源表格
     * @param targetSheet 目标表格
     */
    private void copyWidth(Sheet sourceSheet, Sheet targetSheet, boolean isFirst) {
        for (int i = sourceSheet.getFirstRowNum(); i < sourceSheet.getLastRowNum(); i++) {
            Row row = sourceSheet.getRow(i);
            if (row != null) {
                for (int j = row.getLastCellNum(); j >= row.getFirstCellNum(); j--) {
                    int width = sourceSheet.getColumnWidth((short) j);

                    if (isFirst || targetSheet.getColumnWidth(j) < width) {
                        targetSheet.setColumnWidth((short) j, (short) width);
                    }

                    if (width == 0) {
                        targetSheet.setColumnHidden((short) j, true);
                    } else {
                        targetSheet.setColumnHidden((short) j, false);
                    }
                }
                break;
            }
        }
    }

    /**
     * 校验数据是否重复
     *
     * @param excelCombineDTO 参数
     * @param checkSet 已经添加的数据列表
     * @param duplicateRemovalTypeEnum 校验类型
     * @param mDataList 当前数据列表
     * @return 是否重复
     */
    private boolean checkIsDuplicate(ExcelCombineDTO excelCombineDTO,
                                     Set<String> checkSet,
                                     ExcelSheetDuplicateRemovalTypeEnum duplicateRemovalTypeEnum,
                                     List<Object> mDataList) {
        boolean isDuplicate = false;

        switch (duplicateRemovalTypeEnum) {
            case NOT_CHECK:
                isDuplicate = false;
                break;
            case ALL_SUIT:
            case RANGE:
                Boolean hasValue = false;
                StringBuilder builder = new StringBuilder();

                for (int index = 0; index < mDataList.size(); index++) {
                    // 范围控制
                    if (duplicateRemovalTypeEnum == ExcelSheetDuplicateRemovalTypeEnum.RANGE
                            && !excelCombineDTO.getSheetDuplicateRemovalRangeIndex().contains(index)) {
                        continue;
                    }

                    hasValue = true;
                    builder.append(String.valueOf(mDataList.get(index))).append("@@@");
                }

                if (!hasValue) {
                    break;
                }

                String str = builder.toString();
                if (checkSet.contains(str)) {
                    isDuplicate = true;
                } else {
                    checkSet.add(str);
                }
                break;
            default:
                ExceptionUtil.illegalParameter("去重类型未定义");
        }
        return isDuplicate;
    }

    /**
     * 获取来源信息
     *
     * @param showSourceTypeEnum 显示类型
     * @param excelInfoList 表记录
     * @param excelInfo 当前文件信息
     * @param sheetName 当前表名
     * @return 来源信息
     */
    private String getSourceName(ExcelShowSourceTypeEnum showSourceTypeEnum,
                                 List<ExcelInfoDTO> excelInfoList,
                                 ExcelInfoDTO excelInfo,
                                 String sheetName) {

        String sourceName = "";
        switch (showSourceTypeEnum) {
            case NO:
                break;
            case FILE_NAME:
                if (excelInfoList.size() > 1) {
                    sourceName = excelInfo.getName();
                }
                break;
            case SHEET_NAME:
                sourceName = sheetName;
                break;
            case FILE_NAME_AND_SHEET_NAME:
                if (excelInfoList.size() > 1) {
                    sourceName = "【文件】：" + excelInfo.getName() + ", ";
                }
                sourceName += "【表格】:" + sheetName;
                break;
            default:
        }
        return sourceName;
    }
}
