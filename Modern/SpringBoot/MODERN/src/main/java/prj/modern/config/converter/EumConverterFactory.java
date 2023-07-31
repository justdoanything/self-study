package prj.modern.config.converter;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class EumConverterFactory implements ConverterFactory<String, Enum> {

    @Override
    public <T extends Enum> Converter<String, T> getConverter(Class<T> enumType) {
        return new EnumConverter(getEnumType(enumType));
    }

    private class EnumConverter<T extends Enum> implements Converter<String, T> {

        private final Class<T> enumType;

        private EnumConverter(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(String input) {
            if(input.isEmpty() || input == null)
                return null;

            input = input.trim().toUpperCase();

            boolean isPlainEnum = EnumUtils.isValidEnum(enumType, input);
            if(isPlainEnum){
                return (T) Enum.valueOf(this.enumType, input);
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

                    return (T) Enum.valueOf(enumType,mathcEnum.name());
                } else {
                    throw new IllegalArgumentException("No enum constant " + enumType.getCanonicalName() + "." +input);
                }
            }
        }
    }

    private Class<?> getEnumType(Class classType) {
        Class<?> enumType = classType;
        while(enumType != null && !enumType.isEnum()){
            enumType = enumType.getSuperclass();
        }
        if(enumType == null) {
            throw new IllegalArgumentException("This type " + enumType.getName() + " is not an enum type.");
        }
        return enumType;
    }
}
