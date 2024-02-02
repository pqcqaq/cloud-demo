package online.zust.services.entity.dto.chain.chainconfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author qcqcqc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrustRootConfig {
    private String orgId;
    private List<String> root;
}
