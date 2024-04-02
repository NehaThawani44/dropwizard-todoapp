package com.baeldung.dropwizard.introduction;

import com.baeldung.dropwizard.introduction.configuration.BasicConfiguration;
import com.baeldung.dropwizard.introduction.domain.SubTask;
import com.baeldung.dropwizard.introduction.domain.ToDo;
import com.baeldung.dropwizard.introduction.repository.SubTaskRepository;
import com.baeldung.dropwizard.introduction.repository.ToDoRepository;
import com.baeldung.dropwizard.introduction.resource.SubTaskResource;
import com.baeldung.dropwizard.introduction.resource.ToDoResource;


import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.hibernate.HibernateBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Application extends io.dropwizard.Application<BasicConfiguration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
    public static void main(final String[] args) throws Exception {
        new Application().run(args);
    }
    
  
    private final HibernateBundle<BasicConfiguration> hibernate = new HibernateBundle<BasicConfiguration>(ToDo.class, SubTask.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(BasicConfiguration configuration) {
            return configuration.getDatabaseAppDataSourceFactory();
        }
    };

    @Override
    public String getName() {
        return "dropwizard-hibernate";
    }

    @Override
    public void initialize(Bootstrap<BasicConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(BasicConfiguration configuration, Environment environment) throws ClassNotFoundException {

        final ToDoRepository toDoRepository = new ToDoRepository(hibernate.getSessionFactory());
        final SubTaskRepository subTaskRepository = new SubTaskRepository(hibernate.getSessionFactory());
        final ToDoResource toDoResource = new ToDoResource(5, toDoRepository);
        final SubTaskResource subTaskResource = new SubTaskResource(subTaskRepository);

        environment.jersey().register(toDoResource);
        environment.jersey().register(subTaskResource);
    }
 
}
