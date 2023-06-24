package prj.modern.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EnumTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void doTestEnumDeserializer() throws Exception {
        mockMvc.perform(get("/v1/test/enum/deserializer")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("searchEngineTypeCode", "KAKAO"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void doTestEnumDeserializer_code() throws Exception {
        mockMvc.perform(get("/v1/test/enum/deserializer")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("searchEngineTypeCode", "001"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
