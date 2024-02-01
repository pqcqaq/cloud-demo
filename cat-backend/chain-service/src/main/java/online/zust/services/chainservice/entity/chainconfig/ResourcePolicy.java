package online.zust.services.chainservice.entity.chainconfig;

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
public class ResourcePolicy {
    private String resourceName;

    public ResourcePolicy(ChainConfigOuterClass.ResourcePolicy resourcePolicy) {
        this.resourceName = resourcePolicy.getResourceName();
    }
}
