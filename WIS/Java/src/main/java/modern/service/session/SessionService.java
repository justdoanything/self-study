package modern.service.session;

import modern.model.spring.SpringRequestVO;
import modern.model.spring.SpringResponseVO;

public interface SessionService {
    SpringResponseVO checkHealth(SpringRequestVO request);

    SpringResponseVO checkHealth(int id);
}
