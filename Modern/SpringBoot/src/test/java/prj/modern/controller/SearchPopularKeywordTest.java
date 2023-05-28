package prj.modern.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import prj.modern.constant.SearchConstant;
import prj.modern.model.search.SearchPopularKeywordRequestVO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static prj.modern.constant.StatusCodeMessageConstant.BAD_REQUEST;
import static prj.modern.constant.StatusCodeMessageConstant.SUCCESS;

@SpringBootTest
@AutoConfigureMockMvc
public class SearchPopularKeywordTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("성공_적절한 파라미터를 사용해서 인기 검색어 검색에 성공한다.")
    public void happy_get_popular_keywords_case1() throws Exception {
        //given
        SearchPopularKeywordRequestVO request = SearchPopularKeywordRequestVO.builder()
                .period(SearchConstant.PopularKeywordPeriod.ALL.name())
                .size(8)
                .build();

        //when

        //then
        mockMvc.perform(get("/v1/popular-keyword")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("period", request.getPeriod())
                        .param("size", String.valueOf(request.getSize())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SUCCESS))
                .andDo(print());
    }

    @Test
    @DisplayName("실패_파라미터_size의 범위를 벗어난 파라미터를 사용해서 실패한다.")
    public void bad_parameter_invalid_size() throws Exception {
        //given
        SearchPopularKeywordRequestVO request = SearchPopularKeywordRequestVO.builder()
                .period(SearchConstant.PopularKeywordPeriod.ALL.name())
                .size(11)
                .build();

        //when

        //then
        mockMvc.perform(get("/v1/popular-keyword")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("period", request.getPeriod())
                        .param("size", String.valueOf(request.getSize())))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value(BAD_REQUEST))
                .andDo(print());
    }

    @Test
    @DisplayName("실패_파라미터_period의 범위를 벗어난 파라미터를 사용해서 실패한다.")
    public void bad_parameter_invalid_period() throws Exception {
        //given
        SearchPopularKeywordRequestVO request = SearchPopularKeywordRequestVO.builder()
                .period("2MONTH")
                .size(8)
                .build();

        //when

        //then
        mockMvc.perform(get("/v1/popular-keyword")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("period", request.getPeriod())
                        .param("size", String.valueOf(request.getSize())))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value(BAD_REQUEST))
                .andDo(print());
    }
}


