package order.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/***
    也可以在全局过滤器中进行请求登录授权的判断
 */
@Component
public class LogFilter implements GlobalFilter {
    Logger log= LoggerFactory.getLogger(this.getClass());

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        System.out.println("这是全局过滤器");

        System.out.println(exchange.getRequest().getPath().value());
        return chain.filter(exchange);
    }
}
