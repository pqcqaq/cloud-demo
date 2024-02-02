package online.zust.services.entity.dto.chain.blockinfo;

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
public class Result {
    private String code;
    private ContractResult contractResult;
    private String rwSetHash;
    private String message;
}
