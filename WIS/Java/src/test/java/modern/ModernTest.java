package modern;

import java.util.Arrays;
import java.util.List;
import modern.controller.SpringController;
import modern.model.spring.TestVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ModernTest {

    @Autowired
    SpringController springController;

    @Test
    public void modernTest() throws Exception {
        List<Integer> required = Arrays.asList(1, 2, 6, 9, 11);
        List<TestVO> request = Arrays.asList(
                TestVO.builder().termId(1).agree("Y").build(),
                TestVO.builder().termId(2).agree("Y").build(),
                TestVO.builder().termId(3).agree("Y").build(),
                TestVO.builder().termId(4).agree("Y").build(),
                TestVO.builder().termId(5).agree("Y").build(),
                TestVO.builder().termId(6).agree("Y").build(),
                TestVO.builder().termId(9).agree("Y").build(),
                TestVO.builder().termId(11).agree("Y").build());

        required.forEach(id -> {
            request.stream()
                    .filter(vo -> vo.getTermId() == id)
                    .findFirst()
                    .ifPresentOrElse(
                            vo -> {
                                if (!vo.getAgree().equals("Y")) {
                                    System.out.println("필수 항목을 반대했습니다 : " + id);
                                }
                            },
                            () -> {
                                System.out.println("필수 항목을 누락했습니다 : " + id);
                            });
        });
    }
}
