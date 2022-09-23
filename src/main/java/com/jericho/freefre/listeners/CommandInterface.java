// Copyright (c) 2022, Jericho Crosby <jericho.crosby227@gmail.com>

package com.jericho.freefre.listeners;

import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.io.IOException;
import java.util.List;

public interface CommandInterface {

    String getName();

    String getDescription();

    String getRole();

    List<OptionData> getOptions();

    void execute(SlashCommandInteractionEvent event) throws IOException;
}
