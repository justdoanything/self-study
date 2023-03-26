package prj.modern.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prj.modern.model.common.CommonResponseVO;
import prj.modern.model.search.SearchKakaoBlogRequestVO;
import prj.modern.model.search.SearchNaverBlogRequestVO;
import prj.modern.model.search.SearchPopularKeywordRequestVO;
import prj.modern.service.SearchService;
import prj.modern.util.ResponseUtil;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1")
@Api(tags = {"Search"})
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @ApiOperation(value = "카카오 블로그 검색", httpMethod = "GET", notes = "카카오 블로그 사이트를 블로그를 검색한다. 카카오 API가 작동하지 않을 시 네이버 API를 대체하여 사용한다.")
    @GetMapping(path = "/kakao/blogs", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponseVO> getKakaoBlogs(@Valid SearchKakaoBlogRequestVO searchKakaoBlogRequestVO) {
        return ResponseUtil.createSuccessResponse(searchService.getKakaoBlogs(searchKakaoBlogRequestVO));
    }

    @ApiOperation(value = "네이버 블로그 검색", httpMethod = "GET", notes = "네이버 블로그 사이트를 블로그를 검색한다.")
    @GetMapping(path = "/naver/blogs", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponseVO> getNaverBlogs(@Valid SearchNaverBlogRequestVO naverBlogRequestVO) {
        return ResponseUtil.createSuccessResponse(searchService.getNaverBlogs(naverBlogRequestVO));
    }

    @ApiOperation(value = "인기 키워드 검색", httpMethod = "GET", notes = "특정 기간 안에 있는 인기 검색어 목록을 검색한다.")
    @GetMapping(path = "/popular-keyword", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponseVO> getPopularKeywords(@Valid SearchPopularKeywordRequestVO searchPopularKeywordRequestVO) {
        return ResponseUtil.createSuccessResponse(searchService.getPopularKeywords(searchPopularKeywordRequestVO));
    }

}