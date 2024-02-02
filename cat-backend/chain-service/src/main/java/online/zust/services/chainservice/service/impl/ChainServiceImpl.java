package online.zust.services.chainservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import online.zust.common.exception.ServiceException;
import online.zust.services.chainservice.chainmaker.SystemContract;
import online.zust.services.chainservice.chainmaker.contract.DefaultContract;
import online.zust.services.chainservice.entity.response.*;
import online.zust.services.chainservice.service.ChainService;
import org.chainmaker.pb.common.*;
import org.chainmaker.pb.config.ChainConfigOuterClass;
import org.chainmaker.sdk.ChainClient;
import org.chainmaker.sdk.User;
import org.chainmaker.sdk.utils.SdkUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author qcqcqc
 */
@Service
@Slf4j
public class ChainServiceImpl implements ChainService {
    private final ChainClient chainClient;
    private final User user;

    @Value("${chain.rpc-call-timeout}")
    private Long rpcCallTimeout;
    @Value("${chain.sync-result-timeout}")
    private Long syncResultTimeout;

    private final DefaultContract defaultContract;

    @Autowired
    public ChainServiceImpl(ChainClient chainClient, User user, DefaultContract defaultContract) {
        this.chainClient = chainClient;
        this.user = user;
        this.defaultContract = defaultContract;
    }

    /**
     * 获取链配置
     *
     * @return 链配置
     */
    @Override
    public ChainConfig getChainConfig() {
        return new ChainConfig(getChainConfig(chainClient));
    }

    /**
     * 创建合约
     *
     * @return 交易响应
     */
    @Override
    public TxResponse createContract() {
        return defaultContract.createContract(chainClient, user);
    }

    /**
     * 调用合约
     *
     * @return 交易响应
     */
    @Override
    public TxResponse invokeContract() {
        return defaultContract.invokeContract(chainClient);
    }

    /**
     * 查询合约
     *
     * @return 交易响应
     */
    @Override
    public TxResponse queryContract() {
        return defaultContract.queryContract(chainClient);
    }

    /**
     * 上传并创建合约
     *
     * @param file            合约文件
     * @param contractName    合约名称
     * @param contractVersion 合约版本
     * @param runtimeType     运行时类型
     * @return 交易响应
     */
    @Override
    public TxResponse uploadAndCreateContract(MultipartFile file, String contractName, String contractVersion, String runtimeType) {
        byte[] bytes;
        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            log.error("upload file error: {}", e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return defaultContract.createContract(bytes, contractName, contractVersion, ContractOuterClass.RuntimeType.valueOf(runtimeType), chainClient, user);
    }

    /**
     * 根据区块高度获取区块信息
     *
     * @param height 区块高度
     * @return 区块信息
     */
    @Override
    public BlockInfo getBlockByHeight(Long height) {
        return SystemContract.getBlockByHeight(height, chainClient);
    }

    /**
     * 获取链配置
     *
     * @param chainClient 链客户端
     * @return 链配置
     */
    private ChainConfigOuterClass.ChainConfig getChainConfig(ChainClient chainClient) {
        ChainConfigOuterClass.ChainConfig chainConfig;
        try {
            chainConfig = chainClient.getChainConfig(rpcCallTimeout);
        } catch (Exception e) {
            log.error("get chain config error: {}", e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return chainConfig;
    }

    /**
     * 更新链配置
     *
     * @param chainClient 链客户端
     * @param adminUser   管理员用户
     * @return 交易响应
     */
    public TxResponse updateChainConfig(ChainClient chainClient, User... adminUser) {
        ResultOuterClass.TxResponse responseInfo;
        try {
            Request.Payload payload = chainClient.createPayloadOfChainConfigBlockUpdate(false,
                    9002, 200, 225, 2000, 10000, rpcCallTimeout);
            Request.EndorsementEntry[] endorsementEntries = SdkUtils.getEndorsers(payload,
                    adminUser);
            responseInfo = chainClient.updateChainConfig(payload, endorsementEntries, rpcCallTimeout, syncResultTimeout);
        } catch (Exception e) {
            log.error("update chain config error: {}", e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return new TxResponse(responseInfo);
    }

    @Override
    public TransactionInfo getTxByTxId(String txId) {
        ChainmakerTransaction.TransactionInfo txByTxId;
        try {
            txByTxId = chainClient.getTxByTxId(txId, rpcCallTimeout);
        } catch (Exception e) {
            log.error("get tx by txId error: {}", e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return new TransactionInfo(txByTxId);
    }

    @Override
    public List<Contract> getContractList() {
        ContractOuterClass.Contract[] contractList;
        try {
            contractList = chainClient.getContractList(rpcCallTimeout);
        } catch (Exception e) {
            log.error("get contract list error: {}", e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return Arrays.stream(contractList).map(Contract::new)
                .collect(Collectors.toList());
    }

    @Override
    public BlockInfo getBlockByHash(String hash) {
        ChainmakerBlock.BlockInfo blockByHash;
        try {
            blockByHash = chainClient.getBlockByHash(hash, false, rpcCallTimeout);
        } catch (Exception e) {
            log.error("get block by hash error: {}", e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return new BlockInfo(blockByHash);
    }

    @Override
    public BlockInfo getBlockByTxId(String txId) {
        ChainmakerBlock.BlockInfo blockByTxId;
        try {
            blockByTxId = chainClient.getBlockByTxId(txId, false, rpcCallTimeout);
        } catch (Exception e) {
            log.error("get block by txId error: {}", e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return new BlockInfo(blockByTxId);
    }
}
