package prj.yong.modern.service.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prj.yong.modern.model.spring.SpringRequestVO;
import prj.yong.modern.model.spring.SpringResponseVO;

@Service
@RequiredArgsConstructor
public class SpringServiceImpl implements SpringService {
    @Override
    public SpringResponseVO checkHealth(SpringRequestVO request) {
        System.out.println("Enter checkHealth : " + request.toString());
        return SpringResponseVO.builder().message("name : " + request.getName()).build();
    }

    @Override
    public SpringResponseVO checkHealth(int id) {
        System.out.println("Enter checkHealth : " + id);
        return SpringResponseVO.builder()
                .message("GET /health?" + id + " ====> This is health check!")
                .build();
    }
}
