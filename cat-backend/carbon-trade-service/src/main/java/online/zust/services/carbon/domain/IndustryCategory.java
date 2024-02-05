package online.zust.services.carbon.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author qcqcqc
 * @TableName industry_category
 */
@TableName(value = "industry_category", autoResultMap = true)
@Data
public class IndustryCategory implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = -8829283810920208965L;
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 大类名称
     */
    private String name;

    /**
     *
     */
    @TableField(value = "industry_ids", typeHandler = JacksonTypeHandler.class)
    private String industryIds;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Long createBy;

    /**
     *
     */
    @TableLogic
    private Integer disabled;

}
