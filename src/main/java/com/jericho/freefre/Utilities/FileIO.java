// Copyright (c) 2022, Jericho Crosby <jericho.crosby227@gmail.com>

package com.jericho.freefre.Utilities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

import static com.jericho.freefre.Utilities.CSVWriter.writeCSV;

public class FileIO {

    // Path to resources file:
    private static final String path = "src/main/resources/";

    //
    // Load a JSON Array from file:
    //
    public static JSONArray loadJSONArray(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path + file));
        String line = reader.readLine();
        StringBuilder stringBuilder = new StringBuilder();
        while (line != null) {
            stringBuilder.append(line);
            line = reader.readLine();
        }
        String content = stringBuilder.toString();
        if (content.equals("")) {
            return new JSONArray();
        } else {
            return new JSONArray(content);
        }
    }

    //
    // Load a JSON Object from file:
    //
    public static JSONObject loadJSONObject(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path + file));
        String line = reader.readLine();
        StringBuilder stringBuilder = new StringBuilder();
        while (line != null) {
            stringBuilder.append(line);
            line = reader.readLine();
        }
        String content = stringBuilder.toString();
        if (content.equals("")) {
            return new JSONObject();
        } else {
            return new JSONObject(content);
        }
    }

    //
    // Write to JSON file:
    //
    public static void writeJSONFile(JSONArray json, String file) throws IOException {
        FileWriter fileWriter = new FileWriter(path + file);
        fileWriter.write(json.toString(4));
        fileWriter.flush();
        fileWriter.close();

        writeCSV();
    }
}
