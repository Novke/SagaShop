package rs.saga.obuka.sagashop.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.saga.obuka.sagashop.dao.ProductDAO;
import rs.saga.obuka.sagashop.domain.Product;
import rs.saga.obuka.sagashop.dto.product.CreateProductCmd;
import rs.saga.obuka.sagashop.dto.product.ProductInfo;
import rs.saga.obuka.sagashop.dto.product.ProductResult;
import rs.saga.obuka.sagashop.dto.product.UpdateProductCmd;
import rs.saga.obuka.sagashop.exception.DAOException;
import rs.saga.obuka.sagashop.exception.ErrorCode;
import rs.saga.obuka.sagashop.exception.ServiceException;
import rs.saga.obuka.sagashop.mapper.ProductMapper;
import rs.saga.obuka.sagashop.service.ProductService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductDAO productDAO;

    @Override
    public Product save(CreateProductCmd cmd) throws ServiceException {
        Product product = ProductMapper.INSTANCE.createProductCmdToProduct(cmd);
        try {
            product = productDAO.save(product);
        } catch (DAOException e) {
            LOGGER.error(null, e);
            throw new ServiceException(ErrorCode.ERR_GEN_001, "Saving of product failed!", e);
        }

        return product;
    }

    @Override
    public List<ProductResult> findAll() {
        return ProductMapper.INSTANCE.listProductToListProductResult(productDAO.findAll());
    }

    @Override
    public ProductInfo findById(Long id) {
        return ProductMapper.INSTANCE.productToProductInfo(productDAO.findOne(id));
    }

    @Override
    public void update(UpdateProductCmd productDTO) throws ServiceException {

        Product product;
        try {
            product = productDAO.findOne(productDTO.getId());
            if (product == null) {
                throw new ServiceException(ErrorCode.ERR_GEN_001, "Product not found!");
            }
            ProductMapper.INSTANCE.updateProductInfoToProduct(product, productDTO);
            productDAO.merge(product);
        } catch (DAOException e) {
            LOGGER.error(null, e);
            throw new ServiceException(ErrorCode.ERR_GEN_001, "Finding of product failed!", e);
        }

    }

    @Override
    public void delete(Long id) throws ServiceException {
        Product product = productDAO.findOne(id);
        if (product != null) {
            try {
                productDAO.delete(product);
            } catch (DAOException e) {
                LOGGER.error(null, e);
                throw new ServiceException(ErrorCode.ERR_GEN_001, "Deleting of product failed!", e);
            }
        } else {
            throw new ServiceException(ErrorCode.ERR_CAT_001, "Product not found!");
        }
    }

    @Override
    public List<ProductResult> findProducts(String name, Integer quantity, Double price, String category) {
        return productDAO.findByCriteria(name, quantity, price, category);
    }
}
