package online.zust.services.chainservice.service;

import online.zust.services.chainservice.entity.ChainConfig;
import online.zust.services.chainservice.entity.TxResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author qcqcqc
 */
public interface ChainService {
    /**
     * 获取链配置
     *
     * @return 链配置
     */
    ChainConfig getChainConfig();

    /**
     * 创建合约
     *
     * @return 交易响应
     */
    TxResponse createContract();

    /**
     * 调用合约
     *
     * @return 交易响应
     */
    TxResponse invokeContract();

    /**
     * 查询合约
     *
     * @return 交易响应
     */
    TxResponse queryContract();

    TxResponse uploadAndCreateContract(MultipartFile file, String contractName, String contractVersion, String runtimeType);
}
