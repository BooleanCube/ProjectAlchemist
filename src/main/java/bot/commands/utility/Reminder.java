package bot.commands.utility;

import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Reminder implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) throws IOException {
        if(args.size() >= 2) {
            String time = args.get(0);
            String reminder = String.join(" ", args.subList(1, args.size()));
            long seconds = Tools.timeToSeconds(time);
            if(seconds == -1) {
                event.getChannel().sendMessage("The time provided doesnt follow the correct format! To find the correct format use the command `a!timeformat`").queue();
                return;
            }
            event.getMember().getUser().openPrivateChannel().queue(channel -> {
                channel.sendMessage("Reminder: " + reminder + "\n" + "Duration: " + Tools.secondsToTime(seconds)).queueAfter(seconds, TimeUnit.SECONDS);
            });
            event.getChannel().sendMessage("Set reminder `" + reminder + "`\nDuration: " + Tools.secondsToTime(seconds)).queue();
        } else {
            event.getChannel().sendMessage("Wrong Command Usage!\n"+getHelp()).queue();
        }
    }

    @Override
    public String getHelp() {
        return "Sets a reminder and DMs you the reminder (To know how to type the time use the command `a!timeformat`)\nUsage: `a!reminder [time] [reminder]`";
    }

    @Override
    public String getInvoke() {
        return "reminder";
    }
}
