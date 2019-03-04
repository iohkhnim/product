package myapplication.service;

import java.util.List;
import myapplication.dto.Product;

public interface IProductService {

  List<Product> findAll();

  Product findByid(int id);

  Boolean create(Product product);

  Boolean update(Product product);

  Boolean delete(int id);
}
