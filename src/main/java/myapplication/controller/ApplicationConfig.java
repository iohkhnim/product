package myapplication.controller;

import com.khoi.proto.PriceServiceGrpc;
import com.khoi.stockproto.StockServiceGrpc;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

  private final String priceServiceEndpoint = "localhost:6565";
  private final String stockServiceEndpoint = "localhost:6570";

  @Bean(name = "priceChannel")
  Channel priceChannel() {
    return ManagedChannelBuilder.forTarget(priceServiceEndpoint).usePlaintext().build();
  }

  @Bean(name = "stockChannel")
  Channel stockChannel() {
    return ManagedChannelBuilder.forTarget(stockServiceEndpoint).usePlaintext().build();
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

}
