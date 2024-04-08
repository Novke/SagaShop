package rs.saga.obuka.sagashop.unit.rest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import rs.saga.obuka.sagashop.AbstractUnitRestTest;
import rs.saga.obuka.sagashop.exception.BudgetExceededException;
import rs.saga.obuka.sagashop.rest.ShoppingCartRest;
import rs.saga.obuka.sagashop.service.ShoppingCartService;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ShoppingCartRest.class)
public class ShoppingCartRestTest extends AbstractUnitRestTest {

    @MockBean
    private ShoppingCartService shoppingCartService;

    @Test
    public void testCheckout() throws Exception {
        doThrow(BudgetExceededException.class).when(shoppingCartService).checkout(anyLong());

        mockMvc.perform(post("/cart/checkout/2"))
                .andExpect(status().is4xxClientError());
    }
}
