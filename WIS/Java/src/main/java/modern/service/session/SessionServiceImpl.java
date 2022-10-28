package modern.service.session;

import lombok.RequiredArgsConstructor;
import modern.model.session.SessionVO;
import modern.repository.SessionRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;

    @Override
    public SessionVO getSession(String sessionId) {
        return sessionRepository.getSession(sessionId);
    }
}
