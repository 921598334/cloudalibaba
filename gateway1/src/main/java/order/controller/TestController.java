package order.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class TestController {

//    @Autowired
//    private RestTemplate restTemplate;

    @Value("${user.name}")
    String name;

    @RequestMapping("/order")
    public String orderTest() throws Exception {



        System.out.println(name);
        return "rr";
    }



}
