package myapplication.dao.dao.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import myapplication.dao.IBaseDAO;

public class ImplBaseDAO implements IBaseDAO {

  private String table;
  private String dto;
  @PersistenceContext
  private EntityManager entityManager;

  public ImplBaseDAO(String table) {
    this.table = table;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Object> findAll() {
    String hql = "FROM "+ table +" as prod ORDER BY prod.id";
    return (List<Object>) entityManager.createQuery(hql).getResultList();
  }

  @Override
  public Object findByid(int id) {
    return entityManager.find(Object.class, id);
  }

  @Override
  public Boolean create(Object object) {
    try {
      entityManager.persist(object);
      return true;
    } catch (Exception ex) {
      return false;
    }
  }

  @Override
  public Boolean update(Object object) {
    try {
      Object prod = findByid(product.getId());
      prod.setName(product.getName());
      prod.setPrice(product.getPrice());
      prod.setSupplier(product.getSupplier());
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
