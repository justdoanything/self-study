package prj.modern.config.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import prj.modern.constant.SearchEngineTypeCode;

@Component
public class SearchEngineConverter implements Converter<String, SearchEngineTypeCode> {
    @Override
    public SearchEngineTypeCode convert(String value) {
        return SearchEngineTypeCode.valueOf(value.toUpperCase());
    }
}
