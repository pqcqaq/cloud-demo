package online.zust.services.chainservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import online.zust.common.exception.ServiceException;
import online.zust.services.chainservice.chainmaker.ChainConfig;
import online.zust.services.chainservice.chainmaker.Contract;
import online.zust.services.chainservice.entity.TxResponse;
import online.zust.services.chainservice.service.ChainService;
import org.chainmaker.pb.common.ContractOuterClass;
import org.chainmaker.sdk.ChainClient;
import org.chainmaker.sdk.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author qcqcqc
 */
@Service
@Slf4j
public class ChainServiceImpl implements ChainService {
    private final ChainClient chainClient;
    private final User user;

    @Autowired
    public ChainServiceImpl(ChainClient chainClient, User user) {
        this.chainClient = chainClient;
        this.user = user;
    }

    @Override
    public online.zust.services.chainservice.entity.ChainConfig getChainConfig() {
        return new online.zust.services.chainservice.entity.ChainConfig(ChainConfig.getChainConfig(chainClient));
    }

    @Override
    public TxResponse createContract() {
        return Contract.createContract(chainClient, user);
    }

    @Override
    public TxResponse invokeContract() {
        return Contract.invokeContract(chainClient);
    }

    @Override
    public TxResponse queryContract() {
        return Contract.queryContract(chainClient);
    }

    @Override
    public TxResponse uploadAndCreateContract(MultipartFile file, String contractName, String contractVersion, String runtimeType) {
        byte[] bytes;
        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            log.error("upload file error: {}", e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return Contract.createContract(bytes, contractName, contractVersion, ContractOuterClass.RuntimeType.valueOf(runtimeType), chainClient, user);
    }
}
