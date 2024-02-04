package online.zust.services.carbon.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author qcqcqc
 * @TableName report_a
 */
@TableName(value = "report_a", autoResultMap = true)
@Data
public class ReportA implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 6032142488731195070L;
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     *
     */
    private String typeA;

    /**
     *
     */
    private String dataA;

    /**
     *
     */
    @TableField(value = "type_a_id")
    private Long typeAId;

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
