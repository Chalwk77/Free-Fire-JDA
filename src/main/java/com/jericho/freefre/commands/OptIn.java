// Copyright (c) 2022, Jericho Crosby <jericho.crosby227@gmail.com>

package com.jericho.freefre.commands;

import com.jericho.freefre.listeners.CommandInterface;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.jericho.freefre.Utilities.FileIO.writeJSONFile;
import static com.jericho.freefre.main.*;

public class OptIn implements CommandInterface {

    @Override
    public String getName() {
        return "optin";
    }

    @Override
    public String getDescription() {
        return "Opt-in to the FF ID database";
    }

    public String getRole() {
        return "Guest";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> data = new ArrayList<>();
        OptionData optin = new OptionData(OptionType.STRING, "confirm", "Confirm yes/no", true);
        optin.addChoice("yes", "yes");
        optin.addChoice("no", "no");
        data.add(optin);
        return data;
    }

    private static void newOptin(String userID, String userName, SlashCommandInteractionEvent event) throws IOException {

        if (event.getOption("confirm").getAsString().equals("yes")) {

            JSONObject user = new JSONObject();

            user.put("Free Fire ID", "Not Set");
            user.put("Name", userName);
            user.put("Discord ID", userID);
            database.put(user);

            writeJSONFile(database, "database.json");
            event.reply("You have been added to the database.").setEphemeral(true).queue();

        } else {
            event.reply("You have not been opted in.").setEphemeral(true).queue();
        }
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) throws IOException {

        var member = event.getMember();
        var username = member.getEffectiveName();
        var userID = event.getUser().getId();

        if (database.length() == 0) {
            newOptin(userID, username, event);
        } else {

            for (int i = 0; i < database.length(); i++) {
                if (database.getJSONObject(i).getString("Discord ID").equals(userID)) {
                    event.reply("You are already in the database!").setEphemeral(true).queue();
                    return;
                }
            }

            newOptin(userID, username, event);
        }
    }
}
