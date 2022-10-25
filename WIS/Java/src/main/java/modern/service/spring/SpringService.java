package modern.service.spring;

import modern.model.spring.SpringRequestVO;
import modern.model.spring.SpringResponseVO;

public interface SpringService {
    SpringResponseVO checkHealth(SpringRequestVO request);

    SpringResponseVO checkHealth(int id);
}
