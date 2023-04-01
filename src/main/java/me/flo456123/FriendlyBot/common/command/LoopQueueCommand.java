package me.flo456123.FriendlyBot.common.command;

import me.flo456123.FriendlyBot.common.lavaplayer.GuildMusicManager;
import me.flo456123.FriendlyBot.common.lavaplayer.PlayerManager;
import me.flo456123.FriendlyBot.jda.commands.VoiceAction;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

/**
 * Responsible for handling the "loopqueue" command, which loops the queue.
 */
public class LoopQueueCommand extends VoiceAction {

    /**
     * Handles the logic for looping the current song that is playing.
     *
     * @param ctx the CommandContext of the command event.
     */
    @Override
    protected void handleVoice(SlashCommandInteractionEvent ctx) {
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        final boolean newLooping = !musicManager.getScheduler().isLooping();

        musicManager.getScheduler().setLoopingQueue(newLooping);

        ctx.replyFormat("Loop queue has been **%s**", newLooping ? "started" : "stopped").queue();
    }

    @Override
    public String getName() {
        return "loop";
    }
}
