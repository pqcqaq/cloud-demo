package online.zust.services.chainservice.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.zust.services.chainservice.entity.response.chainconfig.*;
import org.chainmaker.pb.config.ChainConfigOuterClass;

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

    public ChainConfig(ChainConfigOuterClass.ChainConfig chainConfig) {
        this.chainId = chainConfig.getChainId();
        this.version = chainConfig.getVersion();
        this.authType = chainConfig.getAuthType();
        this.crypto = new ChainCrypto(chainConfig.getCrypto());
        this.block = new ChainBlock(chainConfig.getBlock());
        this.trustRoots = chainConfig.getTrustRootsList().stream().map(TrustRootConfig::new).collect(java.util.stream.Collectors.toList());
        this.trustMembers = chainConfig.getTrustMembersList().stream().map(TrustMemberConfig::new).collect(java.util.stream.Collectors.toList());
        this.resourcePolicies = chainConfig.getResourcePoliciesList().stream().map(ResourcePolicy::new).collect(java.util.stream.Collectors.toList());
        this.disabledNativeContract = chainConfig.getDisabledNativeContractList();
        this.vm = new ChainVmSupport(chainConfig.getVm());
    }
}
