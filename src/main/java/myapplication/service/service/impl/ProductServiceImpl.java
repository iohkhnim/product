package myapplication.service.service.impl;

import com.khoi.proto.GetPriceHistoryRequest;
import com.khoi.proto.PriceEntry;
import com.khoi.proto.PriceServiceGrpc;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import java.util.Iterator;
import java.util.List;
import myapplication.dao.IProductDAO;
import myapplication.dto.Product;
import myapplication.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements IProductService {

  //private final PriceServiceGrpc.PriceServiceBlockingStub priceService;
  @Autowired
  private IProductDAO productDAO;

 /*public ProductServiceImpl(PriceServiceGrpc.PriceServiceBlockingStub priceService) {
    this.priceService = priceService;
  }*/

  private static <T> Iterable<T> toIterable(final Iterator<T> iterator) {
    return new Iterable<T>() {
      @Override
      public Iterator<T> iterator() {
        return iterator;
      }
    };
  }

  @Override
  public List<Product> findAll() {
    return productDAO.findAll();
  }

  @Override
  public Product findByid(int id) {
    Product prod = productDAO.findByid(id);

    //config gRPC
    //String priceServiceEndpoint = "localhost:6565";
    Channel channel = ManagedChannelBuilder.forAddress("localhost", 6565).usePlaintext().build();
    PriceServiceGrpc.PriceServiceBlockingStub priceService = PriceServiceGrpc.newBlockingStub(channel);
    Iterable<PriceEntry> entries = toIterable(priceService.getPriceHistory(
        GetPriceHistoryRequest.newBuilder().setProductId(id).build()));
    System.out.println(entries);
    return prod;
  }

  @Override
  public Boolean create(Product product) {
    if (productDAO.create(product)) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public Boolean update(Product product) {
    if (productDAO.update(product)) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public Boolean delete(int id) {
    if (productDAO.delete(id)) {
      return true;
    } else {
      return false;
    }
  }
}
