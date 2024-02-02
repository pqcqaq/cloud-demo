package online.zust.services.entity.dto.chain.contract;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
