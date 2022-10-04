// Copyright (c) 2022, Jericho Crosby <jericho.crosby227@gmail.com>

package com.jericho.freefre;

import com.jericho.freefre.commands.*;
import com.jericho.freefre.listeners.CommandManager;
import com.jericho.freefre.listeners.EventListeners;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.security.auth.login.LoginException;
import java.io.IOException;

import static com.jericho.freefre.Utilities.FileIO.loadJSONArray;
import static com.jericho.freefre.Utilities.FileIO.loadJSONObject;

public class main {

    public static JSONObject settings;
    public static JSONArray database;

    private final ShardManager shardManager;

    public main() throws LoginException {

        String token = getToken();
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.watching("YOU"));
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT);
        shardManager = builder.build();
        shardManager.addEventListener(new EventListeners());

        CommandManager manager = new CommandManager();
        manager.add(new Link());
        manager.add(new OptIn());
        manager.add(new OptOut());
        manager.add(new Attach());
        manager.add(new AddHeader());
        manager.add(new DeleteHeader());

        shardManager.addEventListener(manager);
    }

    public static void cprint(String message) {
        System.out.println(message);
    }

    // Return the bot token:
    public static String getToken() {
        return String.valueOf(settings.getString("token"));
    }

    public static void loadFiles() {
        try {
            settings = loadJSONObject("settings.json");
            database = loadJSONArray("database.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        loadFiles();
        try {
            new main();
        } catch (LoginException e) {
            cprint("ERROR: Provided bot token is invalid");
        }
    }

    public ShardManager getShardManager() {
        return shardManager;
    }
}
