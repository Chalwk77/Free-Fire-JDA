// Copyright (c) 2022, Jericho Crosby <jericho.crosby227@gmail.com>

package com.jericho.freefre;

import com.jericho.freefre.commands.Attach;
import com.jericho.freefre.commands.Link;
import com.jericho.freefre.commands.OptOut;
import com.jericho.freefre.listeners.CommandManager;
import com.jericho.freefre.commands.OptIn;
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

    static {
        try {
            settings = loadJSONObject("settings.json");
            database = loadJSONArray("database.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private final ShardManager shardManager;

    // Return the bot token:
    public static String getToken() {
        return String.valueOf(settings.getString("token"));
    }

    public main() throws LoginException {
        String token = getToken();
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.watching("YOU"));
        builder.enableIntents(
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.MESSAGE_CONTENT
        );
        shardManager = builder.build();
        shardManager.addEventListener(new EventListeners());

        CommandManager manager = new CommandManager();
        manager.add(new Link());
        manager.add(new OptIn());
        manager.add(new OptOut());
        manager.add(new Attach());

        shardManager.addEventListener(manager);
    }

    public ShardManager getShardManager() {
        return shardManager;
    }

    public static void main(String[] args) {
        try {
            new main();
        } catch (LoginException e) {
            cprint("ERROR: Provided bot token is invalid");
        }
    }
}
