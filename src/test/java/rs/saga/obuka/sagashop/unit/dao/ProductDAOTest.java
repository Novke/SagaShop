package rs.saga.obuka.sagashop.unit.dao;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import rs.saga.obuka.sagashop.AbstractDAOTest;
import rs.saga.obuka.sagashop.dao.CategoryDAO;
import rs.saga.obuka.sagashop.dao.ProductDAO;
import rs.saga.obuka.sagashop.domain.Category;
import rs.saga.obuka.sagashop.domain.Product;
import rs.saga.obuka.sagashop.dto.product.ProductResult;
import rs.saga.obuka.sagashop.exception.DAOException;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class ProductDAOTest extends AbstractDAOTest {

    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private CategoryDAO categoryDAO;

    @BeforeEach
    public void setUp() throws DAOException {
        Category kola = new Category("Kola", "Automobili", null);
        Category sok = new Category("Sok", "Pice", null);
        Category slatkisi = new Category("Slatkisi", "Sokovi i Cokoladice", null);
        Product audi = new Product("Audi A5", new BigDecimal(3000000), 3, null, null);
        audi.addCategory(kola);
        Product bmw = new Product("BMW 5", new BigDecimal(350000), 1, null, null);
        bmw.addCategory(kola);
        Product cocaCola = new Product("Coca Cola", new BigDecimal(100), 500, null, null);
        cocaCola.addCategory(sok);
        cocaCola.addCategory(slatkisi);
        Product fanta = new Product("Fanta", new BigDecimal(80), 210, null, null);
        fanta.addCategory(sok);
        fanta.addCategory(slatkisi);
        Product milka = new Product("Milka", new BigDecimal(100), 1000, null, null);
        milka.addCategory(slatkisi);
        Product kinder = new Product("Kinder", new BigDecimal(200), 100, null, null);
        kinder.addCategory(slatkisi);
        Product sprite = new Product("Sprite", new BigDecimal(80), 200, null, null);
        sprite.addCategory(sok);
        sprite.addCategory(slatkisi);

        categoryDAO.save(kola);
        categoryDAO.save(sok);
        categoryDAO.save(slatkisi);
        productDAO.save(audi);
        productDAO.save(bmw);
        productDAO.save(cocaCola);
        productDAO.save(fanta);
        productDAO.save(milka);
        productDAO.save(kinder);
        productDAO.save(sprite);
    }

    @AfterEach
    public void tearDown() {
        productDAO.findAll().forEach(product -> {
            try {
                productDAO.delete(product);
            } catch (DAOException e) {
                e.printStackTrace();
            }
        });
        categoryDAO.findAll().forEach(category -> {
            try {
                categoryDAO.delete(category);
            } catch (DAOException e) {
                e.printStackTrace();
            }
        });
    }

    @ParameterizedTest
    @CsvSource({
            "a, , , sok, 2",
            ", , , so, 3",
            ", 210, 80, s, 1",
            ", 1, 100, , 2",
            ", 2, , kola, 1"
    })
    public void testFindbyCriteria(@Param("name") String name,
                                   @Param("quantity") Integer quantity,
                                   @Param("price") Double price,
                                   @Param("category") String category,
                                   @Param("expectedSize") Integer expectedSize) {

        List<ProductResult> products = productDAO.findByCriteria(name, quantity, price, category);

        assertEquals(expectedSize, products.size());
        if (name!=null) assertTrue(products.stream().allMatch(productResult -> productResult.getName().contains(name)));
        if (quantity!=null) assertTrue(products.stream().allMatch(productResult -> productResult.getQuantity() >= quantity));
        if (price!=null) assertTrue(products.stream().allMatch(productResult -> Objects.equals(productResult.getPrice(), new BigDecimal(price))));

        if (category!=null) {
            assertTrue(products.stream().anyMatch(
                    productResult -> productResult.getCategories().stream().anyMatch(
                            cat -> cat.getName().toLowerCase().contains(category.toLowerCase())
                    )
            ));
        }
    }
}
