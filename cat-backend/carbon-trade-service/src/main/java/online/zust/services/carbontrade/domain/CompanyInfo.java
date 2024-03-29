package online.zust.services.carbontrade.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import online.zust.services.annotation.OtODeepSearch;
import online.zust.services.carbontrade.service.impl.IndustryServiceImpl;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author qcqcqc
 * @TableName company_info
 */
@TableName(value = "company_info", autoResultMap = true)
@Data
public class CompanyInfo implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = -6030775323206689813L;
    /**
     * 公司id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 公司名
     */
    private String name;

    /**
     *
     */
    private Long industryId;

    @OtODeepSearch(service = IndustryServiceImpl.class, baseId = "industryId")
    @TableField(exist = false)
    private Industry industry;

    /**
     * 添加时间
     */
    private Date createTime;

    /**
     * 添加者
     */
    private Long createBy;

    /**
     *
     */
    @TableLogic
    private Integer disabled;

}
