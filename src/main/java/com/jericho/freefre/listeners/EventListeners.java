// Copyright (c) 2022, Jericho Crosby <jericho.crosby227@gmail.com>

package com.jericho.freefre.listeners;

import com.jericho.freefre.main;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class EventListeners extends ListenerAdapter {

    @Override
    public void onGuildReady(@Nonnull GuildReadyEvent event) {
        main.cprint("Guild ready: " + event.getGuild().getName());
        main.cprint("Bot name: " + event.getJDA().getSelfUser().getName());
    }
}