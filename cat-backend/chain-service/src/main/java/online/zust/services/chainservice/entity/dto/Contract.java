package online.zust.services.chainservice.entity.dto;

import lombok.Data;
import org.chainmaker.pb.common.ContractOuterClass;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author qcqcqc
 */
@Data
public class Contract {
    @NotBlank(message = "合约名称不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9_]{1,30}$", message = "请输入30位以内字母、数字、下划线组合")
    private String contractName;
    @NotBlank(message = "合约版本不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9_.-]{1,30}$", message = "请输入30位以内组合")
    private String contractVersion;
    @NotBlank(message = "合约类型不能为空")
    @Pattern(regexp = "^(WASMER|GASM|EVM|WXVM|DOCKER_GO)$", message = "请输入正确的runtimeType")
    private String runtimeType;

}
