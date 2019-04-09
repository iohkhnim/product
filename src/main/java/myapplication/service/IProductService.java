package myapplication.service;

import java.util.List;
import myapplication.dto.Product;

public interface IProductService {

  /**
   * <p>This method gets all products information</p>
   *
   * @return List of Product type
   */
  List<Product> findAll();

  /**
   * <p>This method get a product information, price, price history from gRPC price server, stock
   * information from gRPC stock server, list of supplier supply this product from gRPC supplier
   * server</p>
   *
   * @param id Product id needs to be retrieved information
   * @return Return a Product object
   */
  Product findByid(int id);

  /**
   * <p>This method passes value to DAO in order to insert into database with given information</p>
   *
   * @param product Product object contains information
   * @return Return a boolean value according to result
   */
  Boolean create(Product product);

  /**
   * <p>This method passes value to DAO in order to update a product in database</p>
   *
   * @param product Product object contains information
   * @return Return a boolean value according to result
   */
  Boolean update(Product product);

  /**
   * <p>This method passes value to DAO in order to delete a product in database
   * with given product ID</p>
   *
   * @param id Product ID needs to be deleted
   * @return Return a boolean value according to result
   */
  Boolean delete(int id);
}
