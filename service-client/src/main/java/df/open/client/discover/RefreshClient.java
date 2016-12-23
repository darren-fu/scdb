package df.open.client.discover;

import com.netflix.appinfo.AmazonInfo;
import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.DataCenterInfo;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.*;
import com.netflix.discovery.endpoint.EndpointUtils;
import com.netflix.discovery.shared.Applications;
import com.netflix.discovery.shared.transport.EurekaTransportConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.CloudEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Administrator on 2016/12/19.
 */
@Component
public class RefreshClient implements ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(RefreshClient.class);

//    @Autowired
//    private DiscoveryClient discoveryClient;

    //    private EurekaClient eurekaClient;
    private ApplicationContext applicationContext;

    private DiscoveryClient discoveryClient;


    public RefreshClient(ApplicationInfoManager applicationInfoManager, EurekaClientConfig eurekaClientConfig, ApplicationContext context) {
        DiscoveryClient cloudEurekaClient = new DiscoveryClient(applicationInfoManager, eurekaClientConfig, null);
        this.discoveryClient = cloudEurekaClient;
    }

    public void refreshRegistry() {

        System.out.println(this.discoveryClient);

        try {
            Method refreshRegistry = DiscoveryClient.class.getDeclaredMethod("refreshRegistry");
            refreshRegistry.setAccessible(true);
            refreshRegistry.invoke(this.discoveryClient);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        System.out.println("########");

    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
