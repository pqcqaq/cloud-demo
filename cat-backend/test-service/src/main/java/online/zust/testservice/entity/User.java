package online.zust.testservice.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.zust.common.serializer.LocalDatetimeDeserializer;

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
    private static final long serialVersionUID = -3893325794150948776L;
    private Long id;
    private String username;
    private String password;
    private List<String> roles;
    @JsonDeserialize(using = LocalDatetimeDeserializer.class)
    private LocalDateTime createTime;
    private Boolean disabled;
}
