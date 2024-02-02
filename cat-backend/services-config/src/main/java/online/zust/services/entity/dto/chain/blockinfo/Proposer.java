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
public class Proposer {
    private String orgId;
    private String memberType;
    private String memberInfo;
}
