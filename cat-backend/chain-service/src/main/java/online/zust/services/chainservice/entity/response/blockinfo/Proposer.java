package online.zust.services.chainservice.entity.response.blockinfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chainmaker.pb.accesscontrol.MemberOuterClass;

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

    public Proposer(MemberOuterClass.Member proposer) {
        this.orgId = proposer.getOrgId();
        this.memberType = proposer.getMemberType().name();
        this.memberInfo = proposer.getMemberInfo().toStringUtf8();
    }
}
