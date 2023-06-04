package com.study.configs;

import com.study.configs.interceptors.SiteConfigInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@RequiredArgsConstructor
@EnableJpaAuditing
public class MvcConfig implements WebMvcConfigurer {

    // 사이트 설정 유지 인터셉터
    private final SiteConfigInterceptor siteConfigInterceptor;

    @Value("${file.upload.path}")
    private String fileUploadPath;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/")
                .setViewName("main/index");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file://"+fileUploadPath);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(siteConfigInterceptor)
                .addPathPatterns("/**");
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
        ms.setBasenames("messages.commons", "messages.errors", "messages.validations");
        ms.setDefaultEncoding("UTF-8");

        return ms;
    }

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {    // Get, Post 외의 매핑을 사용하기위한 설정
        return new HiddenHttpMethodFilter();
    }
}