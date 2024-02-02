package online.zust.services.chainservice.entity.response.blockinfo;

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
public class Result {
    private String code;
    private ContractResult contractResult;
    private String rwSetHash;
    private String message;

    public Result(ResultOuterClass.Result result) {
        this.code = result.getCode().name();
        this.contractResult = new ContractResult(result.getContractResult());
        this.rwSetHash = result.getRwSetHash().toStringUtf8();
        this.message = result.getMessage();
    }
}
