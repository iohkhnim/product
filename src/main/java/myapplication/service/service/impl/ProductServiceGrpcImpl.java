package myapplication.service.service.impl;

import com.khoi.productproto.GetProductNameByIdRequest;
import com.khoi.productproto.GetProductNameByIdResponse;
import com.khoi.productproto.GetProductRequest;
import com.khoi.productproto.ProductEntry;
import com.khoi.productproto.ProductServiceGrpc;
import io.grpc.stub.StreamObserver;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import myapplication.dao.IProductDAO;
import myapplication.dto.Product;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GRpcService
public class ProductServiceGrpcImpl extends ProductServiceGrpc.ProductServiceImplBase {

  @Autowired
  private IProductDAO productDAO;

  /**
   * <p>This method converts date in Date type to date in String type</p>
   * @param date date in Date type
   * @return date in String type
   */
  private String convertDate2String(Date date) {
    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    return dateFormat.format(date);
  }

  /**
   * <p>This method receives a request from gRPC client then return product information to gRPC client</p>
   * @param request Contains product ID needs to be retrieved information
   * @param responseObserver Contains product information in ProductEntry type
   */
  @Override
  public void getProduct(GetProductRequest request, StreamObserver<ProductEntry> responseObserver) {
    Product product = productDAO.findByid(request.getProductId());
    responseObserver.onNext(
        ProductEntry.newBuilder().setId(product.getId()).setName(product.getName())
            .setDescription(product.getDescription())
            .setCreatedTime(convertDate2String(product.getCreatedTime()))
            .setUpdatedTime(convertDate2String(product.getUpdatedTime())).build());
    responseObserver.onCompleted();
  }

  /**
   *  <p>This method receives a request from gRPC client then return product name to gRPC client</p>
   * @param request Contains product ID needs to be retrieved its name
   * @param streamObserver Contains product name
   */
  @Override
  public void getProductNameById(GetProductNameByIdRequest request,
      StreamObserver<GetProductNameByIdResponse> streamObserver) {
    streamObserver.onNext(GetProductNameByIdResponse.newBuilder()
        .setProductName(productDAO.getProductNameById(request.getProductId())).build());
    streamObserver.onCompleted();
  }
}
