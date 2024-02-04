package online.zust.services.carbon.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author qcqcqc
 * @TableName report_alpha1
 */
@TableName(value = "report_alpha1", autoResultMap = true)
@Data
public class ReportAlpha1 implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = -5035745301839809459L;
    /**
     *
     */
    private Long id;

    /**
     *
     */
    private String typeAlpha1;

    /**
     *
     */
    private String dataAlpha1;

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
