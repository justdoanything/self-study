package prj.searching.service;

import prj.searching.model.pagination.PaginationResponseVO;
import prj.searching.model.search.SearchKakaoBlogRequestVO;
import prj.searching.model.search.SearchNaverBlogRequestVO;
import prj.searching.model.search.SearchPopularKeywordRequestVO;
import prj.searching.model.search.SearchPopularKeywordResponseVO;

import java.util.List;

public interface SearchService {
    PaginationResponseVO getKakaoBlogs(SearchKakaoBlogRequestVO kakaoBlogRequestVO);

    PaginationResponseVO getNaverBlogs(SearchNaverBlogRequestVO naverBlogRequestVO);

    List<SearchPopularKeywordResponseVO> getPopularKeywords(SearchPopularKeywordRequestVO searchPopularKeywordRequestVO);
}
