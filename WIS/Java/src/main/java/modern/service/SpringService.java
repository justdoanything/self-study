package modern.service;

import modern.model.SpringRequestVO;
import modern.model.SpringResponseVO;

public interface SpringService {
    SpringResponseVO checkHealth(SpringRequestVO request);

    SpringResponseVO checkHealth(int id);
}
