package cn.com.toolWeb.controller;

import cn.com.toolWeb.enums.IEnumDict;
import cn.com.toolWeb.error.ExceptionUtil;
import cn.com.toolWeb.model.base.KeyNameVo;
import cn.com.toolWeb.model.dto.action.DictDTO;
import cn.com.toolWeb.model.response.PageResponseMsg;
import cn.com.toolWeb.model.response.ResponseDataMsg;
import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 字典项简单 处理控制层
 *
 * @author jack.huang
 * @since 2021年07月13日
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/dict")
@Api(tags = "excel处理控制层：ExcelController", value = "Web - ExcelController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DictController {

    /** 枚举类所在的包名 */
    private final String packageName = "classpath*:cn/com/toolWeb/enums/**/*.class";

    /** 字典项缓存 */
    Map<String, List<KeyNameVo>> cache = new HashMap<>();

    /**
     * 获取字典项
     *
     * @param dictDTO 字典项编码
     * @return 表列表
     */
    @PostMapping("/getDictList")
    @ApiOperation(value = "获取字典项")
    public ResponseDataMsg<Map<String, List<KeyNameVo>>> getDictList(@RequestBody DictDTO dictDTO) throws IOException {
        if (dictDTO == null || ObjectUtil.isEmpty(dictDTO.getDictTypeList())) {
            ExceptionUtil.illegalParameter();
        }

        Map<String, List<KeyNameVo>> dictMap = new HashMap<>();
        for (String dictType : dictDTO.getDictTypeList()) {
            dictMap.put(dictType, cache.getOrDefault(dictType, new ArrayList<>()));
        }

        return ResponseDataMsg.succeed(dictMap);
    }

    /**
     * 初始化
     */
    @PostConstruct
    public void init() throws IOException, ClassNotFoundException {
        List<Class<?>> classList = new ArrayList<Class<?>>();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metaReader = new CachingMetadataReaderFactory();
        org.springframework.core.io.Resource[] resources = resolver.getResources(packageName);
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        for (Resource resource : resources) {
            MetadataReader reader = metaReader.getMetadataReader(resource);
            String className = reader.getClassMetadata().getClassName();
            Class<?> clazz = loader.loadClass(className);
            if (clazz.isEnum() && (IEnumDict.class.isAssignableFrom(clazz))) {
                classList.add(clazz);
            }

            // 处理内部类
            Class[] innerClazzs = clazz.getDeclaredClasses();
            if (innerClazzs != null && innerClazzs.length > 0) {
                for (Class innerClazz : innerClazzs) {
                    if (innerClazz.isEnum() && (IEnumDict.class.isAssignableFrom(innerClazz))) {
                        classList.add(innerClazz);
                    }
                }
            }
        }

        if (ObjectUtil.isNotEmpty(classList)) {
            for (Class c : classList) {
                try {
                    Method method = c.getDeclaredMethod("values");
                    Object[] enumsArrays = (Object[]) method.invoke(c);

                    method = c.getDeclaredMethod("getDict");

                    if (ObjectUtil.isNotEmpty(enumsArrays)) {
                        List<KeyNameVo> keyNameList = new ArrayList<>();
                        for (Object enumsValue : enumsArrays) {
                            KeyNameVo keyValue = (KeyNameVo) method.invoke(enumsValue);
                            keyNameList.add(keyValue);
                        }

                        method = c.getDeclaredMethod("getDictType");
                        cache.put((String) method.invoke(enumsArrays[0]), keyNameList);
                    }
                } catch (Exception e) {
                    log.error("DictController.init, error:{}", e);
                }
            }
        }
    }
}
