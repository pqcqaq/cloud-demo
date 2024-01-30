package online.zust.services.chainservice.servcice.impl;

import online.zust.services.chainservice.client.RemoteChainClient;
import online.zust.services.chainservice.servcice.ChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author qcqcqc
 */
@Service
public class ChainServiceImpl implements ChainService {

    private final RemoteChainClient remoteChainClient;

    @Autowired
    public ChainServiceImpl(RemoteChainClient remoteChainClient) {
        this.remoteChainClient = remoteChainClient;
    }

    @Override
    public String getChainConfig() {
        return remoteChainClient.getChainConfig().getData();
    }

}
