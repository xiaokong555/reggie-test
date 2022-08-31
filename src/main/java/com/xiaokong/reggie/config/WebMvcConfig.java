package com.xiaokong.reggie.config;

import com.xiaokong.reggie.common.JacksonObjectMapper;
import com.xiaokong.reggie.interceptor.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * ClassName WebMvcConfig
 * Description Spring-MVC的相关配置
 * Author Mrk
 * Date 2022/8/20 21:06
 * Version 1.0
 */
@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    /*
     * @Description: 配置静态资源的映射(backend/front)
     * @Param registry: 资源映射操作柄
     * @return: void
     **/
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 记得本地资源路径后加 ==> '/'
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
    }

    /*
     * @Description: 扩展SPRING-MVC框架的消息转换器
     * @Param converters:
     * @return: void
     **/
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 创建新的消息转换器对象
        MappingJackson2HttpMessageConverter mj2 = new MappingJackson2HttpMessageConverter();
        mj2.setObjectMapper(new JacksonObjectMapper());

        // 将自建的消息转换器追加到容器中
        converters.add(0, mj2); // 索引,对象
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/", "/backend/**", "/fromt/**", "/employee/login", "/employee/logout",
                                     "/common/**");
    }
}
