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
public class Dag {
    private List<Neighbor> vertexes;

    public Dag(ChainmakerBlock.DAG dag) {
        List<ChainmakerBlock.DAG.Neighbor> vertexesList = dag.getVertexesList();
        this.vertexes = vertexesList.stream().map(Neighbor::new).toList();
    }
}
