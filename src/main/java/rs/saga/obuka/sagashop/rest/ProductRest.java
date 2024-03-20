package rs.saga.obuka.sagashop.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rs.saga.obuka.sagashop.domain.Product;
import rs.saga.obuka.sagashop.dto.product.CreateProductCmd;
import rs.saga.obuka.sagashop.dto.product.ProductInfo;
import rs.saga.obuka.sagashop.dto.product.ProductResult;
import rs.saga.obuka.sagashop.dto.product.UpdateProductCmd;
import rs.saga.obuka.sagashop.exception.ServiceException;
import rs.saga.obuka.sagashop.service.ProductService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductRest {

    private final ProductService productService;

    @PostMapping("/save")
    public Product save(@RequestBody @Valid CreateProductCmd cmd) throws ServiceException {
        return productService.save(cmd);
    }

    @GetMapping("/all")
    @ResponseBody
    public List<ProductResult> findAll() {
        return productService.findAll();
    }

    @GetMapping("/id/{id}")
    public ProductInfo findById(@PathVariable long id) {
        return productService.findById(id);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid UpdateProductCmd cmd) throws ServiceException {
        productService.update(cmd);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) throws ServiceException {
        productService.delete(id);
    }

    @GetMapping("/find")
    public List<ProductResult> find(@RequestParam(required = false) String name,
                                    @RequestParam(required = false) Integer quantity,
                                    @RequestParam(required = false) Double price,
                                    @RequestParam(required = false) String category) {
        return productService.findProducts(name, quantity, price, category);
    }

}
