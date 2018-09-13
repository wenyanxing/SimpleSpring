package main.java.com.systop.base_1500.dao.impl;

import main.java.com.systop.base_1500.dao.UserDao;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;

@Component("u")
public class UserDAOImpl implements UserDao {

    private DataSource dataSource;

    public DataSource getDataSource() {
        return dataSource;
    }

    @Resource
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(UserDao user) {
        try {
            Connection connection = dataSource.getConnection();
            connection.createStatement().executeUpdate("insert into user values (null, 'zhangsan')");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("user save");
    }
}
