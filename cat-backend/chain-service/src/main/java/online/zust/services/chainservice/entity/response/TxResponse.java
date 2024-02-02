package online.zust.services.chainservice.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.zust.services.chainservice.entity.response.txresponse.ContractResult;
import org.chainmaker.pb.common.ResultOuterClass;

/**
 * @author qcqcqc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TxResponse {
    private String code;
    private String msg;
    private String txId;
    private ContractResult contractResult;

    public TxResponse(ResultOuterClass.TxResponse response) {
        this.code = response.getCode().name();
        this.msg = response.getMessage();
        this.txId = response.getTxId();
        this.contractResult = new ContractResult(response.getContractResult());
    }
}
