package com.ilyastuit.microservices.resourceprocessor.config;

import feign.Response;
import feign.RetryableException;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class FeignClientConfiguration {

    @Bean
    public Retryer retryer() {
        return new Retryer.Default(1000L, 2000L, 10);
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new HttpErrorDecoder();
    }

    public static class HttpErrorDecoder implements ErrorDecoder {
        private final ErrorDecoder defaultErrorDecoder = new Default();

        @Override
        public Exception decode(String methodKey, Response response) {
            Exception exception = defaultErrorDecoder.decode(methodKey, response);
            if (response.status() != HttpStatus.OK.value()) {
                return new RetryableException(
                        response.status(),
                        "Api call for url: %s failed".formatted(response.request().url()),
                        response.request().httpMethod(),
                        exception.getCause(),
                        null,
                        response.request()
                );
            }
            return exception;
        }
    }

}
