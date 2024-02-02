package online.zust.services.entity.dto.chain.chainconfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qcqcqc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrustMemberConfig {
    private String memberInfo;
    private String orgId;
    private String role;
    private String nodeId;
}
