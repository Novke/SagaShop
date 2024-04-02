package rs.saga.obuka.sagashop;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SagashopApplicationTest.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SuppressWarnings("unused")
public class AbstractUnitServiceTest {
}
