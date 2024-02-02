package online.zust.services.entity.dto.chain.blockinfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author qcqcqc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dag {
    private List<Neighbor> vertexes;
}
