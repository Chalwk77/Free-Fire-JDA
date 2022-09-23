// Copyright (c) 2022, Jericho Crosby <jericho.crosby227@gmail.com>

package com.jericho.freefre.commands;

import com.jericho.freefre.listeners.CommandInterface;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

import static com.jericho.freefre.Utilities.UpdateHeaders.updateDatabase;
import static com.jericho.freefre.main.database;

public class DeleteHeader implements CommandInterface {

    @Override
    public String getName() {
        return "header_delete";
    }

    @Override
    public String getDescription() {
        return "Delete a URL Header";
    }

    public String getRole() {
        return "Admin";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> data = new ArrayList<>();
        OptionData options = new OptionData(OptionType.STRING, "header", "Delete this header", true);

        for (int i = 0; i < database.length(); i++) {
            for (String key : database.getJSONObject(i).keySet()) {
                if (!key.equals("Name") && !key.equals("Discord ID") && !key.equals("Free Fire ID")) {
                    options.addChoice(key, key);
                }
            }
        }

        data.add(options);
        return data;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) throws IOException {
        if (database.length() == 0) {
            event.reply("**The database is empty! Please opt-in to the database using `/optin` first.**").setEphemeral(true).queue();
        } else {
            for (int i = 0; i < database.length(); i++) {
                for (String header : database.getJSONObject(i).keySet()) {
                    if (!header.equals("Name") && !header.equals("Discord ID") && !header.equals("Free Fire ID")) {
                        OptionMapping options = event.getOption("header");
                        var option_value = options.getAsString();
                        if (option_value.equals(header)) {
                            event.reply("Header (**" + header + "**) has been deleted.").setEphemeral(true).queue();
                            updateDatabase(header, false, "null", "null");
                            return;
                        }
                    }
                }
            }
        }
    }
}
