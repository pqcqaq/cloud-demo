package online.zust.services.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author qcqcqc
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 8866693990705068083L;
    private Long id;
    private String username;
    private String password;
    private List<String> roles;
    private LocalDateTime createTime;
    private Boolean disabled;
}
