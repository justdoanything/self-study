package prj.searching.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import prj.searching.model.search.SearchNaverBlogRequestVO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static prj.searching.constant.StatusCodeMessageConstant.BAD_REQUEST;
import static prj.searching.constant.StatusCodeMessageConstant.SUCCESS;

@SpringBootTest
@AutoConfigureMockMvc
public class SearchNaverBlogTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("성공_적절한 파라미터를 사용해서 네이버 블로그 검색에 성공한다.")
    public void happy_get_naver_blogs() throws Exception {
        //given
        SearchNaverBlogRequestVO request = SearchNaverBlogRequestVO.builder()
                .keyword("날씨")
                .build();

        //then
        mockMvc.perform(get("/v1/naver/blogs")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("keyword", request.getKeyword()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SUCCESS))
                .andDo(print());
    }

    @Test
    @DisplayName("실패_파라미터_pageSize 범위를 벗어난 파라미터를 사용해서 실패한다.")
    public void bad_parameter_invalid_pageSize() throws Exception {
        //given
        SearchNaverBlogRequestVO request = SearchNaverBlogRequestVO.builder()
                .keyword("날씨")
                .pageSize(101)
                .build();

        //then
        mockMvc.perform(get("/v1/naver/blogs")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("keyword", request.getKeyword())
                        .param("sortType", request.getSortType())
                        .param("start", String.valueOf(request.getStart()))
                        .param("pageSize", String.valueOf(request.getPageSize()))
                        .param("pageSize", String.valueOf(request.getPageSize())))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value(BAD_REQUEST))
                .andDo(print());

    }

    @Test
    @DisplayName("실패_파라미터_sortType 범위를 벗어난 파라미터를 사용해서 실패한다.")
    public void bad_parameter_invalid_sortType() throws Exception {
        //given
        SearchNaverBlogRequestVO request = SearchNaverBlogRequestVO.builder()
                .keyword("날씨")
                .sortType("RECENT")
                .build();

        //then
        mockMvc.perform(get("/v1/naver/blogs")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("keyword", request.getKeyword())
                        .param("sortType", request.getSortType())
                        .param("start", String.valueOf(request.getStart()))
                        .param("pageSize", String.valueOf(request.getPageSize()))
                        .param("pageSize", String.valueOf(request.getPageSize())))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value(BAD_REQUEST))
                .andDo(print());

    }

    @Test
    @DisplayName("실패_파라미터_start 범위를 벗어난 파라미터를 사용해서 실패한다.")
    public void bad_parameter_invalid_start() throws Exception {
        //given
        SearchNaverBlogRequestVO request = SearchNaverBlogRequestVO.builder()
                .keyword("날씨")
                .start(101)
                .build();

        //then
        mockMvc.perform(get("/v1/naver/blogs")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("keyword", request.getKeyword())
                        .param("sortType", request.getSortType())
                        .param("start", String.valueOf(request.getStart()))
                        .param("pageSize", String.valueOf(request.getPageSize()))
                        .param("pageSize", String.valueOf(request.getPageSize())))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value(BAD_REQUEST))
                .andDo(print());
    }

    @Test
    @DisplayName("실패_파라미터_keyword 범위를 벗어난 파라미터를 사용해서 실패한다.")
    public void bad_parameter_invalid_keyword() throws Exception {
        //given
        SearchNaverBlogRequestVO request = SearchNaverBlogRequestVO.builder()
                .keyword("")
                .build();

        //then
        mockMvc.perform(get("/v1/naver/blogs")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("keyword", request.getKeyword())
                        .param("sortType", request.getSortType())
                        .param("start", String.valueOf(request.getStart()))
                        .param("pageSize", String.valueOf(request.getPageSize()))
                        .param("pageSize", String.valueOf(request.getPageSize())))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value(BAD_REQUEST))
                .andDo(print());
    }
}