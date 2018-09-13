package main.java.com.systop.base;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        HelloWorld helloWorld = (HelloWorld)context.getBean("helloWorld");
        helloWorld.sayHello();
    }
}
