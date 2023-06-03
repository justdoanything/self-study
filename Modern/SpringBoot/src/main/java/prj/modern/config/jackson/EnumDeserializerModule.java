package prj.modern.config.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.type.ClassKey;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class EnumDeserializerModule {
    @Bean
    public Module enumModule() {
        return new Module() {
            @Override
            public String getModuleName() {
                return "enum";
            }

            @Override
            public Version version() {
                return Version.unknownVersion();
            }

            @Override
            public void setupModule(SetupContext context) {
                context.addDeserializers(
                        new Deserializers.Base() {
                            final Map<ClassKey, JsonDeserializer<?>> cache = new ConcurrentHashMap<>();

                            @Override
                            public JsonDeserializer<?> findEnumDeserializer(
                                    Class<?> type,
                                    DeserializationConfig config,
                                    BeanDescription beanDesc) {
                                if (Enum.class.isAssignableFrom(type)) {
                                    JsonDeserializer<?> enmDeserializer = new EnumDeserializer(type);
                                    addDeserializer(type, enmDeserializer);
                                    return enmDeserializer;
                                }
                                return null;
                            }

                            @Override
                            public boolean hasDeserializerFor(DeserializationConfig config, Class<?> type) {
                                return cache.containsKey(new ClassKey(type));
                            }

                            public void addDeserializer(Class<?> type, JsonDeserializer<?> deserializer) {
                                ClassKey classKey = new ClassKey(type);
                                cache.put(classKey, deserializer);
                            }
                        });
            }
        };
    }
}
