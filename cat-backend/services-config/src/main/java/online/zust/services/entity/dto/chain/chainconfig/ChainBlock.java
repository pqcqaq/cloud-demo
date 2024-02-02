package online.zust.services.entity.dto.chain.chainconfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qcqcqc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChainBlock {
    private Boolean txTimestampVerify;
    private Integer txTimeout;
    private Integer blockTxCapacity;
    private Integer blockSize;
    private Integer blockInterval;
    private Integer txParameterSize;
}
