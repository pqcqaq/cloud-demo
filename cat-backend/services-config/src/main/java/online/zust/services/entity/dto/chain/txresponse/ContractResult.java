package online.zust.services.entity.dto.chain.txresponse;

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
public class ContractResult {
    private String result;
    private String message;
    private List<ContractEvent> contractEvent;
    private Long gasUsed;
}
