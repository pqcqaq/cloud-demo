package online.zust.services.chainservice.entity.response.blockinfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chainmaker.pb.common.ChainmakerBlock;
import org.chainmaker.pb.common.ChainmakerTransaction;

import java.util.List;

/**
 * @author qcqcqc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Block {
    private BlockHeader header;
    private Dag dag;
    private List<Transaction> txs;

    public Block(ChainmakerBlock.Block block) {
        this.header = new BlockHeader(block.getHeader());
        this.dag = new Dag(block.getDag());
        List<ChainmakerTransaction.Transaction> txsList = block.getTxsList();
        this.txs = txsList.stream().map(Transaction::new).toList();
    }
}
