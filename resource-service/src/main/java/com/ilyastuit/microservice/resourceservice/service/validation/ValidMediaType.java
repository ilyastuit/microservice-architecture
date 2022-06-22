package com.ilyastuit.microservice.resourceservice.service.validation;

import org.springframework.http.MediaType;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MediaTypeValidator.class)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMediaType {

    String value() default MediaType.ALL_VALUE;

    String message() default "Not Valid MediaType";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}