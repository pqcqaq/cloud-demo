package online.zust.services.carbon.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import online.zust.services.annotation.OtODeepSearch;
import online.zust.services.carbon.service.impl.CompanyInfoServiceImpl;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author qcqcqc
 * @TableName order_info
 */
@TableName(value = "order_info", autoResultMap = true)
@Data
public class OrderInfo implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 7174823353924399418L;
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     *
     */
    private Integer type;

    /**
     *
     */
    private Long productId;

    /**
     *
     */
    private Long saleCompanyId;

    /**
     * 出售企业
     */
    @TableField(exist = false)
    @OtODeepSearch(service = CompanyInfoServiceImpl.class, baseId = "saleCompanyId")
    private CompanyInfo saleCompanyInfo;

    /**
     *
     */
    private Long buyCompanyId;

    /**
     * 求购企业
     */
    @TableField(exist = false)
    @OtODeepSearch(service = CompanyInfoServiceImpl.class, baseId = "buyCompanyId")
    private CompanyInfo buyCompanyInfo;

    /**
     *
     */
    private Long createBy;

    /**
     *
     */
    private Date createTime;

    /**
     * 状态：0：刚刚下单，1：已同意，2：已拒绝，3：失败
     */
    private Integer status;

    /**
     *
     */
    @TableLogic
    private Integer disabled;
}
