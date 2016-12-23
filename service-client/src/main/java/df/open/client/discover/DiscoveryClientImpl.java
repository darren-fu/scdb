package df.open.client.discover;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClientConfig;

/**
 * Created by Administrator on 2016/12/19.
 */
public class DiscoveryClientImpl extends DiscoveryClient {
    public DiscoveryClientImpl(ApplicationInfoManager applicationInfoManager, EurekaClientConfig config) {
        super(applicationInfoManager, config);
    }
}
