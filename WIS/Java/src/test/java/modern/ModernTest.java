package modern;

import modern.controller.SpringController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ModernTest {

    @Autowired
    SpringController springController;

    @Test
    public void modernTest() {
        springController.healthCheck(1);
    }
}
