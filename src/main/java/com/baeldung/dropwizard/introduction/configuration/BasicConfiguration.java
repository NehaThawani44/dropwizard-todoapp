package com.baeldung.dropwizard.introduction.configuration;


import com.baeldung.dropwizard.introduction.resource.ToDoResource;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import io.dropwizard.db.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicConfiguration extends Configuration {
    @NotNull private final int defaultSize;
    private static final Logger LOGGER = LoggerFactory.getLogger(BasicConfiguration.class);
    @JsonCreator
    public BasicConfiguration(@JsonProperty("defaultSize") final int defaultSize) {
        this.defaultSize = defaultSize;
    }

    public int getDefaultSize() {
        return defaultSize;
    }
    
    @Valid
    @NotNull
    @JsonProperty("database")
    private DataSourceFactory database = new DataSourceFactory();

    public DataSourceFactory getDatabaseAppDataSourceFactory() {
        return database;
    }
}
