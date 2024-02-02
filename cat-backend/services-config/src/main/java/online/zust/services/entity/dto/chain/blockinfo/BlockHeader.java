package online.zust.services.entity.dto.chain.blockinfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qcqcqc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlockHeader {
    private Integer blockVersion;
    private String blockType;
    private String chainId;
    private Long blockHeight;
    private String blockHash;
    private String preBlockHash;
    private Long preConfigHeight;
    private Integer txCount;
    private String txRoot;
    private String dagHash;
    private String rwSetRoot;
    private Long blockTimestamp;
    private String consensusArgs;
    private Proposer proposer;
    private String signature;

}
