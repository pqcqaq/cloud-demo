package online.zust.services.chainservice.service;

import online.zust.services.chainservice.entity.response.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    /**
     * 上传并创建合约
     *
     * @param file            合约文件
     * @param contractName    合约名称
     * @param contractVersion 合约版本
     * @param runtimeType     运行时类型
     * @return 交易响应
     */
    TxResponse uploadAndCreateContract(MultipartFile file, String contractName, String contractVersion, String runtimeType);

    /**
     * 获取区块信息
     *
     * @param height 区块高度
     * @return 区块信息
     */
    BlockInfo getBlockByHeight(Long height);

    /**
     * 根据交易ID获取交易信息
     *
     * @param txId 交易ID
     * @return 交易信息
     */
    TransactionInfo getTxByTxId(String txId);

    /**
     * 获取合约列表
     *
     * @return 合约列表
     */
    List<Contract> getContractList();

    /**
     * 根据交易hash获取区块信息
     *
     * @param hash 交易hash
     * @return 区块详情
     */
    BlockInfo getBlockByHash(String hash);

    /**
     * 根据交易ID获取区块信息
     *
     * @param txId 交易ID
     * @return 区块信息
     */
    BlockInfo getBlockByTxId(String txId);
}
