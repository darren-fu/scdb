package df.open.support.configuration;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import io.swagger.annotations.Api;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.RequestHandlerSelectors.withClassAnnotation;
import static springfox.documentation.builders.RequestHandlerSelectors.withMethodAnnotation;

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
@Configuration
@EnableSwagger2
@ConfigurationProperties(prefix = "df.open.swagger")
@ConditionalOnMissingBean(Docket.class)
@Data
public class SwaggerConfiguration {

    private String path;

    @Bean
    public Docket swaggerApi() {
        System.out.println("初始化Swagger-api");
        //selector 过滤类
        Predicate<RequestHandler> selector = Predicates.or(
                (withClassAnnotation(Api.class)),
                (withMethodAnnotation(Api.class)));
        //RequestHandlerSelectors.any()
        //RequestHandlerSelectors.basePackage("com.example.controller")

        //path  过滤 RequestMapping 的路径
        Predicate<String> apiPath = StringUtils.isEmpty(path) ? PathSelectors.any() : PathSelectors.ant(path);
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Base")
                .select()  // 选择那些路径和api会生成document
                .apis(selector) // 对所有api进行监控
                .paths(apiPath) // 对所有路径进行监控
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "API服务",
                "Some custom description of API.",
                "API TOS",
                "Terms of service",
                "myeaddress@company.com",
                "License of API",
                "API license URL");
        return apiInfo;
    }
}
