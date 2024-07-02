package cn.com.toolWeb.model.base;

import cn.com.toolWeb.constant.CodeEnum;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页参数.
 *
 * 分页时使用 PageParamsVo(Integer current, Integer limit) 构造
 * 不需要分页的时候使用 getNoPage() 构造
 *
 * @param <T> 泛型类
 * @author hyj
 * @since  2020-05-22 14:55:05
 */
@Data
public class PageParamsVo<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 当前页码. */
    Integer current;

    /** 每页条数. */
    Integer limit;

    /** 是否分页. */
    Boolean isPage;

    /** mybatisPlus分页参数. */
    Page<T> page;

    /** 返回结果. */
    List<T> resultList;

    /** 分页查询结果. */
    PageResult<T> pageResult;

    /**
     * 空构造方法.
     */
    public PageParamsVo() {

    }

    /**
     * 空构造方法.
     */
    public PageParamsVo(BaseQueryDto baseQueryDto) {
        this(baseQueryDto.getPageNum(), baseQueryDto.getPageSize(), baseQueryDto.getIsPage());
    }

    /**
     * 默认为分页.
     *
     * @param current 当前页码
     * @param limit   每页条数
     */
    public PageParamsVo(Integer current, Integer limit) {
        this(current, limit, true);
    }

    /**
     * 构造方法.
     *
     * @param current 当前页码
     * @param limit   每页条数
     * @param isPage  是否分页
     */
    public PageParamsVo(Integer current, Integer limit, Boolean isPage) {
        this.current = current;
        this.limit = limit;
        this.isPage = isPage;
        this.page = bulidPage();
    }

    /**
     * 构造不分页的参数.
     *
     * @return 不分页的对象
     */
    public static PageParamsVo getNoPage() {
        PageParamsVo pageParamsVo = new PageParamsVo();
        pageParamsVo.setIsPage(false);
        return pageParamsVo;
    }

    /**
     * 获取分页查询参数.
     *
     * @return 返回分页查询参数
     */
    public Page<T> bulidPage() {
        // 不分页直接返回空
        if (!isPage) {
            return null;
        }

        return new Page<>(current, limit);
    }

    /**
     * 设置返回结果.
     *
     * @param resultList 返回结果
     */
    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
        if (isPage) {
            pageResult = PageResult.<T>builder().data(resultList).code(CodeEnum.SUCCESS.getCode()).count(page.getTotal()).build();
        } else {
            pageResult = null;
        }
    }

    /**
     * 初始化返回的数据.
     *
     * @return 分页返回结果
     */
    public PageResult<T> initPageResult() {
        pageResult = PageResult.<T>builder().data(new ArrayList<>()).code(CodeEnum.SUCCESS.getCode()).count(0L).build();
        return getPageResult();
    }

    /**
     * 获取分页返回结果.
     *
     * @return 分页返回结果
     */
    public PageResult<T> getPageResult() {
        if (pageResult != null) {
            return pageResult;
        } else {
            return PageResult.<T>builder().data(new ArrayList<>()).code(CodeEnum.SUCCESS.getCode()).count(0L).build();
        }
    }

    /**
     * 获取空的返回值.
     *
     * @return 空的返回值
     */
    public static PageResult getEmptyPageResult() {
        return PageResult.builder().data(new ArrayList<>()).code(CodeEnum.SUCCESS.getCode()).count(0L).build();
    }
}
