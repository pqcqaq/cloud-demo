package online.zust.services.chainservice.entity.response.txresponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chainmaker.pb.common.ResultOuterClass;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qcqcqc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractResult {
    private String result;
    private String message;
    private List<ContractEvent> contractEvent;
    private Long gasUsed;

    public ContractResult(ResultOuterClass.ContractResult contractResult) {
        this.result = contractResult.getResult().toStringUtf8();
        this.message = contractResult.getMessage();
        this.contractEvent = new ArrayList<>();
        int contractEventCount = contractResult.getContractEventCount();
        for (int i = 0; i < contractEventCount; i++) {
            this.contractEvent.add(new ContractEvent(contractResult.getContractEvent(i)));
        }
        this.gasUsed = contractResult.getGasUsed();
    }
}
