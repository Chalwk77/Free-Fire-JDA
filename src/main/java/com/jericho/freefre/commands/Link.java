// Copyright (c) 2022, Jericho Crosby <jericho.crosby227@gmail.com>

package com.jericho.freefre.commands;

import com.jericho.freefre.listeners.CommandInterface;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.jericho.freefre.Utilities.UpdateHeaders.updateDatabase;
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

    public String getRole() {
        return "Guest";
    }

    @Override
    public List<OptionData> getOptions() {

        List<OptionData> data = new ArrayList<>();
        OptionData url = new OptionData(OptionType.STRING, "url", "URL to link.", true);
        OptionData options = new OptionData(OptionType.STRING, "header", "Add URL to this header.", true);

        for (int i = 0; i < database.length(); i++) {
            for (String key : database.getJSONObject(i).keySet()) {
                if (!key.equals("Name") && !key.equals("Discord ID") && !key.equals("Free Fire ID")) {
                    options.addChoice(key, key);
                }
            }
        }

        data.add(url);
        data.add(options);
        return data;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) throws IOException {

        if (database.length() == 0) {
            event.reply("**The database is empty. Please opt-in first.**").setEphemeral(true).queue();
        } else {

            var url = event.getOption("url").getAsString();

            for (int i = 0; i < database.length(); i++) {
                for (String header : database.getJSONObject(i).keySet()) {
                    if (!header.equals("Name") && !header.equals("Discord ID") && !header.equals("Free Fire ID")) {
                        OptionMapping options = event.getOption("header");
                        var option_value = options.getAsString();
                        if (option_value.equals(header)) {
                            event.reply("URL (**" + url + "**) has been added to header (**" + header + "**).").setEphemeral(true).queue();
                            updateDatabase(header, false, url, event.getUser().getId());
                            return;
                        }
                    }
                }
            }
        }
    }
}
