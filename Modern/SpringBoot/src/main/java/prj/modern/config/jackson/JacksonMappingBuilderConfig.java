package prj.modern.config.jackson;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;

public class JacksonMappingBuilderConfig implements Jackson2ObjectMapperBuilderCustomizer {

    @Override
    public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
        jacksonObjectMapperBuilder
                .featuresToEnable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .timeZone(TimeZone.getDefault())
                .locale(Locale.getDefault())
                .simpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .serializers(
                        new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyyMMdd")),
                        new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss"))
                )
                .deserializers(
                        new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyyMMdd")),
                        new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss")),
                        new EnumDeserializer(Enum.class)
                );
    }
}
