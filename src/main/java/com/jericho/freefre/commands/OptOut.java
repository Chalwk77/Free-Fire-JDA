// Copyright (c) 2022, Jericho Crosby <jericho.crosby227@gmail.com>

package com.jericho.freefre.commands;

import com.jericho.freefre.listeners.CommandInterface;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

import static com.jericho.freefre.main.database;

public class OptOut implements CommandInterface {

    @Override
    public String getName() {
        return "optout";
    }

    @Override
    public String getDescription() {
        return "Opt-out of the FF ID database";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> data = new ArrayList<>();
        OptionData optout = new OptionData(OptionType.STRING, "confirm", "Confirm yes/no", true);
        optout.addChoice("yes", "yes");
        optout.addChoice("no", "no");
        data.add(optout);
        return data;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        var userID = event.getUser().getId();
        if (database.length() == 0) {
            event.reply("You are not opted in.").setEphemeral(true).queue();
        } else {
            for (int i = 0; i < database.length(); i++) {
                if (database.getJSONObject(i).getString("Discord ID").equals(userID)) {
                    if (event.getOption("confirm").getAsString().equals("yes")) {
                        database.remove(i);
                        event.reply("You have been opted out.").setEphemeral(true).queue();
                    } else {
                        event.reply("You have not been opted out.").setEphemeral(true).queue();
                    }
                    return;
                } else {
                    event.reply("You need to opt-in first.").setEphemeral(true).queue();
                }
            }
        }
    }
}
