package rs.saga.obuka.sagashop.integration.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import rs.saga.obuka.sagashop.AbstractIntegrationTest;
import rs.saga.obuka.sagashop.domain.BaseEntity;
import rs.saga.obuka.sagashop.domain.Category;
import rs.saga.obuka.sagashop.domain.Product;
import rs.saga.obuka.sagashop.dto.category.CategoryInfo;
import rs.saga.obuka.sagashop.dto.category.CategoryResult;
import rs.saga.obuka.sagashop.dto.category.CreateCategoryCmd;
import rs.saga.obuka.sagashop.dto.category.UpdateCategoryCmd;
import rs.saga.obuka.sagashop.dto.product.CreateProductCmd;
import rs.saga.obuka.sagashop.exception.ServiceException;
import rs.saga.obuka.sagashop.service.CategoryService;
import rs.saga.obuka.sagashop.service.ProductService;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author: Ana Dedović
 * Date: 13.07.2021.
 */
public class CategoryServiceTest extends AbstractIntegrationTest {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    @Test
    public void saveCategory() throws ServiceException {
        CreateCategoryCmd cmd = new CreateCategoryCmd("Tehnika", "Tv, CD, USB", null);
        Category category = categoryService.save(cmd);
        assertNotNull(category.getId());
        assertEquals(cmd.getDescription(), category.getDescription());
        assertEquals(cmd.getName(), category.getName());
    }

    @Test
    public void updateCategory() throws ServiceException {
        //cuvamo kategoriju
        CreateCategoryCmd cmd = new CreateCategoryCmd("Tehnika", "Tv, CD, USB", null);
        Category category = categoryService.save(cmd);
        assertNotNull(category.getId());

        //menjamo kategoriju
        UpdateCategoryCmd updateCategoryCmd = new UpdateCategoryCmd(category.getId(),  category.getName(), category.getDescription());
        updateCategoryCmd.setName("promenjena kategorija");
        categoryService.update(updateCategoryCmd);

        //proveravamo da li je kategorija promenjena
        CategoryInfo categoryInfo = categoryService.findById(category.getId());
        assertEquals("promenjena kategorija", categoryInfo.getName());
    }

    @Test
    public void deleteCategory() throws ServiceException {
        //kreiramo kategoriju
        CreateCategoryCmd cmd = new CreateCategoryCmd("Tehnika", "Tv, CD, USB", null);
        Category category = categoryService.save(cmd);
        assertNotNull(category.getId());

        //brisemo kategoriju
        categoryService.delete(category.getId());

        //proveravamo kategoriju
        CategoryInfo categoryInfo = categoryService.findById(category.getId());
        assertNull(categoryInfo);
    }

    @Test
    public void findOne() throws ServiceException {
        //kreiramo kategoriju
        CreateCategoryCmd cmd = new CreateCategoryCmd("Tehnika", "Tv, CD, USB", null);
        Category category = categoryService.save(cmd);
        assertNotNull(category.getId());

        //proveravamo kategoriju
        CategoryInfo categoryInfo = categoryService.findById(category.getId());
        assertEquals("Tehnika", categoryInfo.getName());
        assertEquals("Tv, CD, USB", categoryInfo.getDescription());
    }

    @Test
    public void findAll() throws ServiceException {
        //cuvamo kategoriju 1
        CreateCategoryCmd cmd1 = new CreateCategoryCmd("Tehnika", "Tv, CD, USB", null);
        categoryService.save(cmd1);

        //cuvamo kategoriju 2
        CreateCategoryCmd cmd2 = new CreateCategoryCmd("Hrana", "Smoki, Cips, Grisine", null);
        categoryService.save(cmd2);

        //proveravamo listu kategorija
        List<CategoryResult> categoryResult = categoryService.findAll();
        assertEquals(2, categoryResult.size());
        assertTrue(categoryResult.stream().anyMatch(e -> e.getName().equals("Tehnika")));
        assertTrue(categoryResult.stream().anyMatch(e -> e.getName().equals("Hrana")));
    }

    @Test
    public void saveWithCascades() throws ServiceException {
        CreateProductCmd productCmd = new CreateProductCmd("Ves masina",new BigDecimal(50000), 40,"Top",null);
        CreateCategoryCmd cmd = new CreateCategoryCmd("Tehnika", "Tv, CD, USB", List.of(productCmd));

        Category category = categoryService.save(cmd);
        assertNotNull(category.getId());

        assertNotNull(category.getProducts());
        assertFalse(category.getProducts().isEmpty());
        assertEquals(1, category.getProducts().size());
        assertNotNull(category.getProducts().get(0).getId());
        assertTrue(category.getProducts().get(0).getId()>0);

        Product product = productService.save(new CreateProductCmd("Name", new BigDecimal(500), 150, "Opis", null));
        assertNotNull(product);
        assertNotNull(product.getId());
        assertNotEquals(0, product.getId());
    }

    @Test
    public void saveWithCascades2() throws ServiceException {
        CreateCategoryCmd categoryCmd = new CreateCategoryCmd("Tehnika", "Tv, CD, USB", emptyList());
        CreateCategoryCmd categoryCmd1 = new CreateCategoryCmd("Elektronika", "IT", emptyList());
        CreateProductCmd productCmd = new CreateProductCmd("Ves masina",new BigDecimal(50000),40, "opis", List.of(categoryCmd, categoryCmd1));

        Product product = productService.save(productCmd);
        assertNotNull(product.getId());

        assertNotNull(product.getCategories());
        assertFalse(product.getCategories().isEmpty());
        assertEquals(2, product.getCategories().size());

        for (Category category : product.getCategories()) {
            assertNotNull(category.getId());
            assertNotEquals(0, category.getId());
        }

        List<Long> ids = product.getCategories().stream().map(c -> c.getId()).collect(Collectors.toList());

        Product newProduct = productService.saveWithCategories(new CreateProductCmd("Name", new BigDecimal(500), 150, "Opis", null), ids);

        assertNotNull(newProduct);
        assertNotNull(newProduct.getId());
        assertNotEquals(0, newProduct.getId());
        assertEquals(2, product.getCategories().size());
    }

}
