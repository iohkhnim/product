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
import com.khoi.supplierproto.GetSupplierListRequest;
import com.khoi.supplierproto.SupplierEntry;
import com.khoi.supplierproto.SupplierServiceGrpc;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
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

  @Qualifier("supplierService")
  private final SupplierServiceGrpc.SupplierServiceBlockingStub supplierService;

  @Autowired
  private IProductDAO productDAO;

  public ProductServiceImpl(PriceServiceGrpc.PriceServiceBlockingStub priceService,
      StockServiceGrpc.StockServiceBlockingStub stockService,
      SupplierServiceGrpc.SupplierServiceBlockingStub supplierService) {
    this.priceService = priceService;
    this.stockService = stockService;
    this.supplierService = supplierService;
  }

  /**
   * <p>This method converts Iterator to Iterable</p>
   *
   * @param iterator Iterator object
   * @param <T> Type of Iterator object
   * @return Iterable object has the same type of provided Iterator object
   */
  private static <T> Iterable<T> toIterable(final Iterator<T> iterator) {
    return new Iterable<T>() {
      @Override
      public Iterator<T> iterator() {
        return iterator;
      }
    };
  }

  /**
   * <p>This method gets all products information</p>
   *
   * @return List of Product type
   */
  @Override
  public List<Product> findAll() {
    List<Product> list = productDAO.findAll();
    list.stream().forEach(p -> findByid(p.getId()));
    return list;
  }

  /**
   * <p>This method get a product information, price, price history from gRPC price server, stock
   * information from gRPC stock server, list of supplier supply this product from gRPC supplier
   * server</p>
   *
   * @param id Product id needs to be retrieved information
   * @return Return a Product object
   */
  @Override
  public Product findByid(int id) {
    Product prod = productDAO.findByid(id);
    try {
      //get PriceHistory
      Iterable<PriceEntry> entries = toIterable(priceService.getPriceHistory(
          GetPriceHistoryRequest.newBuilder().setProductId(id).build()));

      List<PriceEntry> list1 = new ArrayList<>();
      entries.forEach(list1::add);

      //cach ta dao
    /*List<String> strings = list1.stream()
        .map(object -> Objects.toString(object, null))
        .collect(Collectors.toList());*/

      //cach khong ta dao?
      List<String> strings = list1.stream().map(p -> new JsonFormat().printToString(p))
          .collect(Collectors.toList());

      prod.setPriceEntries(strings);

      GetPriceResponse rs = priceService
          .getPrice(GetPriceRequest.newBuilder().setProductId(id).build());
      prod.setPrice(rs.getPrice());
    } catch (Exception ex) {
      System.out.println(ex);
      prod.setPriceEntries(null);
      prod.setPrice(-1);
    }
    try {
      //get stock
      GetStockResponse rs2 = stockService
          .getStock(GetStockRequest.newBuilder().setProductId(id).build());
      prod.setStock(rs2.getStock());
    } catch (Exception ex) {
      prod.setStock(-1);
    }
    try {
      //get list of suppliers selling this product
      List<SupplierEntry> supplierEntryList = new ArrayList<>();
      //get result from gRPC server
      Iterable<SupplierEntry> supplierEntryIterable = toIterable(
          supplierService.getSupplierListByProductId(
              GetSupplierListRequest.newBuilder().setProductId(id).build()));
      //convert Iterable -> list<Entry>
      supplierEntryIterable.forEach(supplierEntryList::add);

      //convert list<entry> -> list<String>
      //result of list<entry> -> list<String>
      List<String> supplierList = supplierEntryList.stream()
          .map(s -> new JsonFormat().printToString(s))
          .collect(Collectors.toList());

      prod.setSupplierEntries(supplierList);
    } catch (Exception ex) {
      prod.setSupplierEntries(null);
    }
    return prod;
  }

  /**
   * <p>This method passes value to DAO in order to insert into database with given information</p>
   *
   * @param product Product object contains information
   * @return Return a boolean value according to result
   */
  @Override
  public Boolean create(Product product) {
    if (productDAO.create(product)) {

      //create new price
      CreateResponse rs = priceService.create(
          CreateRequest.newBuilder().setPrice(product.getPrice()).setProductId(product.getId())
              .build());
      return rs.getId() > 0;
    } else {
      return false;
    }
  }

  /**
   * <p>This method passes value to DAO in order to update a product in database</p>
   *
   * @param product Product object contains information
   * @return Return a boolean value according to result
   */
  @Override
  public Boolean update(Product product) {
    Product prod_old = findByid(product.getId());

    if (prod_old.getPrice() != product.getPrice()) {
      CreateResponse rs = priceService.create(
          CreateRequest.newBuilder().setPrice(product.getPrice()).setProductId(product.getId())
              .build());
      if (rs.getId() >= 0) {
        return productDAO.update(product);
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  /**
   * <p>This method passes value to DAO in order to delete a product in database
   * with given product ID</p>
   *
   * @param id Product ID needs to be deleted
   * @return Return a boolean value according to result
   */
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
