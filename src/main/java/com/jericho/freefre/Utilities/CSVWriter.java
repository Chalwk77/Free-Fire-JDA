// Copyright (c) 2022, Jericho Crosby <jericho.crosby227@gmail.com>

package com.jericho.freefre.Utilities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.IOException;

public class CSVWriter {

    /**
     * Write a CSV file from a JSON file.
     * Code from: <<a href="https://www.baeldung.com/java-converting-json-to-csv">...</a>>
     *
     * @throws IOException if the file cannot be written.
     */

    public static void writeCSV() throws IOException {

        JsonNode jsonTree = new ObjectMapper().readTree(new File("src/main/resources/database.json"));
        if (jsonTree.size() > 0) {

            CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();
            JsonNode firstObject = jsonTree.elements().next();
            firstObject.fieldNames().forEachRemaining(csvSchemaBuilder::addColumn);

            CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();
            CsvMapper csvMapper = new CsvMapper();
            csvMapper.writerFor(JsonNode.class)
                    .with(csvSchema)
                    .writeValue(new File("src/main/resources/database.csv"), jsonTree);
        }
    }
}
