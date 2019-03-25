package myapplication;

import com.khoi.proto.PriceServiceGrpc;
import com.khoi.stockproto.StockServiceGrpc;
import com.khoi.supplierproto.SupplierServiceGrpc;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;
import java.io.File;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

  private final String priceServiceEndpoint = "localhost:6565";
  private final String stockServiceEndpoint = "localhost:6570";
  private final String supplierServiceEndpoint = "localhost:6580";

  @Bean(name = "priceChannel")
  Channel priceChannel() throws Exception {
    return NettyChannelBuilder.forTarget(priceServiceEndpoint).negotiationType(NegotiationType.TLS)
        .sslContext(GrpcSslContexts.forClient()
            .trustManager(new File("D:\\product\\src\\main\\java\\myapplication\\key\\ca.crt"))
            .build())
        .build();
  }

  @Bean(name = "stockChannel")
  Channel stockChannel() {
    return ManagedChannelBuilder.forTarget(stockServiceEndpoint).usePlaintext().build();
  }

  @Bean(name = "supplierChannel")
  Channel supplierChannel() {
    return ManagedChannelBuilder.forTarget(supplierServiceEndpoint).usePlaintext().build();
  }

  @Bean(name = "priceService")
  @Qualifier("priceChannel")
  PriceServiceGrpc.PriceServiceBlockingStub priceService(Channel priceChannel) {
    return PriceServiceGrpc.newBlockingStub(priceChannel);
  }

  @Bean(name = "stockService")
  @Qualifier("stockChannel")
  StockServiceGrpc.StockServiceBlockingStub stockService(Channel stockChannel) {
    return StockServiceGrpc.newBlockingStub(stockChannel);
  }

  @Bean(name = "supplierService")
  @Qualifier("supplierChannel")
  SupplierServiceGrpc.SupplierServiceBlockingStub supplierService(Channel supplierChannel) {
    return SupplierServiceGrpc.newBlockingStub(supplierChannel);
  }

}
