package myapplication.dao;

import java.util.List;

public interface IBaseDAO {

  List<Object> findAll();

  Object findByid(int id);

  Boolean create(Object obj);

  Boolean update(Object obj);

  Boolean delete(int id);
}
