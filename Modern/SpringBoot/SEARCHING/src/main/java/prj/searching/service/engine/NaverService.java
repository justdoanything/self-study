package prj.searching.service.engine;

import prj.searching.model.naver.NaverBlogRequestDTO;
import prj.searching.model.naver.NaverBlogResponseDTO;

public interface NaverService {
    NaverBlogResponseDTO getNaverBlog(NaverBlogRequestDTO naverBlogRequestDTO);
}
