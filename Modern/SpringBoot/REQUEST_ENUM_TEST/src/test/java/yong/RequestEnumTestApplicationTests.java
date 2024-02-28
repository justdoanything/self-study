package yong;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import yong.constants.ContentsTypeCode;
import yong.model.RequestVO;

import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
class RequestEnumTestApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("성공_올바른 ContentsTypeCode을 사용해서 성공한다.")
    public void happy_basic_case() throws Exception {
        //given
        Map<String, String> request = Map.of(
                "title", "title",
                "contents", "contents",
                "contentsTypeCode", "FEED"
        );

        //then
        mockMvc.perform(post("/v1/post/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("성공_ContentsTypeCode의 null 값을 허용한다.")
    public void happy_basic_null_case() throws Exception {
        //given
        Map<String, String> request = Map.of(
                "title", "title",
                "contents", "contents"
        );

        //then
        mockMvc.perform(post("/v1/post/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("실패_범위에서 벗어난 ContentsTypeCode을 사용해서 실패한다.")
    public void fail_basic_case() throws Exception {
        //given
        Map<String, String> request = Map.of(
                "title", "title",
                "contents", "contents",
                "contentsTypeCode", "YOUTUBE"
        );

        //then
        mockMvc.perform(post("/v1/post/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("실패_소문자 값을 보냈을 때 실패합니다.")
    public void fail_basic_lower_case() throws Exception {
        //given
        Map<String, String> request = Map.of(
                "title", "title",
                "contents", "contents",
                "contentsTypeCode", "feed"
        );

        //then
        mockMvc.perform(post("/v1/post/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(print());
    }
}

