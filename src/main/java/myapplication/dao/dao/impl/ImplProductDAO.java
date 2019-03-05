package myapplication.dao.dao.impl;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import myapplication.dao.IProductDAO;
import myapplication.dto.Product;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class ImplProductDAO implements IProductDAO {

  @PersistenceContext
  private EntityManager entityManager;

  @SuppressWarnings("unchecked")
  @Override
  public List<Product> findAll() {
    String hql = "FROM Product as prod ORDER BY prod.id";
    return (List<Product>) entityManager.createQuery(hql).getResultList();
  }

  @Override
  public Product findByid(int id) {
    return entityManager.find(Product.class, id);
  }

  @Override
  public Boolean create(Product product) {
    try {
      entityManager.persist(product);
      return true;
    } catch (Exception ex) {
      return false;
    }
  }

  @Override
  public Boolean update(Product product) {
    try {
      Product prod = findByid(product.getId());
      prod.setName(product.getName());
      prod.setPrice(product.getPrice());
      prod.setSupplier(product.getSupplier());
      prod.setUpdatedTime(Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTime());
      entityManager.flush();
      return true;
    } catch (Exception ex) {
      return false;
    }
  }

  @Override
  public Boolean delete(int id) {
    try {
      entityManager.remove(findByid(id));
      return true;
    } catch (Exception ex) {
      return false;
    }
  }
}
