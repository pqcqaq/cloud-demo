package online.zust.services.chainservice.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.zust.services.chainservice.entity.response.contract.MemberFull;
import org.chainmaker.pb.common.ContractOuterClass;

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

    public Contract(ContractOuterClass.Contract t) {
        this.name = t.getName();
        this.version = t.getVersion();
        this.runtimeType = t.getRuntimeType().name();
        this.status = t.getStatus().name();
        this.creator = new MemberFull(t.getCreator());
        this.address = t.getAddress();
    }
}
