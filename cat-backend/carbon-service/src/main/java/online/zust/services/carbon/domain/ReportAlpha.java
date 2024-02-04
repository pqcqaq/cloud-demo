package online.zust.services.carbon.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author qcqcqc
 * @TableName report_alpha
 */
@TableName(value = "report_alpha", autoResultMap = true)
@Data
public class ReportAlpha implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = -7765998559420641034L;
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     *
     */
    private String typeAlpha;

    /**
     *
     */
    private BigDecimal dataAlpha;

    /**
     *
     */
    private Long typeAlphaId;

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
