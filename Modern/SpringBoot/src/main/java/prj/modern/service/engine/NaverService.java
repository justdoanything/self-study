package prj.modern.service.engine;

import prj.modern.model.naver.NaverBlogRequestDTO;
import prj.modern.model.naver.NaverBlogResponseDTO;

public interface NaverService {
    NaverBlogResponseDTO getNaverBlog(NaverBlogRequestDTO naverBlogRequestDTO);
}
