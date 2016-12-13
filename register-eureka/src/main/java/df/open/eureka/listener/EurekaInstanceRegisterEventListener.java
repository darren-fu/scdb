package df.open.eureka.listener;

import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRegisteredEvent;
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
public class EurekaInstanceRegisterEventListener implements ApplicationListener<EurekaInstanceRegisteredEvent> {
    @Override
    public void onApplicationEvent(EurekaInstanceRegisteredEvent event) {
        if (event instanceof EurekaInstanceRegisteredEvent) {
            System.out.println("##################EurekaInstanceRegisterEventListener");
            System.out.println(event.getInstanceInfo().getAppName());
            System.out.println(event.getSource());
        }   else{
            System.out.println("@@@@@@" + event.getClass());
        }

    }
}
