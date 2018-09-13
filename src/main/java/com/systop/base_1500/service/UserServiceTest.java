package main.java.com.systop.base_1500.service;

import main.java.com.systop.base_1500.model.User;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserServiceTest {
    @Test
    public  void testAdd() throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        UserService service = (UserService) context.getBean("userService");
        System.out.println(service.getClass());
        service.add(new User());
        context.destroy();
    }
}
