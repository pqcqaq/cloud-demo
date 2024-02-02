package online.zust.services.chainservice.entity.response.blockinfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chainmaker.pb.common.ChainmakerTransaction;
import org.chainmaker.pb.common.Request;

import java.util.List;

/**
 * @author qcqcqc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private Payload payload;
    private EndorsementEntry sender;
    private List<EndorsementEntry> endorsers;
    private Result result;

    public Transaction(ChainmakerTransaction.Transaction transaction) {
        this.payload = new Payload(transaction.getPayload());
        this.sender = new EndorsementEntry(transaction.getSender());
        List<Request.EndorsementEntry> endorsersList = transaction.getEndorsersList();
        this.endorsers = endorsersList.stream().map(EndorsementEntry::new).toList();
        this.result = new Result(transaction.getResult());
    }
}
