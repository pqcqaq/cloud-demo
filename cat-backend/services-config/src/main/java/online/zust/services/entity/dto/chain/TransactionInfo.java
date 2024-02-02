package online.zust.services.entity.dto.chain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.zust.services.entity.dto.chain.blockinfo.Transaction;

/**
 * @author qcqcqc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionInfo {
    private Transaction transaction;
    private Long blockHeight;
    private String blockHash;
    private Integer txIndex;
    private Long blockTimestamp;
}
