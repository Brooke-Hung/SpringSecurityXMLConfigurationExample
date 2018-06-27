package com.springsecurity.demo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.test.context.support.WithMockUser;

/**
 * @author Brooke
 * @since JDK1.8
 * @created on 2018/06/27
 */
@Retention(RetentionPolicy.RUNTIME)
@WithMockUser(value="Alex",authorities="WRITE")
public @interface WithWriteAuthority {

}
