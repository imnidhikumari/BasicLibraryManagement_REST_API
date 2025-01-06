package org.development1.dao;

import org.development1.entity.Book;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

public class HibernateConfig {

    private final String jdbcUrl;
    private final String driverClass;
    private final String user;
    private final String password;

    public HibernateConfig(String jdbcUrl, String driverClass, String user, String password){
        this.jdbcUrl=jdbcUrl;
        this.driverClass=driverClass;
        this.user=user;
        this.password=password;
    }

    public SessionFactory buildSessionFactory(){
        Configuration configuration = new Configuration();

        configuration.setProperty("hibernate.connection.driver_class",driverClass);
        configuration.setProperty("hibernate.connection.url",jdbcUrl);
        configuration.setProperty("hibernate.connection.user",user);
        configuration.setProperty("hibernate.connection.password",password);

        configuration.addAnnotatedClass(Book.class);

        return configuration.buildSessionFactory();
    }

    public SessionFactory getSessionFactory(){ return buildSessionFactory(); }

}
