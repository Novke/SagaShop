package rs.saga.obuka.sagashop.dao.impl;

import org.springframework.stereotype.Repository;
import rs.saga.obuka.sagashop.dao.ProductDAO;
import rs.saga.obuka.sagashop.domain.Category;
import rs.saga.obuka.sagashop.domain.CategoryProduct;
import rs.saga.obuka.sagashop.domain.Product;
import rs.saga.obuka.sagashop.dto.product.ProductResult;
import rs.saga.obuka.sagashop.mapper.ProductMapper;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDAOImpl extends AbstractDAOImpl<Product, Long> implements ProductDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<ProductResult> findByCriteria(String name, Integer quantity, Double price, String category) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);

        Root<Product> root = criteriaQuery.from(Product.class);
        List<Predicate> predicates = new ArrayList<>();

        if (name != null && !name.isBlank()) {
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
        }
        if (quantity != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("quantity"), quantity));
        }
        if (price != null) {
            predicates.add(criteriaBuilder.equal(root.get("price"), price));
        }
        if (category != null && !category.isBlank()) {
            Join<Product, CategoryProduct> categoryJoin = root.join("categoryProducts");
            Join<CategoryProduct, Category> categoryJoin2 = categoryJoin.join("category");
            predicates.add(criteriaBuilder.like(categoryJoin2.get("name"), "%"+category+"%"));
        }
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        criteriaQuery.select(root);
        List<Product> products = entityManager.createQuery(criteriaQuery).getResultList();

        return ProductMapper.INSTANCE.listProductToListProductResult(products);
    }
}
