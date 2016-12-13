package df.open.eureka.listener;

import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRegisteredEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRenewedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 说明:
 * <p/>
 * Copyright: Copyright (c)
 * <p/>
 * Company:
 * <p/>
 *
 * @author darren-fu
 * @version 1.0.0
 * @contact 13914793391
 * @date 2016/12/12
 */
@Component
public class EurekaInstanceRenewEventListener implements ApplicationListener<EurekaInstanceRenewedEvent> {
    @Override
    public void onApplicationEvent(EurekaInstanceRenewedEvent event) {
        if (event instanceof EurekaInstanceRenewedEvent) {
            System.out.println("@@@@@@@@@@@@@@@@EurekaInstanceRenewEventListener");
//            System.out.println(event.getInstanceInfo().getAppName());
            System.out.println(event.getSource());
        }

    }
}
