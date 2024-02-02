package online.zust.services.entity.dto.chain.chainconfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
}
