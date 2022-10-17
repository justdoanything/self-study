package modern;

import modern.constants.CommonEnumConstants;
import modern.model.common.CommonVO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ModernTest {

    @Test
    public void modernTest() {
        CommonVO commonVO = CommonVO.builder().message("??Common Message 입니다.")
                .build();

        System.out.println(commonVO.getMessage());

        commonVO.setGender(CommonEnumConstants.Gender.MAN);
    }

}
