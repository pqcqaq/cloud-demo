package online.zust.services.entity.dto.chain.blockinfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
}
