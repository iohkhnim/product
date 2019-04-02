package myapplication.config;

import com.khoi.proto.PriceServiceGrpc;
import com.khoi.stockproto.StockServiceGrpc;
import com.khoi.supplierproto.SupplierServiceGrpc;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class ApplicationConfig {


  private String priceServiceEndpoint = "localhost:6565";

  private String stockServiceEndpoint = "172.17.0.5:6565";

  private String supplierServiceEndpoint = "172.17.0.6:6565";

  private final String priceServerKeyPath = "key/ca.crt";
  private final String stockServerKeyPath = "key/castock.crt";
  private final String supplierServerKeyPath = "key/ca.crt";

  @Bean(name = "priceChannel")
  Channel priceChannel() throws Exception {
    return NettyChannelBuilder.forTarget(priceServiceEndpoint)
        .negotiationType(NegotiationType.TLS)
        .sslContext(GrpcSslContexts.forClient().trustManager(new File(priceServerKeyPath)).build())
        .build();
  }

  @Bean(name = "stockChannel")
  Channel stockChannel() {
    try{
    return NettyChannelBuilder.forTarget(stockServiceEndpoint)
        .negotiationType(NegotiationType.TLS)
        .sslContext(GrpcSslContexts.forClient().trustManager(new File(stockServerKeyPath)).build())
        .build();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }

  @Bean(name = "supplierChannel")
  Channel supplierChannel() throws Exception {
    return NettyChannelBuilder.forTarget(supplierServiceEndpoint)
        .negotiationType(NegotiationType.TLS)
        .sslContext(
            GrpcSslContexts.forClient().trustManager(new File(supplierServerKeyPath)).build())
        .build();
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
