package online.zust.services.carbontrade.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author qcqcqc
 * @TableName industry
 */
@TableName(value = "industry", autoResultMap = true)
@Data
public class Industry implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 7501038614796047918L;
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     *
     */
    private String name;

    /**
     * 行业详情
     */
    private String details;

    /**
     * 添加时间
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
