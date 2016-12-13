package df.open.server.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.eureka.CloudEurekaInstanceConfig;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Properties;

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
@RestController
@RequestMapping("/server")
@Api(value = "基础接口")
public class BaseApi {

    @Value("${name:default}")
    private String name;

    @Value("${version:1.0.0}")
    private String version;

    @Autowired
    private CloudEurekaInstanceConfig cloudEurekaInstanceConfig;

    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    @ApiOperation(value = "打个招呼", notes = "都可以调用")
    public String sayHi() {

        System.out.println("getInstanceId: " + cloudEurekaInstanceConfig.getInstanceId());
        System.out.println("getAppname: " + cloudEurekaInstanceConfig.getAppname());
        System.out.println("getSecurePort: " + cloudEurekaInstanceConfig.getSecurePort());
        System.out.println("getNonSecurePort: " + cloudEurekaInstanceConfig.getNonSecurePort());
        return "hey, nice to meet you!";
    }

    @RequestMapping(value = "/name", method = RequestMethod.GET)
    public String getProperties() {
        return "name:" + name + ";version:" + version;
    }


    public static void main(String[] args) throws IOException {
        String jarPath = BaseApi.class.getProtectionDomain().getCodeSource().getLocation().getFile();

        System.out.println("jarPath:" + jarPath);

        Properties properties = new Properties();
        properties.load(BaseApi.class.getResourceAsStream("/META-INF/maven/df.open.scdb/service-server/pom.properties"));

        System.out.println(properties.getProperty("version"));
    }

}
