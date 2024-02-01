package online.zust.services.chainservice.entity.txresponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chainmaker.pb.common.ResultOuterClass;

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

    public ContractEvent(ResultOuterClass.ContractEvent event) {
        this.topic = event.getTopic();
        this.txId = event.getTxId();
        this.contractName = event.getContractName();
        this.contractVersion = event.getContractVersion();
        this.eventData = event.getEventDataList();
    }
}
