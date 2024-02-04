package online.zust.services.carbon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.zust.services.EnhanceService;
import online.zust.services.carbon.domain.ProductInfo;
import online.zust.services.carbon.service.ProductInfoService;
import online.zust.services.carbon.mapper.ProductInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author qcqcqc
* @description 针对表【product_info】的数据库操作Service实现
* @createDate 2024-02-03 22:22:51
*/
@Service
public class ProductInfoServiceImpl extends EnhanceService<ProductInfoMapper, ProductInfo>
    implements ProductInfoService{

}




