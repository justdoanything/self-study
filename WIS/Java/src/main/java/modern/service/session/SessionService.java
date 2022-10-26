package modern.service.session;

import modern.model.session.SessionVO;
import modern.model.spring.SpringRequestVO;
import modern.model.spring.SpringResponseVO;

public interface SessionService {
    SessionVO getSession(String sessionId);
}
