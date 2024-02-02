package online.zust.services.authservice.entity.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.zust.services.serializer.LocalDateTimeSerializer;
import online.zust.services.serializer.LocalDatetimeDeserializer;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author qcqcqc
 */
@TableName(value = "user_info", autoResultMap = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1300711120553327264L;
    private Long id;
    private String username;
    private String password;
    @TableField(value = "roles", typeHandler = JacksonTypeHandler.class)
    private List<String> roles;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDatetimeDeserializer.class)
    private LocalDateTime createTime;
    private Boolean disabled;
}
