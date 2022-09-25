// Copyright (c) 2022, Jericho Crosby <jericho.crosby227@gmail.com>

package com.jericho.freefre.commands;

import com.jericho.freefre.listeners.CommandInterface;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.jericho.freefre.Utilities.FileIO.writeJSONFile;
import static com.jericho.freefre.Utilities.UpdateHeaders.updateDatabase;
import static com.jericho.freefre.main.database;

public class AddHeader implements CommandInterface {

    @Override
    public String getName() {
        return "header_add";
    }

    @Override
    public String getDescription() {
        return "Add a new URL Header";
    }

    public String getRole() {
        return "Admin";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> data = new ArrayList<>();
        data.add(new OptionData(OptionType.STRING, "header", "Name of the URL Header to add").setRequired(true));
        return data;
    }

    private static void newHeader(String userID, String header, SlashCommandInteractionEvent event) throws IOException {

        for (int i = 0; i < database.length(); i++) {
            if (database.getJSONObject(i).has(header)) {
                event.reply("**This header already exists.**").setEphemeral(true).queue();
                return;
            }
        }
        event.reply("Header (**" + header + "**) has been added.").setEphemeral(true).queue();
        updateDatabase(header, true, null, null);

        // Disabled code for adding this header as an option for the Link() command:
        // List<OptionData> options = new Link().getOptions();
        // options.get(1).addChoice(header, header);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) throws IOException {
        var header = event.getOption("header").getAsString();
        var userID = event.getUser().getId();
        if (database.length() == 0) {
            event.reply("**The database is empty. Please opt-in to the database using `/optin` first.**").setEphemeral(true).queue();
        } else {

            if (!header.matches("^[\\w-]+$")) {
                event.reply("""
                        **STRING FORMAT ERROR**
                        Due to a Discord API limitation, header names can only contain lowercase letters,
                        numbers, hyphens and underscores.
                        """).setEphemeral(true).queue();
                return;
            }

            newHeader(userID, header, event);
        }
    }
}
