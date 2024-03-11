package rs.saga.obuka.sagashop.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rs.saga.obuka.sagashop.domain.PayPalAccount;
import rs.saga.obuka.sagashop.dto.paypal.CreatePayPalAccountCmd;
import rs.saga.obuka.sagashop.dto.paypal.PayPalAccountInfo;
import rs.saga.obuka.sagashop.dto.paypal.PayPalAccountResult;
import rs.saga.obuka.sagashop.dto.paypal.UpdatePayPalAccountCmd;
import rs.saga.obuka.sagashop.exception.ServiceException;
import rs.saga.obuka.sagashop.service.PayPalAccountService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/paypal")
@RequiredArgsConstructor
public class PayPalAccountRest {

    private final PayPalAccountService payPalAccountService;


    //TODO: jel ovde fali da se izabere korisnik ?
    @PostMapping("/save")
    public PayPalAccount save(@RequestBody @Valid CreatePayPalAccountCmd cmd) throws ServiceException {
        return payPalAccountService.save(cmd);
    }

    @GetMapping("/all")
    @ResponseBody
    public List<PayPalAccountResult> findAll() {
        return payPalAccountService.findAll();
    }

    @GetMapping("/id/{id}")
    public PayPalAccountInfo findById(@PathVariable long id) {
        return payPalAccountService.findById(id);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid UpdatePayPalAccountCmd cmd) throws ServiceException {
        payPalAccountService.update(cmd);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) throws ServiceException {
        payPalAccountService.delete(id);
    }

}
