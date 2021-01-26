package bot.commands.logs;

import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.util.List;

public class LightLogs implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) throws IOException {
        if (args.isEmpty()) {
            if(!Tools.isAdmin(event.getMember())) {
                event.getChannel().sendMessage("You do not have the permission to use this command!").queue();
                return;
            }
            Tools.setLogsType(event.getGuild().getIdLong(), "LIGHTLOGSon");
            event.getChannel().sendMessage("Enabled light logs!").queue();
        } else {
            event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Enables light logging (Light logging means logs that are not TOO specific)\nUsage: `a!enablelightlogs`";
    }

    @Override
    public String getInvoke() {
        return "enablelightlogs";
    }
}
