package main.java.com.systop.base.service;

import main.java.com.systop.base.model.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserServiceTest {
    public  void testAdd() throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        UserService service = (UserService) context.getBean("userService");
        System.out.println(service.getClass());
        service.add(new User());
        context.destroy();
    }
}
