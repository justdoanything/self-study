package prj.yong.modern.config.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import prj.yong.modern.constants.CommonEnumConstant.Gender;

@Component
public class SpringConverter implements Converter<String, Gender> {
    @Override
    public Gender convert(String value) {
        return Gender.valueOf(value.toUpperCase());
    }
}
