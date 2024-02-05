package online.zust.services.carbontrade.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import online.zust.services.annotation.OtODeepSearch;
import online.zust.services.carbontrade.service.impl.CompanyInfoServiceImpl;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author qcqcqc
 * @TableName product_info
 */
@TableName(value = "product_info", autoResultMap = true)
@Data
public class ProductInfo implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = -7684435947533722711L;
    /**
     * 商品id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 0：出售，1：求购
     */
    private Integer type;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 详情信息
     */
    private String details;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 关联企业id
     */
    private Long companyId;

    /**
     * 关联企业
     */
    @OtODeepSearch(baseId = "companyId", service = CompanyInfoServiceImpl.class)
    @TableField(exist = false)
    private CompanyInfo companyInfo;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建者
     */
    private Long createBy;

    /**
     *
     */
    @TableLogic
    private Integer disabled;
}
