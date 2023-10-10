package prj.yong.modern.service.session;

import prj.yong.modern.model.session.SessionVO;

public interface SessionService {
    SessionVO getSession(String sessionId);
}
