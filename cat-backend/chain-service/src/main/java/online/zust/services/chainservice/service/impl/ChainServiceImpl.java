package online.zust.services.chainservice.service.impl;

import online.zust.services.chainservice.chainmaker.ChainConfig;
import online.zust.services.chainservice.chainmaker.Contract;
import online.zust.services.chainservice.entity.TxResponse;
import online.zust.services.chainservice.service.ChainService;
import org.chainmaker.sdk.ChainClient;
import org.chainmaker.sdk.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author qcqcqc
 */
@Service
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

    public TxResponse createContract() {
        return Contract.createContract(chainClient, user);
    }

    public TxResponse invokeContract() {
        return Contract.invokeContract(chainClient);
    }

    public TxResponse queryContract() {
        return Contract.queryContract(chainClient);
    }
}
