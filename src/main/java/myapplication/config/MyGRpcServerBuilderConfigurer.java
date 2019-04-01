package myapplication.config;

import io.grpc.ServerBuilder;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyServerBuilder;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslProvider;
import org.lognet.springboot.grpc.GRpcServerBuilderConfigurer;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class MyGRpcServerBuilderConfigurer extends GRpcServerBuilderConfigurer {

  private final String certChainFilePath = "src/main/java/myapplication/key/server.crt";
  private final String privateKeyFilePath = "src/main/java/myapplication/key/server.pem";

  private SslContextBuilder getSslContextBuilder() throws Exception {
    SslContextBuilder sslContextBuilder =
        SslContextBuilder.forServer(new File(certChainFilePath), new File(privateKeyFilePath));
    return GrpcSslContexts.configure(sslContextBuilder, SslProvider.OPENSSL);
  }

  @Override
  public void configure(ServerBuilder<?> serverBuilder) {
    try {
      ((NettyServerBuilder) serverBuilder).sslContext(getSslContextBuilder().build()).build();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
