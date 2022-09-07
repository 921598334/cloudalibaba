package order.predicates;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;

import org.springframework.http.server.RequestPath;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/***
 *  断言可以用来过滤简单的请求，例如用户登录时，如果用户名为空可以直接过滤（用的少，其实可以直接再过滤器中使用）
    判断请求参数
 */
@Component
public class CheckAuthRoutePredicateFactory extends AbstractRoutePredicateFactory<CheckAuthRoutePredicateFactory.Config> {


    //用户请求参数
    public static final String PARAM_KEY = "param";
    //期望值，这里用不到
    public static final String REGEXP_KEY = "regexp";

    public CheckAuthRoutePredicateFactory() {
        super(CheckAuthRoutePredicateFactory.Config.class);
    }

    public List<String> shortcutFieldOrder() {
        return Arrays.asList("param", "regexp");
    }

    //判断用户有没有登录
    public Predicate<ServerWebExchange> apply(CheckAuthRoutePredicateFactory.Config config) {
        return new GatewayPredicate() {
            public boolean test(ServerWebExchange exchange) {

                System.out.println("===========开始校验用户名是否为空==============");



                //获取所有请求参数
                MultiValueMap<String, String> paramMap = exchange.getRequest().getQueryParams();

                List<String> nameList = paramMap.get("name");
                List<String> tokenList = paramMap.get("token");

                if(nameList==null || nameList.size()!=1){
                    return false;
                }

                if(tokenList==null || tokenList.size()!=1){
                    //没有token需要进行登录
                    return false;
                }

                String name = nameList.get(0);

                String token = tokenList.get(0);

                System.out.println("name:"+name);
                System.out.println("token:"+token);



                System.out.println("校验成功");
                return  true;

            }

            public String toString() {
                return String.format("CheckAuth: param=%s regexp=%s", config.getParam(), config.getRegexp());
            }
        };
    }


    //得到再application中设置的值
    @Validated
    public static class Config {
        @NotEmpty
        private String param;
        private String regexp;

        public Config() {
        }

        public String getParam() {
            return this.param;
        }

        public CheckAuthRoutePredicateFactory.Config setParam(String param) {
            this.param = param;
            return this;
        }

        public String getRegexp() {
            return this.regexp;
        }

        public CheckAuthRoutePredicateFactory.Config setRegexp(String regexp) {
            this.regexp = regexp;
            return this;
        }
    }
}
