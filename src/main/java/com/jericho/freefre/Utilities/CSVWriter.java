// Copyright (c) 2022, Jericho Crosby <jericho.crosby227@gmail.com>

package com.jericho.freefre.Utilities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static com.jericho.freefre.Utilities.FileIO.getProgramPath;

public class CSVWriter {

    /**
     * Write a CSV file from a JSON file.
     * Code from: <<a href="https://www.baeldung.com/java-converting-json-to-csv">...</a>>
     *
     * @throws IOException if the file cannot be written.
     */

    public static void writeCSV() throws IOException {

        String d = getProgramPath();
        File f = new File(d + "/database.json");

        JsonNode jsonTree = new ObjectMapper().readTree(f);
        if (jsonTree.size() > 0) {

            CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();
            JsonNode firstObject = jsonTree.elements().next();
            firstObject.fieldNames().forEachRemaining(csvSchemaBuilder::addColumn);

            CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();
            CsvMapper csvMapper = new CsvMapper();
            File f2 = new File(d + "/database.scv");
            csvMapper.writerFor(JsonNode.class)
                    .with(csvSchema)
                    .writeValue(f2, jsonTree);
        }
    }
}
