package myapplication.dao.dao.impl;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import myapplication.dao.IProductDAO;
import myapplication.dto.Product;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class ProductDAOImpl implements IProductDAO {

  @PersistenceContext
  private EntityManager entityManager;

  @SuppressWarnings("unchecked")
  public List<Product> findAll() {
    String hql = "FROM Product as prod ORDER BY prod.id";
    List<Product> list = (List<Product>) entityManager.createQuery(hql).getResultList();
   /* RestTemplate restTemplate = new RestTemplate();
    // get product price
    String url = "http://localhost:8085/price/findProductPrice/";
    ResponseEntity<String> response;
    for (Product item : list) {
      response = restTemplate.getForEntity(url + item.getId(), String.class);
      item.setPrice(Integer.parseInt(response.getBody()));
    }*/
    return list;
  }

  public Product findByid(int id) {
    Product prod = entityManager.find(Product.class, id);
    // retrieve price from Price API
    /*RestTemplate restTemplate = new RestTemplate();
    final String url = "http://localhost:8085/price/findProductPrice/";
    ResponseEntity<String> response = restTemplate.getForEntity(url + id, String.class);
    prod.setPrice(Integer.parseInt(response.getBody()));*/
    /*
    // retrieve priceHistory
    ResponseEntity<List<Price>> response2 =
        restTemplate.exchange(
            "http://localhost:8085/price/findProductPriceHistory/" + id,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Price>>() {});
    List<Price> list = response2.getBody();
    */
    return prod;
  }

  public Boolean create(Product product) {
    try {
      product.setCreatedTime(Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTime());
      product.setUpdatedTime(Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTime());

      // create new Product
      entityManager.persist(product);
      return true;
    } catch (Exception ex) {
      return false;
    }
  }

  public Boolean update(Product product) {
    try {
      Product prod = findByid(product.getId());
      prod.setName(product.getName());
      prod.setDescription(product.getDescription());
      if (prod.getPrice() != product.getPrice()) {
        prod.setPrice(product.getPrice());
      }
      prod.setUpdatedTime(Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTime());
      entityManager.flush();
      return true;
    } catch (Exception ex) {
      return false;
    }
  }

  public Boolean delete(int id) {
    try {
      entityManager.remove(findByid(id));
      return true;
    } catch (Exception ex) {
      return false;
    }
  }

  @Override
  public String getProductNameById(int product_id) {
    String hql = "SELECT p.name FROM Product p WHERE p.product_id = :prodid";
    Query query = entityManager.createQuery(hql);
    query.setParameter("prodid", product_id);
    return query.getSingleResult().toString();
  }
}
