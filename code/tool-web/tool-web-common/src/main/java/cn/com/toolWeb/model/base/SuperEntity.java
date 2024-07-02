package cn.com.toolWeb.model.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
 * 基础类
 *
 * @author : hyj
 * @version : 2020年11月04日
 * @since
 */
@Data
public class SuperEntity extends Model {
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId
    private Long id;

    /** 创建时间 */
    private Date createdTime;

    /** 修改时间 */
    private Date updateTime;
}