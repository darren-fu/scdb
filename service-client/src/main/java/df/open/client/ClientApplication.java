package df.open.client;

import com.netflix.loadbalancer.ILoadBalancer;
import df.open.client.service.ServerCallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerContext;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * 说明:
 * <p/>
 * Copyright: Copyright (c)
 * <p/>
 * Company:
 * <p/>
 *
 * @author 付亮(darrenfu)
 * @version 1.0.0
 * @date 2016/11/8
 */

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableEurekaClient
@RestController
@EnableFeignClients
public class ClientApplication {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private ServerCallService serverCallService;

    @Autowired
    private SpringClientFactory springClientFactory;

//    @Autowired
//    private RibbonLoadBalancerContext ribbonLoadBalancerContext;

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }


    @RequestMapping("/hello")
    public String hello() {

        System.out.println(Arrays.toString(discoveryClient.getServices().toArray()));

        System.out.println(springClientFactory.getLoadBalancer("server").getAllServers().size());
        System.out.println(springClientFactory.getLoadBalancer("client").getAllServers().size());

        serverCallService.login();
        return "hello";
    }

    @RequestMapping("/rest")
    public String rest() {

//        System.out.println("client name:" + ribbonLoadBalancerContext.getClientName());
//        ILoadBalancer loadBalancer = ribbonLoadBalancerContext.getLoadBalancer();
        String res = restTemplate.getForEntity("http://SERVER/server/name", String.class).getBody();


        return res;
    }


    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
