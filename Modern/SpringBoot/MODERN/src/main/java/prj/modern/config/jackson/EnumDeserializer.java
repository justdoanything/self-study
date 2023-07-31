package prj.modern.config.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.Arrays;

public class EnumDeserializer extends StdDeserializer<Enum <? extends Enum>> implements ContextualDeserializer {

    /**
     * TODO
     * Path Variable Enum Converter - Null 커버 되는지 확인
     * Enum valid annotation - Code 값이랑 Null 커버
     * Enum Code - Null 커버
     */
    protected EnumDeserializer(Class<?> vc) {
        super(vc);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Enum<? extends Enum> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
        String input = jsonNode.asText().trim().toUpperCase();
        Class<? extends Enum> enumType = (Class<? extends Enum>) this._valueClass;

        if(ObjectUtils.isEmpty(input))
            return null;

        boolean isPlainEnum = EnumUtils.isValidEnum(enumType, input);
        if(isPlainEnum){
            return Enum.valueOf(enumType, input);
        } else {
            boolean isEnumCode = Arrays.stream(enumType.getMethods()).anyMatch(method -> "value".equals(method.getName()));

            if(isEnumCode){
                Enum mathcEnum = null;
                String enumValue;
                for(Enum constant : enumType.getEnumConstants()){
                    try {
                        enumValue = (String) constant.getClass().getMethod("value").invoke(constant);
                        if(enumValue.equals(input.trim().toUpperCase())){
                            mathcEnum = constant;
                            break;
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

                if(mathcEnum == null)
                    throw new IllegalArgumentException("No enum constant " + enumType.getCanonicalName() + "." +input);

                return Enum.valueOf(enumType,mathcEnum.name());
            } else {
                throw new IllegalArgumentException("No enum constant " + enumType.getCanonicalName() + "." +input);
            }
        }
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext context, BeanProperty beanProperty) {
        return new EnumDeserializer(beanProperty.getType().getRawClass());
    }
}
