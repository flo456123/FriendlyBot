package me.flo456123.FriendlyBot;

import me.flo456123.FriendlyBot.common.command.JoinCommand;
import me.flo456123.FriendlyBot.common.command.LeaveCommand;
import me.flo456123.FriendlyBot.common.command.PlayCommand;
import me.flo456123.FriendlyBot.jda.commands.handler.CommandHandlerImpl;
import me.flo456123.FriendlyBot.common.listeners.OnGuildVoiceUpdate;
import me.flo456123.FriendlyBot.common.listeners.OnSlashCommands;
import me.flo456123.FriendlyBot.jda.config.Config;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.util.EnumSet;

/**
 * Create a new bot instance and prepare and start it up.
 */
public class BotStartup {
    private final JDA jda;

    public static void main(String[] args) {
        try {
            BotStartup startup = new BotStartup();
        } catch (LoginException | InterruptedException e) {
            System.out.println("Failed to login!");
        }

    }

    public BotStartup() throws LoginException, InterruptedException {
        jda = JDABuilder.createDefault(
                        Config.get("TOKEN"),
                        GatewayIntent.GUILD_MEMBERS,
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.GUILD_VOICE_STATES)
                .disableCache(EnumSet.of(
                        CacheFlag.CLIENT_STATUS,
                        CacheFlag.ACTIVITY,
                        CacheFlag.EMOJI,
                        CacheFlag.STICKER,
                        CacheFlag.SCHEDULED_EVENTS
                ))
                .enableCache(CacheFlag.VOICE_STATE)
                .build()
                .awaitReady();

        CommandHandlerImpl commandHandler = new CommandHandlerImpl(jda);

        commandHandler.addCommand(Commands.slash("join", "makes the bot join your voice channel"), new JoinCommand());
        commandHandler.addCommand(Commands.slash("leave", "makes the bot leave your voice channel"), new LeaveCommand());
        commandHandler.addCommand(Commands.slash("skip", "skips the current song that the bot is playing"), new PlayCommand());
        commandHandler.addCommand(Commands.slash("stop", "stops the current song and clears the queue"), new PlayCommand());
        commandHandler.addCommand(Commands.slash("nowplaying", "gives you info on the current song that is playing"), new PlayCommand());
        commandHandler.addCommand(Commands.slash("queue", "shows the songs in queue"), new PlayCommand());
        commandHandler.addCommand(Commands.slash("loop", "loops the current song"), new PlayCommand());
        commandHandler.addCommand(Commands.slash("play", "makes the bot play a song")
                .addOption(OptionType.STRING, "query", "enter a link or search term for the bot to find your song with"), new PlayCommand());

        commandHandler.updateCommands();

        jda.addEventListener(new OnGuildVoiceUpdate());
        jda.addEventListener(new OnSlashCommands(commandHandler));
    }
}