package order.filters;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

import org.springframework.cloud.gateway.support.GatewayToStringStyler;
import org.springframework.cloud.gateway.support.HttpStatusHolder;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

/***
    适合做用户登录判断
 */
@Component
    public class CheckAuthGatewayFilterFactory
        extends AbstractGatewayFilterFactory<CheckAuthGatewayFilterFactory.Config> {


    public static final String STATUS_KEY = "status";
    public static final String URL_KEY = "url";

    public CheckAuthGatewayFilterFactory() {
        super(CheckAuthGatewayFilterFactory.Config.class);
    }

    public List<String> shortcutFieldOrder() {
        return Arrays.asList("status", "url");
    }

    public GatewayFilter apply(CheckAuthGatewayFilterFactory.Config config) {
        return this.apply(config.status, config.url);
    }

    public GatewayFilter apply(String statusString, String urlString) {
        HttpStatusHolder httpStatus = HttpStatusHolder.parse(statusString);
        Assert.isTrue(httpStatus.is3xxRedirection(), "status must be a 3xx code, but was " + statusString);
        URI url = URI.create(urlString);
        return this.apply(httpStatus, url);
    }

    public GatewayFilter apply(HttpStatus httpStatus, URI uri) {
        return this.apply(new HttpStatusHolder(httpStatus, (Integer)null), uri);
    }



    public GatewayFilter apply(HttpStatusHolder httpStatus, URI uri) {
        return new GatewayFilter() {
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

                System.out.println("===========开始校验用户登录状态==========");

                //如果请求路径是登录或者错误页面相关的不进行拦截
                RequestPath requirePath = exchange.getRequest().getPath();
                String path = requirePath.value();
                System.out.println("请求路径："+path);

                //设置请求排除项目
                if(path.contains("loginErr")){
                    System.out.println("这是排除项，无需进行校验");
                    return chain.filter(exchange);
                }

                if (!exchange.getResponse().isCommitted()) {


                    //获取所有请求参数
                    MultiValueMap<String, String> paramMap = exchange.getRequest().getQueryParams();

                    List<String> nameList = paramMap.get("name");
                    List<String> tokenList = paramMap.get("token");

                    if(nameList==null || nameList.size()!=1){

                        ServerWebExchangeUtils.setResponseStatus(exchange, httpStatus);
                        ServerHttpResponse response = exchange.getResponse();
                        response.getHeaders().set("Location", uri.toString());
                        return response.setComplete();

                    }

                    if(tokenList==null || tokenList.size()!=1){
                        //没有token需要进行登录
                        ServerWebExchangeUtils.setResponseStatus(exchange, httpStatus);
                        ServerHttpResponse response = exchange.getResponse();
                        response.getHeaders().set("Location", uri.toString());
                        return response.setComplete();
                    }

                    String name = nameList.get(0);

                    String token = tokenList.get(0);

                    System.out.println("name:"+name);
                    System.out.println("token:"+token);



                    System.out.println("校验成功");

                    return chain.filter(exchange);
                } else {
                    return Mono.empty();
                }

            }

            public String toString() {
                String status;
                if (httpStatus.getHttpStatus() != null) {
                    status = String.valueOf(httpStatus.getHttpStatus().value());
                } else {
                    status = httpStatus.getStatus().toString();
                }

                return GatewayToStringStyler.filterToStringCreator(CheckAuthGatewayFilterFactory.this).append(status, uri).toString();
            }
        };
    }

    public static class Config {
        String status;
        String url;

        public Config() {
        }

        public String getStatus() {
            return this.status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
