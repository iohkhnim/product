package myapplication.service.service.impl;

import com.googlecode.protobuf.format.JsonFormat;
import com.khoi.proto.CreateRequest;
import com.khoi.proto.CreateResponse;
import com.khoi.proto.DeleteRequest;
import com.khoi.proto.DeleteResponse;
import com.khoi.proto.GetPriceHistoryRequest;
import com.khoi.proto.GetPriceRequest;
import com.khoi.proto.GetPriceResponse;
import com.khoi.proto.PriceEntry;
import com.khoi.proto.PriceServiceGrpc;
import com.khoi.stockproto.GetStockRequest;
import com.khoi.stockproto.GetStockResponse;
import com.khoi.stockproto.StockServiceGrpc;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import myapplication.dao.IProductDAO;
import myapplication.dto.Product;
import myapplication.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements IProductService {

  @Qualifier("priceService")
  private final PriceServiceGrpc.PriceServiceBlockingStub priceService;

  @Qualifier("stockService")
  private final StockServiceGrpc.StockServiceBlockingStub stockService;

  @Autowired
  private IProductDAO productDAO;

  public ProductServiceImpl(PriceServiceGrpc.PriceServiceBlockingStub priceService,
      StockServiceGrpc.StockServiceBlockingStub stockService) {
    this.priceService = priceService;
    this.stockService = stockService;
  }

  private static <E> Collection<E> makeCollection(Iterable<E> iter) {
    Collection<E> list = new ArrayList<E>();
    for (E item : iter) {
      list.add(item);
    }
    return list;
  }

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
    //return productDAO.findAll();
    List<Product> list = productDAO.findAll();
    for (Product prod : list) {
      //get price
      prod = findByid(prod.getId());
    }
    return list;
  }

  @Override
  public Product findByid(int id) {
    Product prod = productDAO.findByid(id);

    Iterable<PriceEntry> entries = toIterable(priceService.getPriceHistory(
        GetPriceHistoryRequest.newBuilder().setProductId(id).build()));

    List<PriceEntry> list1 = new ArrayList<>();
    entries.forEach(list1::add);

    //cach ta dao
    /*List<String> strings = list1.stream()
        .map(object -> Objects.toString(object, null))
        .collect(Collectors.toList());*/

    List<String> strings = new ArrayList<>();

    //cach khong ta dao
    for (PriceEntry price : list1) {
      strings.add(new JsonFormat().printToString(price));
    }

    prod.setPriceEntries(strings);

    GetPriceResponse rs = priceService
        .getPrice(GetPriceRequest.newBuilder().setProductId(id).build());
    prod.setPrice(rs.getPrice());

    //get stock
    GetStockResponse rs2 = stockService
        .getStock(GetStockRequest.newBuilder().setProductId(id).build());
    prod.setStock(rs2.getStock());

    return prod;
  }

  @Override
  public Boolean create(Product product) {
    if (productDAO.create(product)) {

      //create new price
      CreateResponse rs = priceService.create(
          CreateRequest.newBuilder().setPrice(product.getPrice()).setProductId(product.getId())
              .build());
      if (rs.getId() > 0) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  @Override
  public Boolean update(Product product) {
    Product prod_old = findByid(product.getId());

    if (prod_old.getPrice() != product.getPrice()) {
      CreateResponse rs = priceService.create(
          CreateRequest.newBuilder().setPrice(product.getPrice()).setProductId(product.getId())
              .build());
      if (rs.getId() >= 0) {
        if (productDAO.update(product)) {
          return true;
        } else {
          return false;
        }
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  @Override
  public Boolean delete(int id) {
    if (productDAO.delete(id)) {
      DeleteResponse rs = priceService.delete(DeleteRequest.newBuilder().setProductId(id).build());
      return true;
    } else {
      return false;
    }
  }
}
