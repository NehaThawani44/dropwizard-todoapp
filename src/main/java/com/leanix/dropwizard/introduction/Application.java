package com.leanix.dropwizard.introduction;

import com.leanix.dropwizard.introduction.configuration.BasicConfiguration;
import com.leanix.dropwizard.introduction.domain.SubTask;
import com.leanix.dropwizard.introduction.domain.ToDo;
import com.leanix.dropwizard.introduction.domain.User;
import com.leanix.dropwizard.introduction.repository.SubTaskRepository;
import com.leanix.dropwizard.introduction.repository.ToDoRepository;
import com.leanix.dropwizard.introduction.resource.SubTaskResource;
import com.leanix.dropwizard.introduction.resource.ToDoResource;


import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.hibernate.HibernateBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Application extends io.dropwizard.Application<BasicConfiguration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
    public static void main(final String[] args) throws Exception {
        LOGGER.info("Starting application");
        try {
            new Application().run(args);
        } catch (Exception e) {
            LOGGER.error("Failed to start the application", e);
            throw e;
        }
    }
    
  
    private final HibernateBundle<BasicConfiguration> hibernate = new HibernateBundle<BasicConfiguration>(ToDo.class, SubTask.class, User.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(BasicConfiguration configuration) {
            DataSourceFactory dsf = configuration.getDatabaseAppDataSourceFactory();
            LOGGER.info("Configuring database with URL: {}", dsf.getUrl());
            return dsf;
        }
    };

    @Override
    public String getName() {
        return "dropwizard-hibernate";
    }

    @Override
    public void initialize(Bootstrap<BasicConfiguration> bootstrap) {
        LOGGER.info("Initializing application");
        bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(BasicConfiguration configuration, Environment environment) throws ClassNotFoundException {
        LOGGER.info("Running application");
        LOGGER.info("Initializing application components");
        try {
            final ToDoRepository toDoRepository = new ToDoRepository(hibernate.getSessionFactory());
            final SubTaskRepository subTaskRepository = new SubTaskRepository(hibernate.getSessionFactory());
            final ToDoResource toDoResource = new ToDoResource(5, toDoRepository);
            final SubTaskResource subTaskResource = new SubTaskResource(subTaskRepository);

            environment.jersey().register(toDoResource);
            environment.jersey().register(subTaskResource);
            LOGGER.info("Application components initialized successfully");
        } catch (Exception e) {
            LOGGER.error("Failed to initialize application components", e);
            throw new RuntimeException("Failed to start the application due to initialization error", e);
        }
    }
}
