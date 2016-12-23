package df.open.client;

import com.netflix.loadbalancer.ILoadBalancer;
import df.open.client.discover.RefreshClient;
import df.open.client.service.ServerCallService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerContext;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

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
public class ClientApplication implements ApplicationContextAware {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private ServerCallService serverCallService;

    @Autowired
    private SpringClientFactory springClientFactory;


    private ApplicationContext applicationContext;

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

        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(FeignClient.class);
        System.out.println(beansWithAnnotation.size());
        //HardCodedTarget(type=ServerCallService, name=server, url=http://server)
        System.out.println(beansWithAnnotation.get("df.open.client.service.ServerCallService").toString());

        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        System.out.println(Arrays.toString(beanDefinitionNames));

        String[] beanNamesForAnnotation = applicationContext.getBeanNamesForAnnotation(FeignClient.class);
        //[df.open.client.service.ServerCallService]
        System.out.println(Arrays.toString(beanNamesForAnnotation));

        String[] aliases = applicationContext.getAliases("df.open.client.service.ServerCallService");
        // serverFeignClient
        System.out.println(Arrays.toString(aliases));

        return "hello";
    }

    @RequestMapping("/rest")
    public String rest() {

//        System.out.println("client name:" + ribbonLoadBalancerContext.getClientName());
//        ILoadBalancer loadBalancer = ribbonLoadBalancerContext.getLoadBalancer();
        String res = restTemplate.getForEntity("http://SERVER/server/name", String.class).getBody();


        return res;
    }


    @Autowired
    private RefreshClient refreshClient;

    @RequestMapping(value = "/fresh", method = RequestMethod.GET)
    public String fresh(){
        System.out.println("applicationContext:" + applicationContext.getClass());
        System.out.println("applicationContext:" + (applicationContext instanceof ConfigurableApplicationContext));
//        ((ConfigurableApplicationContext)applicationContext).refresh();
        try {
            refreshClient.refreshRegistry();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        CompletableFuture completableFuture = CompletableFuture.runAsync(discoveryClient.refreshRegistry());

        return "refresh";
    }


    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
