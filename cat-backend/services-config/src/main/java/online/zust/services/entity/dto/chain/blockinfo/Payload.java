package online.zust.services.entity.dto.chain.blockinfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
}
