package main.java.com.systop.base_1500.aop;

import org.springframework.stereotype.Component;


@Component
public class LogInterceptor {
    public void myMethod() { };

    public void before() {
        System.out.println("before");
    }


}
