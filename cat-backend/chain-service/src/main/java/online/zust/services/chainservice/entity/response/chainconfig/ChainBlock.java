package online.zust.services.chainservice.entity.response.chainconfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chainmaker.pb.config.ChainConfigOuterClass;

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

    public ChainBlock(ChainConfigOuterClass.BlockConfig block) {
        this.txTimestampVerify = block.getTxTimestampVerify();
        this.txTimeout = block.getTxTimeout();
        this.blockTxCapacity = block.getBlockTxCapacity();
        this.blockSize = block.getBlockSize();
        this.blockInterval = block.getBlockInterval();
        this.txParameterSize = block.getTxParameterSize();
    }
}
