package org.development1;

import org.development1.dao.BookDAO;
import org.development1.resources.BookResource;
import org.development1.health.DatabaseHealthCheck;
import org.development1.health.DatabaseHealthCheck;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import org.hibernate.SessionFactory;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import javax.inject.Singleton;
import com.codahale.metrics.MetricRegistry;


public class LibraryApplication extends Application<LibraryConfiguration> {

    private static final Logger LOGGER= LoggerFactory.getLogger(LibraryApplication.class);

    public static void main(String[] args) throws Exception {
        new LibraryApplication().run(args);
    }

    @Override
    public String getName() {
        return "LibraryManagement";
    }


    @Override
    public void initialize(Bootstrap<LibraryConfiguration> bootstrap)
        {
            //logic if required
        }

    @Override
    public void run(LibraryConfiguration configuration, Environment environment)
        {
            System.out.println("Hello, Dropwizard!");

            final SessionFactory sessionFactory = configuration.getHibernate().buildSessionFactory();
            final MetricRegistry metricRegistry = environment.metrics();

            //DAOs
            final BookDAO bookDAO = new BookDAO(sessionFactory);

            //Resources
            final BookResource bookResource = new BookResource(bookDAO, metricRegistry);

            environment.jersey().register(new AbstractBinder() {
                @Override
                protected void configure() {
                    bind(sessionFactory).to(SessionFactory.class);
                    bind(bookDAO).to(BookDAO.class).in(Singleton.class);
                    bind(bookResource).to(BookResource.class).in(Singleton.class);
                }
            });

            //Registering Resources
            environment.jersey().register(bookResource);
            environment.jersey().register(RolesAllowedDynamicFeature.class);

            //Health Checks
            environment.healthChecks().register("database", new DatabaseHealthCheck(sessionFactory));

            LOGGER.info("Library Management Application startted successfully");

        }



    }
