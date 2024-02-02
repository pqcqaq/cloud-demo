package online.zust.services.chainservice.entity.response.blockinfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chainmaker.pb.common.ChainmakerBlock;

import java.util.List;

/**
 * @author qcqcqc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Neighbor {
    private List<Integer> neighbors;
    private Integer neighborsMemoizedSerializedSize;

    public Neighbor(ChainmakerBlock.DAG.Neighbor neighbor) {
        this.neighbors = neighbor.getNeighborsList();
        this.neighborsMemoizedSerializedSize = neighbor.getSerializedSize();
    }
}
