package org.development1;

import org.development1.dao.HibernateConfig;
import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Objects;

public class LibraryConfiguration extends Configuration {

    @JsonProperty("database")
    private Map<String,String> database;

    public Map<String,String> getDatabase(){
        return database;
    }

    public void setDatabase(Map<String,String> database){
        this.database = database;
    }

    public HibernateConfig getHibernate(){
        if(database == null){
            throw new IllegalStateException("Database configuration is not set in config.yaml");
        }

        return new HibernateConfig(
                Objects.requireNonNull(database.get("url"),"Database url is not set in config.yaml"),
                Objects.requireNonNull(database.get("driverClass"),"Database driverClass is not set in config.yaml"),
                Objects.requireNonNull(database.get("user"),"Database user is not set in config.yaml"),
                Objects.requireNonNull(database.get("password"),"Database password is not set in config.yaml")
        );
    }

    private static <T> T requiredNonNull(T value, String message){
        if(value == null){
            throw new IllegalStateException();
        }
        return value;
    }

}
