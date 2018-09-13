package main.java.com.systop.base_1500.service;

import main.java.com.systop.base_1500.dao.UserDao;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("userService")
public class UserService {
    private UserDao userDao;

    public void init(){
        System.out.println("init");
    }

    public void add(UserDao user){
        userDao.save(user);
    }

    public UserDao getUserDao() {
        return userDao;
    }

    @Resource(name = "u")
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
