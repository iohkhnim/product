package myapplication.dao.dao.impl;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import myapplication.dao.IProductDAO;
import myapplication.dto.Price;
import myapplication.dto.Product;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Transactional
@Repository
public class ImplProductDAO implements IProductDAO {

  @PersistenceContext
  private EntityManager entityManager;

  @SuppressWarnings("unchecked")
  @Override
  public List<Product> findAll() {
    String hql = "FROM Product as prod ORDER BY prod.id";
    List<Product> list = (List<Product>) entityManager.createQuery(hql).getResultList();
    RestTemplate restTemplate = new RestTemplate();
    //get product price
    String url = "http://localhost:8085/price/findProductPrice/";
    ResponseEntity<String> response;
    for (Product item : list) {
      response = restTemplate.getForEntity(url + item.getId(), String.class);
      item.setPrice(Integer.parseInt(response.getBody()));
    }
    return list;
  }

  @Override
  public Product findByid(int id) {
    Product prod = entityManager.find(Product.class, id);
    //retrieve price from Price API
    RestTemplate restTemplate = new RestTemplate();
    final String url = "http://localhost:8085/price/findProductPrice/";
    ResponseEntity<String> response = restTemplate.getForEntity(url + id, String.class);
    prod.setPrice(Integer.parseInt(response.getBody()));

    //retrieve priceHistory
    ResponseEntity<List<Price>> response2 = restTemplate.exchange(
        "http://localhost:8085/price/findProductPriceHistory/"+id,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<Price>>(){});
    List<Price> list = response2.getBody();
    prod.setPriceHistory(list);

    return prod;
  }

  @Override
  public Boolean create(Product product) {
    try {
      product.setCreatedTime(Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTime());
      product.setUpdatedTime(Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTime());

      //create new Product
      entityManager.persist(product);

      //create new Price
      final String url = "http://localhost:8085/price/create";
      Price price = new Price();
      price.setPrice(product.getPrice());
      price.setProduct_id(product.getId());
      RestTemplate restTemplate = new RestTemplate();
      HttpEntity<Price> requestBody = new HttpEntity<>(price);
      ResponseEntity<Price> result
          = restTemplate.postForEntity(url, requestBody, Price.class);

      return true;
    } catch (Exception ex) {
      return false;
    }
  }

  @Override
  public Boolean update(Product product) {
    try {
      int flag = 0; //mark if price is changed
      Product prod = findByid(product.getId());
      prod.setName(product.getName());
      prod.setSupplier(product.getSupplier());
      if (prod.getPrice() != product.getPrice()) {
        prod.setPrice(product.getPrice());
        flag = 1;
      }
      prod.setUpdatedTime(Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTime());
      entityManager.flush();
      //create new Price
      if (flag == 1) {
        final String url = "http://localhost:8085/price/create";
        Price price = new Price();
        price.setProduct_id(prod.getId());
        price.setPrice(prod.getPrice());
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Price> requestBody = new HttpEntity<>(price);
        ResponseEntity<Price> result
            = restTemplate.postForEntity(url, requestBody, Price.class);
      }
      return true;
    } catch (Exception ex) {
      return false;
    }
  }

  @Override
  public Boolean delete(int id) {
    try {
      entityManager.remove(findByid(id));
      //delete all Price related to this id
      final String url = "http://localhost:8085/price/deleteByProductId/"+id;
      RestTemplate restTemplate = new RestTemplate();
      restTemplate.delete(url);
      return true;
    } catch (Exception ex) {
      return false;
    }
  }
}
