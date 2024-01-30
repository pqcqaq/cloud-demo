package online.zust.services.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import online.zust.common.utils.TimeFormatterUtils;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author pqcmm
 */
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider provider)
            throws IOException {
        String s = value.format(TimeFormatterUtils.SDF_1);
        gen.writeString(s);
    }
}
