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
 * @TableName emission_factors
 */
@TableName(value = "emission_factors", autoResultMap = true)
@Data
public class EmissionFactors implements Serializable {
    @Serial
    private static final long serialVersionUID = -1126388881178004004L;
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     *
     */
    private String type;

    /**
     *
     */
    private BigDecimal co2Data;

    /**
     *
     */
    private BigDecimal ch4Data;

    /**
     *
     */
    private BigDecimal no2Data;

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
