package modern.util;

import lombok.experimental.UtilityClass;
import modern.constants.SessionConstant;
import modern.model.session.SessionVO;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@UtilityClass
public class SessionUtil {
    public static void setSessionContext(String key, Object value) {
        RequestContextHolder.getRequestAttributes().setAttribute(key, value, RequestAttributes.SCOPE_REQUEST);
    }

    public static Object getSessionContext(String key) {
        return RequestContextHolder.getRequestAttributes().getAttribute(key, RequestAttributes.SCOPE_REQUEST);
    }

    public static void setSession(Object value) {
        setSessionContext(SessionConstant.sessionKey, value);
    }
    public static SessionVO getSessionVO() {
        return (SessionVO) getSessionContext(SessionConstant.sessionKey);
    }
}
