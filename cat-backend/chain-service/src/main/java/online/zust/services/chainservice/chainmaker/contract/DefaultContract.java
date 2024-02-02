package online.zust.services.chainservice.chainmaker.contract;

import lombok.extern.slf4j.Slf4j;
import online.zust.common.exception.ServiceException;
import online.zust.services.chainservice.entity.response.TxResponse;
import online.zust.services.chainservice.utils.FileUtils;
import org.chainmaker.pb.common.ContractOuterClass;
import org.chainmaker.pb.common.Request;
import org.chainmaker.pb.common.ResultOuterClass;
import org.chainmaker.sdk.ChainClient;
import org.chainmaker.sdk.User;
import org.chainmaker.sdk.utils.SdkUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * @author qcqcqc
 */
@Slf4j
@Component
public class DefaultContract {

    private static final String QUERY_CONTRACT_METHOD = "query";
    private static final String INVOKE_CONTRACT_METHOD = "increase";
    private static final String CONTRACT_NAME = "counter_sdk_java_demo";
    private static final String CONTRACT_FILE_PATH = "/rust-fact-1.0.0.wasm";

    @Value("${chain.rpc-call-timeout}")
    private Long rpcCallTimeout;
    @Value("${chain.sync-result-timeout}")
    private Long syncResultTimeout;

    public TxResponse createContract(ChainClient chainClient, User... user) {
        ResultOuterClass.TxResponse responseInfo;
        try {
            byte[] byteCode = FileUtils.getResourceFileBytes(CONTRACT_FILE_PATH);

            // 1. create payload
            Request.Payload payload = chainClient.createContractCreatePayload(CONTRACT_NAME, "1", byteCode,
                    ContractOuterClass.RuntimeType.WASMER, null);
            //2. create payloads with endorsement
            Request.EndorsementEntry[] endorsementEntries = SdkUtils
                    .getEndorsers(payload, user);

            // 3. send request
            responseInfo = chainClient.sendContractManageRequest(payload, endorsementEntries, rpcCallTimeout, 10000);
        } catch (Exception e) {
            log.error("create contract error: {}", e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return new TxResponse(responseInfo);
    }

    public TxResponse createContract(byte[] byteCode,
                                     String contractName,
                                     String contractVersion,
                                     ContractOuterClass.RuntimeType runtimeType,
                                     ChainClient chainClient, User... user) {
        ResultOuterClass.TxResponse responseInfo;
        try {

            // 1. create payload
            Request.Payload payload = chainClient.createContractCreatePayload(contractName, contractVersion, byteCode,
                    runtimeType, null);
            //2. create payloads with endorsement
            Request.EndorsementEntry[] endorsementEntries = SdkUtils
                    .getEndorsers(payload, user);

            // 3. send request
            responseInfo = chainClient.sendContractManageRequest(payload, endorsementEntries, rpcCallTimeout, 10000);
        } catch (Exception e) {
            log.error("create contract error: {}", e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return new TxResponse(responseInfo);
    }

    public TxResponse invokeContract(ChainClient chainClient) {
        ResultOuterClass.TxResponse responseInfo;
        try {
            responseInfo = chainClient.invokeContract(CONTRACT_NAME, INVOKE_CONTRACT_METHOD,
                    null, null, rpcCallTimeout, syncResultTimeout);
        } catch (Exception e) {
            log.error("invoke contract error: {}", e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return new TxResponse(responseInfo);
    }

    public TxResponse queryContract(ChainClient chainClient) {
        ResultOuterClass.TxResponse responseInfo;
        try {
            responseInfo = chainClient.queryContract(CONTRACT_NAME, QUERY_CONTRACT_METHOD,
                    null, null, rpcCallTimeout);
        } catch (Exception e) {
            log.error("query contract error: {}", e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return new TxResponse(responseInfo);
    }
}
