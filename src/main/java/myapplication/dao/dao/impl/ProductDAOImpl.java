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

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Product> findAll() {
    String hql = "FROM Product as prod ORDER BY prod.id";
    List<Product> list = (List<Product>) entityManager.createQuery(hql).getResultList();
    return list;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Product findByid(int id) {
    Product prod = entityManager.find(Product.class, id);
    return prod;
  }

  /**
   * {@inheritDoc}
   */
  @Override
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

  /**
   * {@inheritDoc}
   */
  @Override
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

  /**
   * {@inheritDoc}
   */
  @Override
  public Boolean delete(int id) {
    try {
      entityManager.remove(findByid(id));
      return true;
    } catch (Exception ex) {
      return false;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getProductNameById(int product_id) {
    String hql = "SELECT p.name FROM Product p WHERE p.id = :prodid";
    Query query = entityManager.createQuery(hql);
    query.setParameter("prodid", product_id);
    return query.getSingleResult().toString();
  }
}
