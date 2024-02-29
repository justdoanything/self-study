package yong;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
class PostTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("성공_올바른 ContentsTypeCode의 name 값을 사용했을 때 성공한다.")
    public void success_enum_name_case() throws Exception {
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
    @DisplayName("실패_범위에서 벗어난 ContentsTypeCode의 name 값을 사용했을 때 실패한다.")
    public void fail_out_of_enum_name_case() throws Exception {
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
    @DisplayName("성공_올바른 ContentsTypeCode의 code 값을 사용했을 때 성공한다.")
    public void success_enum_code_case() throws Exception {
        //given
        Map<String, String> request = Map.of(
                "title", "title",
                "contents", "contents",
                "contentsTypeCode", "005"
        );

        //then
        mockMvc.perform(post("/v1/post/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("실패_범위에서 벗어난 ContentsTypeCode의 code 값을 사용했을 때 실패한다.")
    public void fail_out_of_enum_code_case() throws Exception {
        //given
        Map<String, String> request = Map.of(
                "title", "title",
                "contents", "contents",
                "contentsTypeCode", "006"
        );

        //then
        mockMvc.perform(post("/v1/post/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("실패_올바른 ContentsTypeCode의 소문자 값을 보냈을 때 성공한다.")
    public void success_enum_name_lower_case() throws Exception {
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
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("실패_ContentsTypeCode의 null 값을 사용했을 때 실패한다.")
    public void fail_enum_null_case() throws Exception {
        //given
        Map<String, String> request = Map.of(
                "title", "title",
                "contents", "contents"
        );

        //then
        mockMvc.perform(post("/v1/post/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("실패_ContentsTypeCode의 index 값을 사용했을 때 실패한다.")
    public void happy_enum_index_case() throws Exception {
        //given
        Map<String, String> request = Map.of(
                "title", "title",
                "contents", "contents",
                "contentsTypeCode", "0"
        );

        //then
        mockMvc.perform(post("/v1/post/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("성공_범위에서 벗어난 ContentsTypeCode을 사용했을 때 응답에 에러 문구가 있다.")
    public void happy_enum_error_message_case() throws Exception {
        //given
        Map<String, String> request = Map.of(
                "title", "title",
                "contents", "contents",
                "contentsTypeCode", "HOT"
        );

        //when
        MvcResult result = mockMvc.perform(post("/v1/post/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andReturn();

        //then
        Assertions.assertEquals(result.getResponse().getStatus(), HttpStatus.BAD_REQUEST.value());
        Assertions.assertTrue(result.getResponse().getContentAsString().length() > 0);
    }
}

