package online.zust.services.chainservice.entity.chainconfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chainmaker.pb.config.ChainConfigOuterClass;

import java.util.List;

/**
 * @author qcqcqc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrustRootConfig {
    private String orgId;
    private List<String> root;

    public TrustRootConfig(ChainConfigOuterClass.TrustRootConfig trustRootConfig) {
        this.orgId = trustRootConfig.getOrgId();
        int rootCount = trustRootConfig.getRootCount();
        this.root = new java.util.ArrayList<>(rootCount);
        for (int i = 0; i < rootCount; i++) {
            this.root.add(trustRootConfig.getRoot(i));
        }
    }
}
