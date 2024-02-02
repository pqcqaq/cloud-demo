package online.zust.services.chainservice.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.zust.services.chainservice.entity.response.blockinfo.Transaction;
import org.chainmaker.pb.common.ChainmakerTransaction;

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

    public TransactionInfo(ChainmakerTransaction.TransactionInfo txByTxId) {
        this.transaction = new Transaction(txByTxId.getTransaction());
        this.blockHeight = txByTxId.getBlockHeight();
        this.blockHash = txByTxId.getBlockHash().toStringUtf8();
        this.txIndex = txByTxId.getTxIndex();
        this.blockTimestamp = txByTxId.getBlockTimestamp();
    }
}
