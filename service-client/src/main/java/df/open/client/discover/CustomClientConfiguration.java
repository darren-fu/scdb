package df.open.client.discover;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.discovery.EurekaClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2016/12/19.
 */

@Configuration
@AutoConfigureAfter(EurekaClientAutoConfiguration.class)
public class CustomClientConfiguration {

    @Autowired
    private ApplicationInfoManager applicationInfoManager;

    @Autowired
    private EurekaInstanceConfig eurekaInstanceConfig;

    @Autowired
    private EurekaClientConfig eurekaClientConfig;
    @Autowired
    private ApplicationContext context;

    @Bean
    public RefreshClient refreshClient(ApplicationInfoManager applicationInfoManager,
                                       EurekaClientConfig eurekaClientConfig, EurekaInstanceConfig eurekaInstanceConfig){
       return  new RefreshClient(applicationInfoManager, eurekaClientConfig, context);
    }

}
