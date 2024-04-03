package com.leanix.dropwizard.introduction.configuration;


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
    private final DataSourceFactory database = new DataSourceFactory();

    public DataSourceFactory getDatabaseAppDataSourceFactory() {
        return database;
    }
}
