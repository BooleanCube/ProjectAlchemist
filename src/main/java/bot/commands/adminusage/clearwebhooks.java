package bot.commands.adminusage;

import bot.Tools;
import bot.objects.ICommand;
import net.dv8tion.jda.api.entities.Webhook;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class clearwebhooks implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(Tools.isAdmin(event.getMember())) {
            if(args.isEmpty()) {
                for(Webhook w : event.getChannel().retrieveWebhooks().complete()) {
                    w.delete().queue();
                }
                event.getChannel().sendMessage("Successfully deleted all of the webhooks in this channel!").queue();
            } else {
                event.getChannel().sendMessage("Wrong Command Usage!\n" + getHelp()).queue();
                return;
            }
        } else {
            event.getChannel().sendMessage("You don't have the perms to use this command!").queue();
            return;
        }
    }

    @Override
    public String getHelp() {
        return "Deletes all of the webhooks in a channel\n" +
                "Usage: `a!clearwebhooks`";
    }

    @Override
    public String getInvoke() {
        return "clearwebhooks";
    }
}
