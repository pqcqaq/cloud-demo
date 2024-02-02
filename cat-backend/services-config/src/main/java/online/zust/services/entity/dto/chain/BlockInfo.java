package online.zust.services.entity.dto.chain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.zust.services.entity.dto.chain.blockinfo.Block;

/**
 * @author qcqcqc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlockInfo {
    private Block block;
}
