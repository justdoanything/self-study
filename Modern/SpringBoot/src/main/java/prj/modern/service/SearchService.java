package prj.modern.service;

import prj.modern.model.pagination.PaginationResponseVO;
import prj.modern.model.search.SearchKakaoBlogRequestVO;
import prj.modern.model.search.SearchNaverBlogRequestVO;
import prj.modern.model.search.SearchPopularKeywordRequestVO;
import prj.modern.model.search.SearchPopularKeywordResponseVO;

import java.util.List;

public interface SearchService {
    PaginationResponseVO getKakaoBlogs(SearchKakaoBlogRequestVO kakaoBlogRequestVO);

    PaginationResponseVO getNaverBlogs(SearchNaverBlogRequestVO naverBlogRequestVO);

    List<SearchPopularKeywordResponseVO> getPopularKeywords(SearchPopularKeywordRequestVO searchPopularKeywordRequestVO);
}
