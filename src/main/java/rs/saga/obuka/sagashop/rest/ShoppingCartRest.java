package rs.saga.obuka.sagashop.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.saga.obuka.sagashop.exception.BudgetExceededException;
import rs.saga.obuka.sagashop.exception.DAOException;
import rs.saga.obuka.sagashop.exception.ServiceException;
import rs.saga.obuka.sagashop.service.ShoppingCartService;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class ShoppingCartRest {

    private final ShoppingCartService shoppingCartService;

    @PostMapping("/checkout/{cartId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public ResponseEntity<?> checkout(@PathVariable Long cartId) throws DAOException, ServiceException {
        try {
            shoppingCartService.checkout(cartId);
        } catch (BudgetExceededException ex){
            System.err.println("desio se exception");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Budget exceeded for this transaction.");

        }
        return null;
    }
}
