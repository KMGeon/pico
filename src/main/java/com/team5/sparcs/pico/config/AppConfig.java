package com.team5.sparcs.pico.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Configuration
public class AppConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(10))
                .additionalMessageConverters(new StringHttpMessageConverter(StandardCharsets.UTF_8))
                .additionalMessageConverters(new FormHttpMessageConverter())
                .additionalMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }
//
//    public ClientHttpRequestInterceptor clientHttpRequestInterceptor() {
//        return (request, body, execution) -> {
//            RetryTemplate retryTemplate = new RetryTemplate();
//            retryTemplate.setRetryPolicy(new SimpleRetryPolicy(3));
//            try {
//                return retryTemplate.execute(context -> execution.execute(request, body));
//            } catch (Throwable throwable) {
//                throw new RuntimeException(throwable);
//            }
//        };
//    }
}
