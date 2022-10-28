package modern.config.converter;

import modern.constants.CommonEnumConstants.Gender;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SpringConverter implements Converter<String, Gender> {
    @Override
    public Gender convert(String value) {
        return Gender.valueOf(value.toUpperCase());
    }
}
