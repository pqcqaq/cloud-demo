package online.zust.services.carbon.service.impl;

import online.zust.services.EnhanceService;
import online.zust.services.carbon.domain.CompanyInfo;
import online.zust.services.carbon.mapper.CompanyInfoMapper;
import online.zust.services.carbon.service.CompanyInfoService;
import org.springframework.stereotype.Service;

/**
 * @author qcqcqc
 * @description 针对表【company_info】的数据库操作Service实现
 * @createDate 2024-02-03 22:19:50
 */
@Service
public class CompanyInfoServiceImpl extends EnhanceService<CompanyInfoMapper, CompanyInfo>
        implements CompanyInfoService {
}




