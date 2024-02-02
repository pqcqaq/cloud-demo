package online.zust.services.entity.dto.chain.txresponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author qcqcqc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractEvent {
    private String topic;
    private String txId;
    private String contractName;
    private String contractVersion;
    private List<String> eventData;

}
