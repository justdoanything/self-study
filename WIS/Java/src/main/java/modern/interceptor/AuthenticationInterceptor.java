package modern.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modern.constants.HttpHeaderConstants;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final String HTTP_METHOD_OPTIONS = "OPTIONS";

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String sessionId = request.getHeader(HttpHeaderConstants.SESSION_ID);
        if(HTTP_METHOD_OPTIONS.equals(request.getMethod()))
            return true;
        return false;
    }
}
