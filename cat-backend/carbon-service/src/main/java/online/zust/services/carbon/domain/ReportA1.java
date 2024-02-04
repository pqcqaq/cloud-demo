package online.zust.services.carbon.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author qcqcqc
 * @TableName report_a1
 */
@TableName(value = "report_a1", autoResultMap = true)
@Data
public class ReportA1 implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = -1934020720730659427L;
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     *
     */
    private String typeA1;

    /**
     *
     */
    private BigDecimal dataA1;

    /**
     *
     */
    private Long typeA1Id;

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
