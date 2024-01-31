package online.zust.services.chainservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chainmaker.pb.config.ChainConfigOuterClass;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qcqcqc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChainVmSupport {
    private List<String> supportList;
    private String addrType;

    public ChainVmSupport(ChainConfigOuterClass.Vm vm) {
        this.supportList = new ArrayList<>();
        int supportListCount = vm.getSupportListCount();
        for (int i = 0; i < supportListCount; i++) {
            this.supportList.add(vm.getSupportList(i));
        }
        this.addrType = vm.getAddrType().name();
    }
}
