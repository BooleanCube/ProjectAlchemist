package bot.commands.utility;

import bot.objects.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.util.List;

public class TimeFormat implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) throws IOException {
        event.getChannel().sendMessage(args.isEmpty() ? "Time Format: DD:HH:MM:SS\nDD=days, HH=hours, MM=minutes, SS=seconds" : "Wrong Command Usage!\n"+getHelp()).queue();
    }

    @Override
    public String getHelp() {
        return "Shows the correct way to format time in commands\nUsage: `a!timeformat`";
    }

    @Override
    public String getInvoke() {
        return "timeformat";
    }
}
