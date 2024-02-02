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
public class Member {
    private String orgId;
    private Integer memberType;
    private String memberInfo;

    public Member(MemberOuterClass.Member signer) {
        this.orgId = signer.getOrgId();
        this.memberType = signer.getMemberType().getNumber();
        this.memberInfo = signer.getMemberInfo().toStringUtf8();
    }
}
