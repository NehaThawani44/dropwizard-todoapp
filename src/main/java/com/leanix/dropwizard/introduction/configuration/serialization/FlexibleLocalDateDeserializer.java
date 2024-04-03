package com.leanix.dropwizard.introduction.configuration.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.leanix.dropwizard.introduction.configuration.ApplicationHealthCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FlexibleLocalDateDeserializer extends JsonDeserializer<LocalDate> {

    private static final DateTimeFormatter GERMAN_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final Logger LOGGER = LoggerFactory.getLogger(FlexibleLocalDateDeserializer.class);

    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        String dateString = node.asText();
        try {
            return LocalDate.parse(dateString, GERMAN_FORMATTER);
        } catch (DateTimeParseException e) {
            try {
                return LocalDate.parse(dateString, ISO_FORMATTER);
            } catch (DateTimeParseException ex) {
                throw new RuntimeException("Failed to parse date. Expected formats are [dd.MM.yyyy] or [yyyy-MM-dd].");
            }
        }
    }


}


