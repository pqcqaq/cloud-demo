package online.zust.services.chainservice.entity.response.blockinfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chainmaker.pb.common.Request;

/**
 * @author qcqcqc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EndorsementEntry {
    private Member signer;
    private String signature;

    public EndorsementEntry(Request.EndorsementEntry sender) {
        this.signer = new Member(sender.getSigner());
        this.signature = sender.getSignature().toStringUtf8();
    }
}
