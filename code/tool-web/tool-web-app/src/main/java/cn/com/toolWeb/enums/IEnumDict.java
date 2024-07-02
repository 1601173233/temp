package cn.com.toolWeb.enums;

import cn.com.toolWeb.model.base.KeyNameVo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 简单的字典项
 *
 * @author jack.huang
 * @date 2020/4/24 17:38
 */
public interface IEnumDict {

    /**
     * 字典项信息
     *
     * @return 字典项信息
     */
    KeyNameVo getDict();

    /**
     * 字典项类型
     *
     * @return 字典项类型
     */
    String getDictType();
}
