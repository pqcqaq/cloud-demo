package online.zust.services.chainservice.entity.response.blockinfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chainmaker.pb.common.ChainmakerBlock;

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

    public BlockHeader(ChainmakerBlock.BlockHeader header) {
        this.blockVersion = header.getBlockVersion();
        this.blockType = header.getBlockType().name();
        this.chainId = header.getChainId();
        this.blockHeight = header.getBlockHeight();
        this.blockHash = header.getBlockHash().toStringUtf8();
        this.preBlockHash = header.getPreBlockHash().toStringUtf8();
        this.preConfigHeight = header.getPreConfHeight();
        this.txCount = header.getTxCount();
        this.txRoot = header.getTxRoot().toStringUtf8();
        this.dagHash = header.getDagHash().toStringUtf8();
        this.rwSetRoot = header.getRwSetRoot().toStringUtf8();
        this.blockTimestamp = header.getBlockTimestamp();
        this.consensusArgs = header.getConsensusArgs().toStringUtf8();
        this.proposer = new Proposer(header.getProposer());
        this.signature = header.getSignature().toStringUtf8();
    }
}
