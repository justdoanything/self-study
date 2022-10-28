package modern.service.session;

import modern.model.session.SessionVO;

public interface SessionService {
    SessionVO getSession(String sessionId);
}
