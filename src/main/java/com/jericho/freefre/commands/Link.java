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

public class Link implements CommandInterface {

    @Override
    public String getName() {
        return "link";
    }

    @Override
    public String getDescription() {
        return "Link a new url entry";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> data = new ArrayList<>();
        data.add(new OptionData(OptionType.STRING, "link", "URL to add").setRequired(true));
        return data;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) throws IOException {

        var userID = event.getUser().getId();
        if (database.length() == 0) {
            event.reply("The database is empty. Please opt-in first.").setEphemeral(true).queue();
        } else {
            for (int i = 0; i < database.length(); i++) {
                if (database.getJSONObject(i).getString("Discord ID").equals(userID)) {
                    var links = database.getJSONObject(i).getJSONArray("Links");
                    var link = event.getOption("link").getAsString();
                    links.put(link);
                    writeJSONFile(database, "database.json");
                    event.reply("Your link has been added to the database.").setEphemeral(true).queue();
                    return;
                } else {
                    event.reply("You need to opt-in first.").setEphemeral(true).queue();
                }
            }
        }
    }
}
