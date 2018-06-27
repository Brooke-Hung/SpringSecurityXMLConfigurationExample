package com.springsecurity.demo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Brooke
 * @since JDK1.8
 * @created on 2018/06/27
 */
@Retention(RetentionPolicy.RUNTIME)
@org.springframework.security.test.context.support.WithMockUser(value="Alan",roles="ADMIN")
public @interface WithMockUser {

}
