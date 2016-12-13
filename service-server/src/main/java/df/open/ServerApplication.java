package df.open;

import com.netflix.appinfo.EurekaInstanceConfig;
import df.open.support.event.ApplicationRefresnListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@EnableAutoConfiguration
@RestController
public class ServerApplication implements EmbeddedServletContainerCustomizer {

    @Autowired
    private EurekaInstanceConfig eurekaInstanceConfig;

    public static void main(String[] args) {
//        SpringApplication.run(ServerApplication.class, new String[]{"--debug"});
        SpringApplicationBuilder appBuilder = new SpringApplicationBuilder();
        appBuilder.sources(ServerApplication.class);

        appBuilder.run();
    }

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
//        container.setPort(7200);
    }


    @RequestMapping("/test")
    public String test() {
        System.out.println(eurekaInstanceConfig.getInstanceId());
        System.out.println(Arrays.toString(eurekaInstanceConfig.getMetadataMap().keySet().toArray()));
        System.out.println(Arrays.toString(eurekaInstanceConfig.getMetadataMap().values().toArray()));

        System.out.println("current version: " + ServerApplication.class.getPackage().getImplementationVersion());
        System.out.println("current title: " + ServerApplication.class.getPackage().getImplementationTitle());
        System.out.println("common version: " + ApplicationRefresnListener.class.getPackage().getImplementationVersion());
        System.out.println("common title: " + ApplicationRefresnListener.class.getPackage().getImplementationTitle());

        Package pack = Package.getPackage("df.open.server");
        System.out.println(pack.getImplementationVersion());
        return "done";
    }


}
