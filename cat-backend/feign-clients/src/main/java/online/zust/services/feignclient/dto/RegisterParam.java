package online.zust.services.feignclient.dto;

import lombok.Data;

/**
 * @author qcqcqc
 */
@Data
public class RegisterParam {
    private String username;
    private String password;
    private String email;
}
