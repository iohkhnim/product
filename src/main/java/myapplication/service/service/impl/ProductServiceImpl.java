package myapplication.service.service.impl;

import java.util.List;
import myapplication.dao.IProductDAO;
import myapplication.dto.Product;
import myapplication.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements IProductService {

  @Autowired
  private IProductDAO productDAO;

  @Override
  public List<Product> findAll() {
    return productDAO.findAll();
  }

  @Override
  public Product findByid(int id) {
    Product prod = productDAO.findByid(id);
    return prod;
  }

  @Override
  public Boolean create(Product product) {
    if (productDAO.create(product)) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public Boolean update(Product product) {
    if (productDAO.update(product)) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public Boolean delete(int id) {
    if (productDAO.delete(id)) {
      return true;
    } else {
      return false;
    }
  }
}
