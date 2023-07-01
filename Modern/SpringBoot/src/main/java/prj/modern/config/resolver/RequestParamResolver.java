package prj.modern.config.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.Conventions;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestParamResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isEnum();
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Map<String, String> requestParameters = webRequest.getParameterMap().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue()[0]));

        if(requestParameters.size() == 0){
            HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
            ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest(httpServletRequest);
            throw new HttpMessageNotReadableException("Required request parameter is missing :: " + parameter.getExecutable().toGenericString(), servletServerHttpRequest);
        }

        Object resolved = new ObjectMapper().convertValue(requestParameters, parameter.getParameterType());
        if(parameter.hasParameterAnnotation(Valid.class)){
            String parameterName = Conventions.getVariableNameForParameter(parameter);
            WebDataBinder webDataBinder = binderFactory.createBinder(webRequest, resolved, parameterName);
            if(webDataBinder.getTarget() != null){
                webDataBinder.validate();
                BindingResult bindingResult = webDataBinder.getBindingResult();
                if(bindingResult.getErrorCount() > 0)
                    throw new MethodArgumentNotValidException(parameter, bindingResult);
            }
        }

        return resolved;
    }
}
