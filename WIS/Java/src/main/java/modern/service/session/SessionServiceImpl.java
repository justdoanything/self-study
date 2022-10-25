package modern.service.session;

import lombok.RequiredArgsConstructor;
import modern.model.spring.SpringRequestVO;
import modern.model.spring.SpringResponseVO;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
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
