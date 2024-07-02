package cn.com.toolWeb.controller;

import cn.com.codeBuilder.model.dto.action.BatchDeleteDTO;
import cn.com.toolWeb.error.ExceptionUtil;
import cn.com.toolWeb.model.dto.base.EnvironmentDTO;
import cn.com.toolWeb.model.dto.query.EnvironmentPageQueryDTO;
import cn.com.toolWeb.model.response.PageResponseMsg;
import cn.com.toolWeb.model.response.ResponseDataMsg;
import cn.com.toolWeb.model.vo.base.EnvironmentVO;
import cn.com.toolWeb.service.IEnvironmentService;
import cn.com.toolWeb.vaild.Save;
import cn.com.toolWeb.vaild.Update;
import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 环境表控制层
 *
 * @author jack.huang
 * @since 2021年07月13日
 */
@RestController
@RequestMapping(value = "/api/environment")
@Api(tags = "环境表模块接口：EnvironmentController", value = "Web - EnvironmentController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class EnvironmentController {
    /** 环境表 服务 */
    @Resource
    IEnvironmentService environmentService;

    /**
     * 获取环境表列表
     *
     * @param environmentDTO 环境表信息
     * @return 环境表列表
     */
    @GetMapping("/getList")
    @ApiOperation(value = "获取环境表列表")
    public PageResponseMsg<List<EnvironmentVO>> getList(EnvironmentDTO environmentDTO) {
        environmentDTO = ObjectUtil.defaultIfNull(environmentDTO, new EnvironmentDTO());
        return PageResponseMsg.succeed(environmentService.listVo(environmentDTO));
    }

    /**
     * 获取环境表字典列表
     *
     * @param environmentDTO 环境表信息
     * @return 环境表列表
     */
    @GetMapping("/getDictList")
    @ApiOperation(value = "获取环境表字典列表")
    public PageResponseMsg<List<EnvironmentVO>> getDictList(EnvironmentDTO environmentDTO) {
        environmentDTO = ObjectUtil.defaultIfNull(environmentDTO, new EnvironmentDTO());
        List<EnvironmentVO> environmentVOList = environmentService.listVo(environmentDTO);
        for (EnvironmentVO environmentVO : environmentVOList) {
            environmentVO.setUrl(null);
            environmentVO.setPort(null);
            environmentVO.setPassword(null);
            environmentVO.setUserName(null);
        }
        return PageResponseMsg.succeed(environmentVOList);
    }

    /**
     * 获取环境表分页列表
     *
     * @param environmentPageQueryDTO 环境表分页信息
     * @return 环境表分页列表
     */
    @GetMapping("/pageVo")
    @ApiOperation(value = "获取环境表分页列表")
    public PageResponseMsg<EnvironmentVO> pageVo(EnvironmentPageQueryDTO environmentPageQueryDTO) {
        environmentPageQueryDTO = ObjectUtil.defaultIfNull(environmentPageQueryDTO, new EnvironmentPageQueryDTO());
        return PageResponseMsg.succeed(environmentService.pageVo(environmentPageQueryDTO));
    }

    /**
     * 根据ID获取环境表
     *
     * @param id ID
     * @return 环境表
     */
    @GetMapping("/getById")
    @ApiOperation(value = "根据ID获取环境表")
    public ResponseDataMsg<EnvironmentVO> getById(Long id) {
        return ResponseDataMsg.succeed(environmentService.getVoById(id));
    }

    /**
     * 保存环境表信息
     *
     * @param environmentDTO 环境表信息
     * @return 保存成功
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存环境表信息")
    public ResponseDataMsg save(@Validated(Save.class) EnvironmentDTO environmentDTO) {
        environmentDTO = ObjectUtil.defaultIfNull(environmentDTO, new EnvironmentDTO());
        return ResponseDataMsg.succeed(environmentService.save(environmentDTO));
    }

    /**
     * 更新环境表信息
     *
     * @param environmentDTO 环境表信息
     * @return 更新成功
     */
    @PostMapping("/updateById")
    @ApiOperation(value = "更新环境表信息")
    public ResponseDataMsg updateById(@Validated(Update.class) EnvironmentDTO environmentDTO) {
        environmentDTO = ObjectUtil.defaultIfNull(environmentDTO, new EnvironmentDTO());
        return ResponseDataMsg.succeed(environmentService.updateById(environmentDTO));
    }

    /**
     * 根据ID列表删除数据
     *
     * @param batchDeleteDTO id列表
     * @return 删除成功
     */
    @PostMapping("/removeByIds")
    @ApiOperation(value = "根据ID列表删除数据")
    public ResponseDataMsg removeByIds(@RequestBody BatchDeleteDTO batchDeleteDTO) {
        if (batchDeleteDTO == null || ObjectUtil.isEmpty(batchDeleteDTO.getIdList())) {
            ExceptionUtil.illegalParameter();
        }

        return ResponseDataMsg.succeed(environmentService.removeByIds(batchDeleteDTO.getIdList()));
    }
}
