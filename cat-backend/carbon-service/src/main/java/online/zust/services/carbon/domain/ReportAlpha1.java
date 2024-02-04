package online.zust.services.carbon.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author qcqcqc
 * @TableName report_alpha1
 */
@TableName(value = "report_alpha1")
@Data
public class ReportAlpha1 implements Serializable {
    @Serial
    private static final long serialVersionUID = -3945346956057571251L;
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     *
     */
    private String typeAlpha1;

    /**
     *
     */
    private BigDecimal dataAlpha1;

    /**
     *
     */
    private Long typeAlpha1Id;

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
