package org.development1.health;

import com.codahale.metrics.health.HealthCheck;
import org.hibernate.SessionFactory;

public class DatabaseHealthCheck extends HealthCheck{

    private final SessionFactory sessionFactory;

    public DatabaseHealthCheck(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    protected Result check() throws Exception{
        try{
            sessionFactory.openSession().close();
            return Result.healthy();
        }catch(Exception e){
            return Result.unhealthy("Unable to establish a connection to the database");
        }
    }
}
