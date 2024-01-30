package online.zust.services.authservice.entity.dto;

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
