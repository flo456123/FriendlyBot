package me.flo456123.FriendlyBot.common.command;

import me.flo456123.FriendlyBot.common.lavaplayer.PlayerManager;
import me.flo456123.FriendlyBot.jda.commands.VoiceAction;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

/**
 * Responsible for stopping the current playing track and clearing the queue.
 */
public class StopCommand extends VoiceAction {

    /**
     * Stops the player and clears the current playing track.
     *
     * @param ctx the CommandContext of the command event.
     */
    @Override
    protected void handleVoice(SlashCommandInteractionEvent ctx) {
        Guild guild = ctx.getGuild();
        PlayerManager.getInstance().getMusicManager(guild).stopPlayer();

        ctx.reply("The player has been stopped and the track has been cleared").queue();
    }

    @Override
    public String getName() {
        return "stop";
    }
}
