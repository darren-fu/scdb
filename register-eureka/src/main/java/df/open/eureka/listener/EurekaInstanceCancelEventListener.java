package df.open.eureka.listener;

import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceCanceledEvent;
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
public class EurekaInstanceCancelEventListener implements ApplicationListener<EurekaInstanceCanceledEvent> {
    @Override
    public void onApplicationEvent(EurekaInstanceCanceledEvent event) {

        if(event instanceof EurekaInstanceCanceledEvent){
            System.out.println("$$$$$$$$$$$$$EurekaInstanceCanceledEvent");
        }

    }
}
