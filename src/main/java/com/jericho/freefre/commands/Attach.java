// Copyright (c) 2022, Jericho Crosby <jericho.crosby227@gmail.com>

package com.jericho.freefre.commands;

import com.jericho.freefre.listeners.CommandInterface;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.jericho.freefre.Utilities.FileIO.writeJSONFile;
import static com.jericho.freefre.main.database;

public class Attach implements CommandInterface {

    @Override
    public String getName() {
        return "attach";
    }

    @Override
    public String getDescription() {
        return "Attach an FF ID to a users database entry";
    }

    public String getRole() {
        return "Guest";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> data = new ArrayList<>();
        data.add(new OptionData(OptionType.STRING, "ff_id", "Free Fire ID").setRequired(true));
        return data;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) throws IOException {

        if (database.length() == 0) {
            event.reply("**The database is empty. Please opt-in to the database using `/optin` first.**").setEphemeral(true).queue();
        } else {
            var userID = event.getUser().getId();

            String ff_id = event.getOption("ff_id").getAsString();

            // Make sure the option is a number:
            if (ff_id.matches("[0-9]+")) {
                // Check if the user is already in the database:
                for (int i = 0; i < database.length(); i++) {
                    if (database.getJSONObject(i).getString("Discord ID").equals(userID)) {
                        database.getJSONObject(i).put("Free Fire ID", ff_id);
                        event.reply("Your Free Fire ID has been updated to **" + ff_id + "**.").setEphemeral(true).queue();
                        writeJSONFile(database, "database.json");
                        return;
                    } else {
                        event.reply("**You need to opt-in first.**").setEphemeral(true).queue();
                    }
                }
            } else {
                event.reply("**Please enter a valid Free Fire ID.**").setEphemeral(true).queue();
            }
        }
    }
}