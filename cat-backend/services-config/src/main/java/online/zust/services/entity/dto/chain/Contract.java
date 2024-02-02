package online.zust.services.entity.dto.chain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.zust.services.entity.dto.chain.contract.MemberFull;

/**
 * @author qcqcqc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contract {
    private String name;
    private String version;
    private String runtimeType;
    private String status;
    private MemberFull creator;
    private String address;
}
