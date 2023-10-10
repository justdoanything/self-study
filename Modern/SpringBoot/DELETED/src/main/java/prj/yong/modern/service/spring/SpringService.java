package prj.yong.modern.service.spring;

import prj.yong.modern.model.spring.SpringRequestVO;
import prj.yong.modern.model.spring.SpringResponseVO;

public interface SpringService {
    SpringResponseVO checkHealth(SpringRequestVO request);

    SpringResponseVO checkHealth(int id);
}
