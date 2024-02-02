package online.zust.services.chainservice.entity.response.contract;

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
public class MemberFull {
    private String orgId;
    private Integer memberType;
    private String memberInfo;
    private String memberId;
    private String role;
    private String uid;

    public MemberFull(MemberOuterClass.MemberFull creator) {
        this.orgId = creator.getOrgId();
        this.memberType = creator.getMemberType().getNumber();
        this.memberInfo = creator.getMemberInfo().toStringUtf8();
        this.memberId = creator.getMemberId();
        this.role = creator.getRole();
        this.uid = creator.getUid();
    }
}
