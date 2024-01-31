package online.zust.services.chainservice.service;

import online.zust.services.chainservice.entity.ChainConfig;
import online.zust.services.chainservice.entity.TxResponse;

/**
 * @author qcqcqc
 */
public interface ChainService {
    ChainConfig getChainConfig();

    TxResponse createContract();

    TxResponse invokeContract();

    TxResponse queryContract();
}
