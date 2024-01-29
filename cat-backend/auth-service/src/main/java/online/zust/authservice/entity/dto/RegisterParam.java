package online.zust.authservice.entity.dto;

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
