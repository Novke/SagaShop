package rs.saga.obuka.sagashop;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rs.saga.obuka.sagashop.dao.CategoryDAO;
import rs.saga.obuka.sagashop.dao.ProductDAO;
import rs.saga.obuka.sagashop.exception.DAOException;
import rs.saga.obuka.sagashop.exception.ServiceException;
import rs.saga.obuka.sagashop.service.CategoryService;
import rs.saga.obuka.sagashop.util.TransactionHandler;

/**
 * @author: Ana Dedović
 * Date: 13.07.2021.
 **/
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SagashopApplicationTest.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractIntegrationTest {

  @Autowired
  private CategoryService categoryService;
  @Autowired
  private ProductDAO productDAO;
  @Autowired
  private CategoryDAO categoryDAO;
  @Autowired
  private TransactionHandler transactionHandler;


  @AfterEach
  public void tearDown(){
//    categoryService.findAll().forEach(e -> {
//      try {
//        categoryService.delete(e.getId());
//      } catch (ServiceException serviceException) {
//        serviceException.printStackTrace();
//      }
//    });

    transactionHandler.runInTransaction(() -> {
        try {
            productDAO.deleteAll();
            categoryDAO.deleteAll();
            return null;
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
    });

  }

}
