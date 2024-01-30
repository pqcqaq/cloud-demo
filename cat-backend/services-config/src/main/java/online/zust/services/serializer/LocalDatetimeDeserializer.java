package online.zust.services.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import online.zust.common.utils.TimeFormatterUtils;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author pqcmm
 */
public class LocalDatetimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctx)
            throws IOException {
        String str = p.getText();
        return LocalDateTime.parse(str, TimeFormatterUtils.SDF_1);
    }
}
