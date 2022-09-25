package com.jericho.freefre.Utilities;

import java.io.IOException;

import static com.jericho.freefre.Utilities.FileIO.writeJSONFile;
import static com.jericho.freefre.main.cprint;
import static com.jericho.freefre.main.database;

public class UpdateHeaders {

    /**
     * @param header The header to add or remove from database.json.
     * @param empty  Boolean value to determine whether to add or remove the header from database.json.
     * @throws IOException If headers/database.json cannot be found.
     * @apiNote If empty is true, the header object will be removed from database.json.
     */
    public static void updateDatabase(String header, Boolean empty, String newURL, String userID) throws IOException {

        for (int i = 0; i < database.length(); i++) {
            if (empty) {
                database.getJSONObject(i).put(header, "");
            } else if (newURL != null) {
                if (database.getJSONObject(i).getString("Discord ID").equals(userID)) {
                    database.getJSONObject(i).put(header, newURL);
                } else {
                    database.getJSONObject(i).put(header, "");
                }
            } else {
                database.getJSONObject(i).remove(header);
            }
        }

        writeJSONFile(database, "database.json");
    }
}

