package online.zust.services.chainservice.entity.response.blockinfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chainmaker.pb.common.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qcqcqc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payload {
    private String chainId;
    private String txType;
    private String txId;
    private Long timestamp;
    private Long expirationTime;
    private String contractName;
    private String methodName;
    private Map<String, String> parameters;
    private Long sequence;

    public Payload(Request.Payload payload) {
        this.chainId = payload.getChainId();
        this.txType = payload.getTxType().name();
        this.txId = payload.getTxId();
        this.timestamp = payload.getTimestamp();
        this.expirationTime = payload.getExpirationTime();
        this.contractName = payload.getContractName();
        this.methodName = payload.getMethod();
        List<Request.KeyValuePair> parametersList = payload.getParametersList();
        HashMap<String, String> parameters1 = new HashMap<>();
        for (Request.KeyValuePair keyValuePair : parametersList) {
            parameters1.put(keyValuePair.getKey(), keyValuePair.getValue().toStringUtf8());
        }
        this.parameters = parameters1;
        this.sequence = payload.getSequence();
    }
}
