package bot.commands.logs;

import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.util.List;

public class EnableLogs implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) throws IOException {
        if (args.isEmpty()) {
            if(!Tools.isAdmin(event.getMember())) {
                event.getChannel().sendMessage("You do not have the permission to use this command!").queue();
                return;
            }
            Tools.setLogsType(event.getGuild().getIdLong(), "LOGSon");
            event.getChannel().sendMessage("Enabled heavy logs!").queue();
        } else {
            event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Enables heavy logging (Heavy logging means that it will log A LOT of specific stuff)\n Usage: `a!enableheavylogs`";
    }

    @Override
    public String getInvoke() {
        return "enableheavylogs";
    }
}
