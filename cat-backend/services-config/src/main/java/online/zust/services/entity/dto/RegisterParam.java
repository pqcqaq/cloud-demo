package online.zust.services.entity.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author qcqcqc
 */
@Data
public class RegisterParam {
    @NotBlank(message = "用户名不能为空")
    @Length(min = 6, max = 20, message = "用户名长度必须在6-20之间")
    @Pattern(regexp = "^[a-zA-Z0-9_-]{6,20}$", message = "用户名为6-20位字母数字或下划线")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 20, message = "密码长度必须在6-20之间")
    @Pattern(regexp = "^[a-zA-Z0-9_-]{6,20}$", message = "密码为6-20位字母数字或下划线")
    private String password;

    @NotBlank(message = "邮箱不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$", message = "邮箱格式不正确")
    private String email;
}
