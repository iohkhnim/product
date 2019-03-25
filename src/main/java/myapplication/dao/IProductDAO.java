package myapplication.dao;

import java.util.List;
import myapplication.dto.Product;

public interface IProductDAO {

  /**
   * <p>This method gets all products from database</p>
   *
   * @return List of Product objects
   */
  List<Product> findAll();

  /**
   * <p>This method get a product information</p>
   *
   * @param id Product id needs to be retrieved information
   * @return Return a Product object
   */
  Product findByid(int id);

  /**
   * <p>This method inserts into database with given information</p>
   *
   * @param product Product object contains information
   * @return Return a boolean value according to result
   */
  Boolean create(Product product);

  /**
   * <p>This method update a product in database</p>
   *
   * @param product Product object contains information
   * @return Return a boolean value according to result
   */
  Boolean update(Product product);

  /**
   * <p>This method deletes a product in database with given product ID</p>
   *
   * @param id Product ID needs to be deleted
   * @return Return a boolean value according to result
   */
  Boolean delete(int id);

  /**
   * <p>This method retrieves product name from database with given product ID</p>
   *
   * @param product_id Product ID needs to be get its name
   * @return Return product name of given product ID
   */
  String getProductNameById(int product_id);
}
