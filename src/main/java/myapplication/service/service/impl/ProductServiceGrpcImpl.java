package myapplication.service.service.impl;

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

  private String convertDate2String(Date date){
    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    return dateFormat.format(date);
  }

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
}
