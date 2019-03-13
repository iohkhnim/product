package myapplication.service.service.impl;

import com.khoi.productproto.GetProductRequest;
import com.khoi.productproto.ProductEntry;
import com.khoi.productproto.ProductServiceGrpc;
import io.grpc.stub.StreamObserver;
import myapplication.dao.IProductDAO;
import myapplication.dto.Product;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GRpcService
public class ProductServiceGrpcImpl extends ProductServiceGrpc.ProductServiceImplBase {

  @Autowired
  private IProductDAO productDAO;

  @Override
  public void getProduct(GetProductRequest request, StreamObserver<ProductEntry> responseObserver) {
    Product product = productDAO.findByid(request.getProductId());
    responseObserver.onNext(
        ProductEntry.newBuilder().setId(product.getId()).setName(product.getName())
            .setDescription(product.getDescription())
            .setCreatedTime(product.getCreatedTime().getTime())
            .setUpdatedTime(product.getUpdatedTime().getTime()).build());
    responseObserver.onCompleted();
  }
}
