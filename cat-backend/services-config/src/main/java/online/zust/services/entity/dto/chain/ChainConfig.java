package online.zust.services.entity.dto.chain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.zust.services.entity.dto.chain.chainconfig.*;

import java.util.List;

/**
 * @author qcqcqc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChainConfig {
    private String chainId;
    private String version;
    private String authType;
    private ChainCrypto crypto;
    private ChainBlock block;
    private List<TrustRootConfig> trustRoots;
    private List<TrustMemberConfig> trustMembers;
    private List<ResourcePolicy> resourcePolicies;
    private List<String> disabledNativeContract;
    private ChainVmSupport vm;
}
