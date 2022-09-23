// Copyright (c) 2022, Jericho Crosby <jericho.crosby227@gmail.com>

package com.jericho.freefre.commands;

import com.jericho.freefre.listeners.CommandInterface;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void execute(SlashCommandInteractionEvent event) throws IOException {

        var header = event.getOption("header").getAsString();
        if (database.length() == 0) {
            event.reply("**The database is empty. Please opt-in to the database using `/optin` first.**").setEphemeral(true).queue();
        } else {

            for (int i = 0; i < database.length(); i++) {
                if (database.getJSONObject(i).has(header)) {
                    event.reply("**This header already exists.**").setEphemeral(true).queue();
                    return;
                }
            }

            if (header.matches("^[\\w-]+$")) {
                event.reply("Header (**" + header + "**) has been added.").setEphemeral(true).queue();
                updateDatabase(header, true, "null", event.getUser().getId());
            } else {
                event.reply("**Due to a Discord API limitation, header names must only contain lowercase letters, numbers, hyphens and underscores.**").setEphemeral(true).queue();
            }
        }
    }
}
