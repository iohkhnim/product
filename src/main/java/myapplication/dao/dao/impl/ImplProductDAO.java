package myapplication.dao.dao.impl;

import com.google.common.collect.Lists;
import com.khoi.proto.PriceEntry;
import com.khoi.proto.PriceServiceGrpc;
import com.khoi.proto.getPriceHistoryRequest;
import java.util.Iterator;
import java.util.List;
import myapplication.dao.IProductDAO;
import myapplication.dto.Product;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class ImplProductDAO implements IProductDAO {

  private PriceServiceGrpc.PriceServiceBlockingStub priceService;

  public ImplProductDAO(PriceServiceGrpc.PriceServiceBlockingStub priceService) {
    this.priceService = priceService;
  }

  private static <T> Iterable<T> toIterable(final Iterator<T> iterator) {
    return new Iterable<T>() {
      @Override
      public Iterator<T> iterator() {
        return iterator;
      }
    };
  }

  @SuppressWarnings("unchecked")
  public List<Product> findAll() {
    Iterable<PriceEntry> rs = toIterable(priceService
        .getPriceHistory(getPriceHistoryRequest.newBuilder().setProductId(1).build()));
    for(PriceEntry entry : rs){
      System.out.println(entry.getPrice());
    }
    return null;
  }

  public Product findByid(int id) {
    return null;
  }

  public Boolean create(Product product) {
    return null;
  }

  public Boolean update(Product product) {
    return null;
  }

  public Boolean delete(int id) {
    return null;
  }
}
