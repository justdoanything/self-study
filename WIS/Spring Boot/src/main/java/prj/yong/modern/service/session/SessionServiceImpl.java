package prj.yong.modern.service.session;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prj.yong.modern.model.session.SessionVO;
import prj.yong.modern.repository.SessionRepository;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;

    @Override
    public SessionVO getSession(String sessionId) {
        return sessionRepository.getSession(sessionId);
    }
}
