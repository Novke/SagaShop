package rs.saga.obuka.sagashop.integration.service;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import rs.saga.obuka.sagashop.AbstractIntegrationTest;
import rs.saga.obuka.sagashop.domain.Category;
import rs.saga.obuka.sagashop.dto.category.CategoryInfo;
import rs.saga.obuka.sagashop.dto.category.CategoryResult;
import rs.saga.obuka.sagashop.dto.category.CreateCategoryCmd;
import rs.saga.obuka.sagashop.dto.category.UpdateCategoryCmd;
import rs.saga.obuka.sagashop.dto.product.CreateProductCmd;
import rs.saga.obuka.sagashop.exception.ServiceException;
import rs.saga.obuka.sagashop.service.CategoryService;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author: Ana Dedović
 * Date: 13.07.2021.
 */
public class CategoryServiceTest extends AbstractIntegrationTest {

    @Autowired
    private CategoryService categoryService;

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
        CreateProductCmd productCmd = new CreateProductCmd("Ves masina",new BigDecimal(50000),"Top",40);
        CreateCategoryCmd cmd = new CreateCategoryCmd("Tehnika", "Tv, CD, USB", List.of(productCmd));

        Category category = categoryService.save(cmd);
        assertNotNull(category.getId());

        assertNotNull(category.getProducts());
        assertFalse(category.getProducts().isEmpty());
        assertEquals(1, category.getProducts().size());
        assertNotNull(category.getProducts().get(0).getId());
        assertTrue(category.getProducts().get(0).getId()>0);
    }

}
