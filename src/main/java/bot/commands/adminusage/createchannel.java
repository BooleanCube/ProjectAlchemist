package bot.commands.adminusage;

import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class createchannel implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(Tools.isAdmin(event.getMember())) {
            if(args.size() == 1) {
                event.getGuild().createTextChannel("bruh").setName(args.get(0)).queue();
            } else {
                event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
                return;
            }
        } else {
            event.getChannel().sendMessage("You do not have the perms to use this bot!").queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Creates a channel\n" +
                "Usage: `a!createchannel [name]`";
    }

    @Override
    public String getInvoke() {
        return "createchannel";
    }
}
