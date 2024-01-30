package online.zust.services.chainservice.servcice.impl;

import online.zust.services.chainservice.servcice.ChainService;
import org.chainmaker.pb.config.ChainConfigOuterClass;
import org.chainmaker.sdk.ChainClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author qcqcqc
 */
@Service
public class ChainServiceImpl implements ChainService {

    private final ChainClient chainClient;

    @Autowired
    public ChainServiceImpl(ChainClient chainClient) {
        this.chainClient = chainClient;
    }

    @Override
    public ChainConfigOuterClass.ChainConfig getChainConfig() {
        ChainConfigOuterClass.ChainConfig chainConfig = null;
        try {
            chainConfig = chainClient.getChainConfig(20000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chainConfig;
    }

}
