package online.zust.services.entity.dto.chain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.zust.services.entity.dto.chain.txresponse.ContractResult;

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
}
