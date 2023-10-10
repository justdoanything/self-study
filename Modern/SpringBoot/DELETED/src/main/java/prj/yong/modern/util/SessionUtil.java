package prj.yong.modern.util;

import lombok.experimental.UtilityClass;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import prj.yong.modern.constants.SessionConstant;
import prj.yong.modern.model.session.SessionVO;

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
