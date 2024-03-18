package rs.saga.obuka.sagashop.integration.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import rs.saga.obuka.sagashop.AbstractIntegrationTest;
import rs.saga.obuka.sagashop.domain.Product;
import rs.saga.obuka.sagashop.dto.product.CreateProductCmd;
import rs.saga.obuka.sagashop.dto.product.ProductInfo;
import rs.saga.obuka.sagashop.dto.product.UpdateProductCmd;
import rs.saga.obuka.sagashop.exception.ServiceException;
import rs.saga.obuka.sagashop.service.ProductService;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class ProductServiceTest extends AbstractIntegrationTest {

    @Autowired
    private ProductService productService;

    @AfterEach
    public void tearDown() {
        productService.findAll().forEach(e -> {
            try {
                productService.delete(e.getId());
            } catch (ServiceException serviceException) {
                serviceException.printStackTrace();
            }
        });
    }
    @Test
    public void saveProduct() throws ServiceException {
        CreateProductCmd cmd = new CreateProductCmd("product", new BigDecimal(6000),"description", 5);
        Product product = productService.save(cmd);

        assertNotNull(product);
        assertNotNull(product.getId());
        assertEquals(cmd.getName(), product.getName());
        assertEquals(cmd.getPrice(), product.getPrice());
        assertEquals(cmd.getDescription(), product.getDescription());
        assertEquals(cmd.getQuantity(), product.getQuantity());
    }

    @Test
    public void updateProduct() throws ServiceException {
        CreateProductCmd cmd = new CreateProductCmd("product", new BigDecimal(6000),"description", 15);
        Product product = productService.save(cmd);

        UpdateProductCmd updateCmd = new UpdateProductCmd(product.getId(), "product", new BigDecimal("6000.00"),"description", 10);
        productService.update(updateCmd);

        ProductInfo updatedProduct = productService.findById(product.getId());


        assertNotNull(updatedProduct);
        assertEquals(updateCmd.getName(), updatedProduct.getName());
        assertEquals(updateCmd.getPrice(), updatedProduct.getPrice());
        assertEquals(updateCmd.getDescription(), updatedProduct.getDescription());
        assertEquals(updateCmd.getQuantity(), updatedProduct.getQuantity());
        assertEquals(updateCmd.getQuantity(), updatedProduct.getQuantity());
    }

    @Test
    public void deleteProduct() throws ServiceException {
        CreateProductCmd cmd = new CreateProductCmd("product", new BigDecimal(6000),"description", 25);
        Product product = productService.save(cmd);

        productService.delete(product.getId());

        ProductInfo deletedProduct = productService.findById(product.getId());

        assertNull(deletedProduct);
    }

    @Test
    public void findOne() throws ServiceException {
        CreateProductCmd cmd = new CreateProductCmd("product", new BigDecimal("6000.00"),"description", 20);
        Product product = productService.save(cmd);

        ProductInfo foundProduct = productService.findById(product.getId());
        assertNotNull(foundProduct);
        assertEquals(product.getName(), foundProduct.getName());
        assertEquals(product.getPrice(), foundProduct.getPrice());
        assertEquals(product.getDescription(), foundProduct.getDescription());
        assertEquals(product.getQuantity(), foundProduct.getQuantity());
    }

    @Test
    public void findAll() throws ServiceException {
        CreateProductCmd cmd = new CreateProductCmd("product", new BigDecimal(6000),"description", 50);
        productService.save(cmd);

        CreateProductCmd cmd2 = new CreateProductCmd("product2", new BigDecimal(6000),"description", 40);
        productService.save(cmd2);

        assertEquals(2, productService.findAll().size());
    }
}
