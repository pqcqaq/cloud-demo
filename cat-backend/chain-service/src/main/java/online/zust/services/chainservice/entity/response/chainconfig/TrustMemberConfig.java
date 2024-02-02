package online.zust.services.chainservice.entity.response.chainconfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chainmaker.pb.config.ChainConfigOuterClass;

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

    public TrustMemberConfig(ChainConfigOuterClass.TrustMemberConfig config) {
        this.memberInfo = config.getMemberInfo();
        this.orgId = config.getOrgId();
        this.role = config.getRole();
        this.nodeId = config.getNodeId();
    }
}
