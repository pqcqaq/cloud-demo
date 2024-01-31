package online.zust.services.chainservice.chainmaker;

import lombok.extern.slf4j.Slf4j;
import online.zust.common.exception.ServiceException;
import online.zust.services.chainservice.entity.TxResponse;
import org.chainmaker.pb.common.Request;
import org.chainmaker.pb.common.ResultOuterClass;
import org.chainmaker.pb.config.ChainConfigOuterClass;
import org.chainmaker.sdk.ChainClient;
import org.chainmaker.sdk.User;
import org.chainmaker.sdk.utils.SdkUtils;

/**
 * @author qcqcqc
 */
@Slf4j
public class ChainConfig {

    public static ChainConfigOuterClass.ChainConfig getChainConfig(ChainClient chainClient) {
        ChainConfigOuterClass.ChainConfig chainConfig;
        try {
            chainConfig = chainClient.getChainConfig(20000);
        } catch (Exception e) {
            log.error("get chain config error: {}", e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return chainConfig;
    }

    public static TxResponse updateChainConfig(ChainClient chainClient, User... adminUser) {
        ResultOuterClass.TxResponse responseInfo;
        try {
            Request.Payload payload = chainClient.createPayloadOfChainConfigBlockUpdate(false,
                    9002, 200, 225, 2000, 10000, 20000L);
            Request.EndorsementEntry[] endorsementEntries = SdkUtils.getEndorsers(payload,
                    adminUser);
            responseInfo = chainClient.updateChainConfig(payload, endorsementEntries, 10000, 10000);
        } catch (Exception e) {
            log.error("update chain config error: {}", e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return new TxResponse(responseInfo);
    }
}
